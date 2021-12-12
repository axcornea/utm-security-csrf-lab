package md.utm.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @GetMapping("/login")
    public String showLogin(@RequestParam(required = false) Boolean error,
                            Model model) {
        model.addAttribute("invalidCredentials", error);
        return "login";
    }

    @PostMapping("/login")
    public String login() {
        return "redirect:/audit";
    }
}
