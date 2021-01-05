package com.justincode.toad.converter.api;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ApiController {

    @RequestMapping(
            path = "/convert/xls/to/xml",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public String convertXlsToXml(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            return fileName + workbook.getSheetName(0);
        } catch (IOException e) {
            return "Issue with file processing";
        }
    }
}
