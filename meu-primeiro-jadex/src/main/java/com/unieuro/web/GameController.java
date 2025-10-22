package com.unieuro.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {

    @GetMapping("/")
    public String index() {
        return "index"; // Retorna src/main/resources/templates/index.html
    }
    
    @GetMapping("/game")
    public String game() {
        return "game"; // Retorna src/main/resources/templates/game.html
    }
}