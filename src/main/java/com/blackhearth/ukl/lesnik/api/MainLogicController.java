package com.blackhearth.ukl.lesnik.api;


import com.blackhearth.ukl.lesnik.api.dto.ReserveRequest;
import com.blackhearth.ukl.lesnik.api.dto.Term;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainLogicController {

    private final MainLogicService service;

    MainLogicController(MainLogicService service) {
        this.service = service;
    }

    @GetMapping("/dates")
    public List<Term> getAllTerms() {
        return service.getAllTermsFromToday();
    }

    @PostMapping("/term/reserve")
    public ResponseEntity response(@RequestBody ReserveRequest request) {
        return service.reserveTerm(request);
    }




}
