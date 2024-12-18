package org.gopnik.service;

import jakarta.transaction.Transactional;
import org.gopnik.model.Employee;
import org.gopnik.model.EventLog;
import org.gopnik.repository.EmployeeRepository;
import org.gopnik.repository.EventLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EventLogService {

    @Autowired
    EventLogRepository eventLogRepository;

    @Autowired
    EmployeeService employeeService;

    @Transactional
    public void addEventLog(String eventDescription) {
        Employee currentUser = employeeService.getCurrentEmployee();
        EventLog newLog = new EventLog(currentUser.getUsername(), currentUser.getDrugstoreId(), eventDescription);
        eventLogRepository.save(newLog);
    }

    public List<EventLog> getAllLogs() {
        return this.eventLogRepository.findAll();
    }

    public List<EventLog> getLogsBetween(LocalDateTime start, LocalDateTime end) {
        return this.eventLogRepository.findByTimestampBetween(employeeService.getCurrentEmployee().getDrugstoreId(), start, end);
    }

    public List<EventLog> getLogsForDate(LocalDateTime targetDate) {

        LocalDateTime start = targetDate.toLocalDate().atStartOfDay();
        LocalDateTime end = targetDate.toLocalDate().atTime(23, 59, 59);

        return this.eventLogRepository.findByTimestampBetween(employeeService.getCurrentEmployee().getDrugstoreId(), start, end);
    }

    public LocalDate getMinDate() {
        return this.eventLogRepository.findEarliestDate(employeeService.getCurrentDrugstoreId());
    }
}
