package com.sky.security.dao;

import com.sky.security.model.AuditLog;
import com.sky.security.model.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuditLogRepository extends JpaSpecificationExecutor<AuditLog>, CrudRepository<AuditLog, Long> {
}
