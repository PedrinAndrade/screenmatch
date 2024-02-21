package br.com.alura.screenmatch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SerieController {

    @GetMapping("/series")//Indica o tipo de requisição
    public String obterSeries(){
        return "Aqui vão ser listadas as séries";
    }

}