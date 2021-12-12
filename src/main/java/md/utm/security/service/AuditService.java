package md.utm.security.service;

import md.utm.security.model.AuditLogRecord;

import java.util.List;

public interface AuditService {

    /**
     * Get all actions executed by application users.
     *
     * @return Full audit log
     */
    List<AuditLogRecord> getAuditLog();

    /**
     * Register an action executed by current user.
     *
     * @param action  Action being executed
     * @param subject User executing the action
     */
    void registerAction(String action, String subject);
}
