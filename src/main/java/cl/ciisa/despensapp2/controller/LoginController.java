package cl.ciisa.despensapp2.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class LoginController {

	
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Cambia "loginForm" por "login" para usar nuestra vista personalizada
    }
    
}
