package com.coffee.coffee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/about")
    public String about(Model model){
        return "/about";
    }


    @GetMapping("/contact")
    public String contact(Model model){
        return "/contact";
    }

    @GetMapping("/home")
    public String home(Model model){
        return "/home";
    }

    @GetMapping("/menu")
    public String menu(Model model){
        return "/menu";
    }

    @GetMapping("/reservation")
    public String reservation(Model model){
        return "/reservation";
    }

    @GetMapping("/service")
    public String service(Model model){
        return "/service";
    }

    @GetMapping("/testimonial")
    public String testimonial(Model model){
        return "/testimonial";
    }

}
