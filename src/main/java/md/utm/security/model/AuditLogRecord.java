package md.utm.security.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuditLogRecord {
    private final String action;
    private final String subject;
    private final LocalDateTime timestamp;
}
