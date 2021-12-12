package md.utm.security.controller;

import lombok.RequiredArgsConstructor;
import md.utm.security.service.AuditService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final AuditService auditService;

    @GetMapping
    public String paymentForm() {
        return "payment";
    }

    @PostMapping
    public String makePayment(@RequestParam String to,
                              @RequestParam Integer amount,
                              Model model) {
        var actionDescription = String.format("Sent %d money units to %s", amount, to);
        var currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();

        auditService.registerAction(actionDescription, currentUserName);

        model.addAttribute("recipient", to);
        model.addAttribute("amount", amount);
        return "paymentSuccessful";
    }
}
