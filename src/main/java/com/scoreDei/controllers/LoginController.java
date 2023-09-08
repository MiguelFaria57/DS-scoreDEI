package com.scoreDei.controllers;

import com.scoreDei.forms.FormLogin;
import com.scoreDei.forms.FormUser;
import com.scoreDei.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scoreDei.AcessToken;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;
    private String result,reguser = "\n";

    @GetMapping("/")
    public String menu(){
        return "redirect:/menu";
    }

    @GetMapping("/login")
    public String login(Model m){
        m.addAttribute("login", new FormLogin());
        m.addAttribute("result", result);
        result = "\n";
        return "login";
    }

    @PostMapping("/login")
    public String result(@ModelAttribute FormLogin l, Model m, HttpServletResponse response){
        String password = this.loginService.findPassword(l.getUsername());
        System.out.println(password);
        if (password != null) {
            if (l.getPassword().equals(password)) {
                m.addAttribute("result", "SUCCESS");
                AcessToken at = new AcessToken();
                at.getToken(response, l.getUsername(), this.loginService.getPermissions(l.getUsername()));
            }
            else {
                result = "WRONG PASSWORD";
                return "redirect:/login";
            }
        }
        else {
            result = "USER NOT FOUND";
            return "redirect:/login";
        }

        return "redirect:/menu";
    }

    @GetMapping("/reguser")
    public String register(HttpServletRequest request,Model m){
        AcessToken at = new AcessToken();
        boolean permission = at.getPermission(request);

        if (permission){
            m.addAttribute("user", new FormUser());
            m.addAttribute("result",reguser);
            reguser = "\n";
            return "reguser";
        }
        else {
            m.addAttribute("result", "PERMISSION DENIED");
            return "error";
        }

    }

    @PostMapping("/reguser")
    public String saveuser(@ModelAttribute FormUser l) {
        boolean success = this.loginService.createUser(l.getUsername(),l.getPassword(),l.getEmail(),l.getPhone(),l.getName(),l.getAdmin());
        if (success) {
            reguser = "USER CREATED SUCCESSFULLY";
            return "redirect:/reguser";
        }
        reguser = "ERROR INVALID FIELDS";
        return "redirect:/reguser";
    }

}
