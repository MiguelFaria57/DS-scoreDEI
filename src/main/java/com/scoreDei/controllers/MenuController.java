package com.scoreDei.controllers;

import com.scoreDei.AcessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MenuController {
    @GetMapping("/menu")
    public String menu(HttpServletRequest request, Model m){
        AcessToken at = new AcessToken();
        if (at.getPermission(request)) m.addAttribute("permission", "1");
        else m.addAttribute("permission", "0");

        return "menu";
    }
}
