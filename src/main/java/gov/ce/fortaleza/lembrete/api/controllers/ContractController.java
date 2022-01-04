package gov.ce.fortaleza.lembrete.api.controllers;

import gov.ce.fortaleza.lembrete.domain.Contract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by berkson
 * Date: 03/01/2022
 * Time: 22:52
 */
@Slf4j
@Controller
public class ContractController {

    @GetMapping(value = "/contract/new")
    public String newReminder(Model model){
        model.addAttribute("reminder", new Contract());

        return "/contract/contractform";
    }
}
