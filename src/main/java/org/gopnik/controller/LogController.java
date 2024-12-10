package org.gopnik.controller;

import org.gopnik.service.EventLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/logs")
public class LogController {

    @Autowired
    EventLogService eventLogService;

    @GetMapping("")
    public String logs(Model model) {
        model.addAttribute("allLogs", eventLogService.getAllLogs());
        return "logs";
    }
}
