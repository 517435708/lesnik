package com.blackhearth.ukl.lesnik.api;

import com.blackhearth.ukl.lesnik.api.dto.ReserveRequest;
import com.blackhearth.ukl.lesnik.api.dto.Term;
import com.blackhearth.ukl.lesnik.api.model.TermModel;
import com.blackhearth.ukl.lesnik.api.repositories.TermModelRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MainLogicServiceImpl implements MainLogicService {

    private final TermModelRepository termModelRepository;

    public MainLogicServiceImpl(TermModelRepository repository) {
        termModelRepository = repository;
    }

    @Override
    public ResponseEntity reserveTerm(ReserveRequest request) {

        Optional<TermModel> optionalTermModel = termModelRepository.findById(request.getTermId());
        TermModel termModel = optionalTermModel.orElse(new TermModel());

        if (termModelNotValid(termModel, request)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        termModel.setAvailablePlaces(calculateAvailablePlaces(termModel, request));
        termModelRepository.save(termModel);

        return new ResponseEntity(HttpStatus.OK);
    }


    @Override
    public List<Term> getAllTermsFromToday() {
        return null;
    }


    private boolean termModelNotValid(TermModel termModel, ReserveRequest request) {
        return notEnoughPlaces(termModel, request) || termModelNotValid(termModel);
    }

    private int calculateAvailablePlaces(TermModel termModel, ReserveRequest request) {
        int places = termModel.getAvailablePlaces() - request.getNumberOfPeople();
        return places < 0 ? 0 : places;
    }


    private boolean notEnoughPlaces(TermModel termModel, ReserveRequest request) {
        return termModel.getAvailablePlaces() - request.getNumberOfPeople() < -2;
    }

    private boolean termModelNotValid(TermModel termModel) {
        if (termModel.getAvailablePlaces() == 0) {
            return true;
        }

        if (termModel.getDateOfTraining()
                     .isBefore(LocalDate.now().minusDays(1))) {
            return true;
        }

        return termModel.getMeetingNumber() != 1;
    }
}
