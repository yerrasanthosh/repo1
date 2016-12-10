package com.vroozi.customerui.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: SURYA MANDADAPU
 * Date: 9/22/12
 * Time: 2:16 PM
 */
@Controller
@RequestMapping("/")
public class MediatorController {


    @RequestMapping(value="/vroozi")
    public String landing(HttpServletRequest request, HttpServletResponse response) throws Exception{

        return "landing";
    }

    @RequestMapping(value="/timeout")
    public String timeout(HttpServletRequest request, HttpServletResponse response) throws Exception{
        return "timeout";
    }
    @RequestMapping(value="/help")
    public String help() {
        return "help";
    }

    @RequestMapping(value="/contact")
    public String contact() {
        return "contact";
    }

    @RequestMapping(value="/policy")
    public String policy() {
        return "policy";
    }
    
    @RequestMapping(value="/download")
    public String download(HttpServletRequest request, HttpServletResponse response) throws Exception{

        return "download";
    }
}