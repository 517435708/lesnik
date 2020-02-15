package com.blackhearth.ukl.lesnik.api.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@Entity(name = "term_model")
public class TermModel {

    @Id
    @SequenceGenerator(name = "term_model_seq_")
    @Column(name = "id")
    int id;

    @Column(name = "date_of_training")
    LocalDate dateOfTraining;

    @Column(name = "available_places")
    int availablePlaces;

    @Column(name = "meeting_number")
    int meetingNumber;



}
