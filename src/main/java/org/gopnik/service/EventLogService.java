package org.gopnik.service;

import jakarta.transaction.Transactional;
import org.gopnik.model.EventLog;
import org.gopnik.repository.EmployeeRepository;
import org.gopnik.repository.EventLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventLogService {

    @Autowired
    EventLogRepository eventLogRepository;

    @Autowired
    EmployeeService employeeService;

    @Transactional
    public void addEventLog(String eventDescription) {
        EventLog tmp = new EventLog(employeeService.getCurrentEmployee().getUsername(), eventDescription);
        eventLogRepository.save(tmp);
    }

    public List<EventLog> getAllLogs() {
        return this.eventLogRepository.findAll();
    }
}
