package com.scoreDei.services;

import com.scoreDei.data.Login;
import com.scoreDei.repositories.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private LoginRepository loginRepository;

    public String findPassword(String username) {
        return loginRepository.findPassword(username);
    }

    public String getPermissions(String username) {
        if(loginRepository.getPermissions(username)){
            return "admin";
        }
        else
            return "user";
    }

    public boolean createUser(String username,String password,String email,String phone,String name,boolean is_admin){
        String user = loginRepository.findUsername(username);
        String m = loginRepository.findPhone(phone);
        String e = loginRepository.findMail(email);
        if(user != null || m != null || e != null || username.equals("") || email.equals("") || phone.equals("") || name.equals("")){
            return false;
        }
        Login l = new Login(username, password, name, email, phone, is_admin);
        loginRepository.save(l);
        return true;
    }
}
