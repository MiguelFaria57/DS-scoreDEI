package com.scoreDei.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String error(Model m){
        m.addAttribute("result","ERROR: SOMETHING WENT WRONG");
        return "error";
    }
}


