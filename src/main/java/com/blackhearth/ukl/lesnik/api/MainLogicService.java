package com.blackhearth.ukl.lesnik.api;

import com.blackhearth.ukl.lesnik.api.dto.ReserveRequest;
import com.blackhearth.ukl.lesnik.api.dto.Term;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MainLogicService {
    ResponseEntity reserveTerm(ReserveRequest request);
    List<Term> getAllTermsFromToday();

}
