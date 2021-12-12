package md.utm.security.service.impl;

import lombok.extern.slf4j.Slf4j;
import md.utm.security.model.AuditLogRecord;
import md.utm.security.service.AuditService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class InMemoryAuditServiceImpl implements AuditService {

    private final List<AuditLogRecord> records = new ArrayList<>();

    @Override
    public List<AuditLogRecord> getAuditLog() {
        return new ArrayList<>(records);
    }

    @Override
    public void registerAction(String action, String subject) {
        var newItem = new AuditLogRecord(action, subject, LocalDateTime.now());
        log.info("New action registered; subject='{}'; action='{}'", subject, action);
        records.add(newItem);
    }
}
