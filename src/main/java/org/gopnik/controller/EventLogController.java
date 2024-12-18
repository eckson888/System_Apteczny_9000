package org.gopnik.controller;

import org.gopnik.model.EventLog;
import org.gopnik.service.EventLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/logs")
public class EventLogController {

    @Autowired
    EventLogService eventLogService;

    @GetMapping("")
    public String logs(Model model) {
        LocalDateTime start = LocalDateTime.of(2024, 12, 11, 0, 0);
        LocalDateTime end = start.plusMinutes(1439);
        model.addAttribute("allLogs", eventLogService.getLogsBetween(start, end));
        model.addAttribute("minDate", eventLogService.getMinDate());
        return "event-logs";
    }

    @GetMapping(path = "/{targetDate}")
    @ResponseBody
    public List<String> getLogsFromDate(@PathVariable String targetDate, Model model) {
        LocalDate date = LocalDate.parse(targetDate, DateTimeFormatter.ISO_DATE);

        LocalDateTime start = date.atStartOfDay();

        return this.eventLogService.getLogsForDate(start).stream().map(Object::toString).toList();
    }

    @GetMapping("/download/csv/{targetDate}")
    public ResponseEntity<byte[]> downloadCsvFile(@PathVariable String targetDate) {
        LocalDate date = LocalDate.parse(targetDate, DateTimeFormatter.ISO_DATE);
        LocalDateTime start = date.atStartOfDay();

        List<EventLog> entities = this.eventLogService.getLogsForDate(start);

        String entitiesAsCsv = entities.stream()
                .map(EventLog::toCsv)
                .collect(Collectors.joining("\n"));

        String csvContent = "Timestamp;Username;DrugstoreId;EventDescription\n" + entitiesAsCsv;
        byte[] csvBytes = csvContent.getBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("event_logs_" + targetDate + ".csv")
                .build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(csvBytes);
    }

    //TODO: CO GDY POBIERANIE JEST A LOGOW NIE MA TEGO DNIA
    @GetMapping("/download/txt/{targetDate}")
    public ResponseEntity<byte[]> downloadTxtFile(@PathVariable String targetDate) {
        LocalDate date = LocalDate.parse(targetDate, DateTimeFormatter.ISO_DATE);
        LocalDateTime start = date.atStartOfDay();

        List<EventLog> entities = this.eventLogService.getLogsForDate(start);

        String entitiesAsTxt = entities.stream()
                .map(EventLog::toString)
                .collect(Collectors.joining("\n"));

        byte[] txtBytes = entitiesAsTxt.getBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("event_logs_" + targetDate + ".txt")
                .build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(txtBytes);
    }
}

//LocalDateTime.parse("2019-12-31T19:15:30");
//https://dev.to/rpkr/different-ways-to-send-a-file-as-a-response-in-spring-boot-for-a-rest-api-43g7