package com.azaroff.x3.web.rest;

import com.azaroff.x3.business.service.BusinessService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private BusinessService businessService;

    @GetMapping
    public String getAll() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(businessService.getAll());
    }
}