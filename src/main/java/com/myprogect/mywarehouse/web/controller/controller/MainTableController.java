package com.myprogect.mywarehouse.web.controller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainTableController {
    @Autowired
    ConsignmentNoteController controller;

    @RequestMapping("/")
    public String home(Model model) {
        return controller.showAllRecords(model);
    }
}
