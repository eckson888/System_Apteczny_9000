package org.gopnik.controller;

import org.gopnik.service.EventLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        LocalDateTime end = date.atTime(23, 59, 59);

        return this.eventLogService.getLogsBetween(start, end).stream().map(Object::toString).toList();
    }
}

//LocalDateTime.parse("2019-12-31T19:15:30");
//https://dev.to/rpkr/different-ways-to-send-a-file-as-a-response-in-spring-boot-for-a-rest-api-43g7