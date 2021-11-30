package gov.ce.fortaleza.lembrete.controllers;

import gov.ce.fortaleza.lembrete.domain.Reminder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by berkson
 * Date: 29/11/2021
 * Time: 22:52
 */
@Slf4j
@Controller
public class ReminderController {

    @GetMapping(value = "/reminder/new")
    public String newReminder(Model model){
        model.addAttribute("reminder", new Reminder());

        return "/reminder/reminderform";
    }
}
