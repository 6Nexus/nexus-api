package com.nexus.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checks")
public class CheckController {

    @GetMapping
    public String checkDaAplicacao(){
        return "A aplicação está de pé, parabéns!";
    }
}
