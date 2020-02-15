package com.blackhearth.ukl.lesnik.api;

import com.blackhearth.ukl.lesnik.api.dto.ReserveRequest;
import com.blackhearth.ukl.lesnik.api.model.TermModel;
import com.blackhearth.ukl.lesnik.api.repositories.TermModelRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MainLogicServiceTest {


    private TermModelRepository repository = Mockito.mock(TermModelRepository.class);
    private MainLogicService mainLogicService = new MainLogicServiceImpl(repository);

    @Test
    void reserveWrongTermThatIsSecondMeetingNumberThenError() {
        //given
        ReserveRequest reserveRequest = new ReserveRequest();

        reserveRequest.setNumberOfPeople(1);
        reserveRequest.setTermId(1);

        TermModel termModel = new TermModel();

        termModel.setId(1);
        termModel.setAvailablePlaces(1);
        termModel.setDateOfTraining(LocalDate.now().plusDays(14));
        termModel.setMeetingNumber(2);


        //when
        Mockito.when(repository.findById(1)).thenReturn(Optional.of(termModel));
        ResponseEntity responseEntity = mainLogicService.reserveTerm(reserveRequest);


        //then
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    @Test
    void reserveWrongTermThatIsTooLateThenError() {
        //given
        ReserveRequest reserveRequest = new ReserveRequest();

        reserveRequest.setNumberOfPeople(1);
        reserveRequest.setTermId(1);

        TermModel termModel = new TermModel();

        termModel.setId(1);
        termModel.setAvailablePlaces(1);
        termModel.setDateOfTraining(LocalDate.now().minusDays(14));
        termModel.setMeetingNumber(1);

        //when
        Mockito.when(repository.findById(1)).thenReturn(Optional.of(termModel));
        ResponseEntity responseEntity = mainLogicService.reserveTerm(reserveRequest);


        //then
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void reserveWrongTermThenError() {
        //given
        ReserveRequest reserveRequest = new ReserveRequest();

        reserveRequest.setNumberOfPeople(1);
        reserveRequest.setTermId(5);

        TermModel termModel = new TermModel();

        termModel.setId(1);
        termModel.setAvailablePlaces(1);
        termModel.setDateOfTraining(LocalDate.now().plusDays(14));
        termModel.setMeetingNumber(1);


        //when
        Mockito.when(repository.findById(5)).thenReturn(Optional.empty());
        ResponseEntity responseEntity = mainLogicService.reserveTerm(reserveRequest);


        //then
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void reserveTermWhenAvailablePlacesEqOneAndWantReserveOneThenNoMorePlacesButOk() {
        //given
        ReserveRequest reserveRequest = new ReserveRequest();

        reserveRequest.setNumberOfPeople(1);
        reserveRequest.setTermId(1);

        TermModel termModel = new TermModel();

        termModel.setId(1);
        termModel.setAvailablePlaces(1);
        termModel.setDateOfTraining(LocalDate.now().plusDays(14));
        termModel.setMeetingNumber(1);


        TermModel expectedTerm = new TermModel();

        expectedTerm.setId(1);
        expectedTerm.setAvailablePlaces(0);
        expectedTerm.setDateOfTraining(LocalDate.now().plusDays(14));
        expectedTerm.setMeetingNumber(1);

        //when
        Mockito.when(repository.findById(1)).thenReturn(Optional.of(termModel));
        ResponseEntity responseEntity = mainLogicService.reserveTerm(reserveRequest);


        //then
        Mockito.verify(repository, Mockito.times(1)).save(expectedTerm);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    void reserveTermWhenAvailablePlacesEqOneAndWantReserveThreeThenNoMorePlacesButOk() {
        //given
        ReserveRequest reserveRequest = new ReserveRequest();

        reserveRequest.setNumberOfPeople(3);
        reserveRequest.setTermId(1);

        TermModel termModel = new TermModel();

        termModel.setId(1);
        termModel.setAvailablePlaces(1);
        termModel.setDateOfTraining(LocalDate.now().plusDays(14));
        termModel.setMeetingNumber(1);


        TermModel expectedTerm = new TermModel();

        expectedTerm.setId(1);
        expectedTerm.setAvailablePlaces(0);
        expectedTerm.setDateOfTraining(LocalDate.now().plusDays(14));
        expectedTerm.setMeetingNumber(1);

        //when
        Mockito.when(repository.findById(1)).thenReturn(Optional.of(termModel));
        ResponseEntity responseEntity = mainLogicService.reserveTerm(reserveRequest);


        //then
        Mockito.verify(repository, Mockito.times(1)).save(expectedTerm);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void reserveTermWhenAvailablePlacesEqLotsAndWantReserveThreeThenOk() {
        //given
        ReserveRequest reserveRequest = new ReserveRequest();

        reserveRequest.setNumberOfPeople(3);
        reserveRequest.setTermId(1);

        TermModel termModel = new TermModel();

        termModel.setId(1);
        termModel.setAvailablePlaces(10);
        termModel.setDateOfTraining(LocalDate.now().plusDays(14));
        termModel.setMeetingNumber(1);


        TermModel expectedTerm = new TermModel();

        expectedTerm.setId(1);
        expectedTerm.setAvailablePlaces(7);
        expectedTerm.setDateOfTraining(LocalDate.now().plusDays(14));
        expectedTerm.setMeetingNumber(1);

        //when
        Mockito.when(repository.findById(1)).thenReturn(Optional.of(termModel));
        ResponseEntity responseEntity = mainLogicService.reserveTerm(reserveRequest);


        //then
        Mockito.verify(repository, Mockito.times(1)).save(expectedTerm);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void reserveTermWhenAvailablePlacesNoMoreAndWantReserveThenError() {
        //given
        ReserveRequest reserveRequest = new ReserveRequest();

        reserveRequest.setNumberOfPeople(1);
        reserveRequest.setTermId(1);

        TermModel termModel = new TermModel();

        termModel.setId(1);
        termModel.setAvailablePlaces(0);
        termModel.setDateOfTraining(LocalDate.now().plusDays(14));
        termModel.setMeetingNumber(1);


        TermModel expectedTerm = new TermModel();

        expectedTerm.setId(1);
        expectedTerm.setAvailablePlaces(0);
        expectedTerm.setDateOfTraining(LocalDate.now().plusDays(14));
        expectedTerm.setMeetingNumber(1);

        //when
        Mockito.when(repository.findById(1)).thenReturn(Optional.of(termModel));
        ResponseEntity responseEntity = mainLogicService.reserveTerm(reserveRequest);


        //then
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }


    @Test
    void getAllTermsFromToday() {


    }
}