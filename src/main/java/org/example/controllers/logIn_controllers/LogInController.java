package org.example.controllers.logIn_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/people")
public class LogInController {

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
}
