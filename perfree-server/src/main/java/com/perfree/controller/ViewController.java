package com.perfree.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 视图渲染默认controller
 */
@Controller
public class ViewController {

    @RequestMapping("/admin")
    public String admin() {
        return "redirect:/admin/home";
    }
}
