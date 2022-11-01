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

//    @RequestMapping(value = "/user/{id}",method={RequestMethod.DELETE, RequestMethod.GET})
//    public String deleteUser(@PathVariable(value = "id") long id, Model model){
//        userService.delete(id);
//
//        return "redirect:/admin";
//    }
//
//    @GetMapping("/user/{id}/edit")
//    public String userEdit(@PathVariable(value = "id") long id , Model model){
//        if(userService.getById(id)!=null){
//            return "redirect:/admin";
//        }
//
//        User user = userService.getById(id);
//        List<User> users = new ArrayList<>();
//        users.add(user);
//        model.addAttribute("user",users);
//        return "admin-edit";
//    }
//
//    @PutMapping("/user/{id}/edit")
//    public String userUpdate(@PathVariable(value = "id") long id,
//                             @RequestParam String firstName,
//                             @RequestParam String lastName,
//                             @RequestParam String username,
//                             @RequestParam String email,Model model){
//        User user = userService.getById(id);
//        user.setFirstName(firstName);
//        user.setLastName(lastName);
//        user.setUsername(username);
//        user.setEmail(email);
//
//        return "redirect:/admin";
//    }

}
