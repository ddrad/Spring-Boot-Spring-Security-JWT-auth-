package com.azaroff.x3.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/general")
public class GeneralController {

    @GetMapping("/free")
    public String getFree() {
        return "Hello from free App";
    }
}
