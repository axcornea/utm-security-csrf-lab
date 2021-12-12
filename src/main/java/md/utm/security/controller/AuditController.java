package md.utm.security.controller;

import lombok.RequiredArgsConstructor;
import md.utm.security.model.AuditLogRecord;
import md.utm.security.service.AuditService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @GetMapping
    public String getAuditHistory(Model model) {
        final List<AuditLogRecord> auditLog = auditService.getAuditLog();

        model.addAttribute("auditLog", auditLog);
        return "auditLog";
    }
}
