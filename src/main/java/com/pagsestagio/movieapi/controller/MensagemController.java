package com.pagsestagio.movieapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mensagem")
public class MensagemController {
    @GetMapping
    public String mensagem(){
        return "Este é o início do meu projeto no Pags!";
    }
}
