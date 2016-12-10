package com.vroozi.customerui.common;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import java.util.HashMap;
import java.util.Map;

/**
 * User: SURYA MANDADAPU
 * Date: 12/19/12
 * Time: 3:56 PM
 */
public class JsonError {

    private final String message;

    public JsonError(String message) {
        this.message = message;
    }

    public ModelAndView asModelAndView() {
        MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
        Map<String, String> response = new HashMap<String, String>();
                response.put("error", message);
        return new ModelAndView(jsonView,response);
    }
}
