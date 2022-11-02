package com.coffee.coffee.controller;

import com.coffee.coffee.dto.UserDto;
import com.coffee.coffee.model.User;
import com.coffee.coffee.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;



    @GetMapping
    public ModelAndView getAllEmployees() {
        ModelAndView mav = new ModelAndView("admin");
        mav.addObject("users", userService.findAllUsers());
        return mav;
    }

    @GetMapping("/addUserForm")
    public ModelAndView addEmployeeForm() {
        ModelAndView mav = new ModelAndView("add-user-form");
        User newUser = new User();
        mav.addObject("user", newUser);
        return mav;
    }

    @PostMapping("/saveUser")
    public String saveEmployee(@ModelAttribute UserDto user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/updateUser")
    public String updateUser(@RequestParam Long userId,@ModelAttribute UserDto user){
        userService.update(userId,user);
        return "redirect:/admin";
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long userId) {
        ModelAndView mav = new ModelAndView("update-user-form");
        User user = userService.getById(userId);
        mav.addObject("user", user);
        return mav;
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam long id){
        userService.delete(id);

        return "redirect:/admin";
    }



}
