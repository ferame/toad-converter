package com.justincode.toad.converter.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @PostMapping("/convert/xls/to/xml")
    public String convertXlsToXml() {
        return "Converted";
    }
}
