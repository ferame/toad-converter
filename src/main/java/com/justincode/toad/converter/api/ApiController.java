package com.justincode.toad.converter.api;

import com.justincode.toad.converter.dao.Product;
import com.justincode.toad.converter.parser.XlsParser;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final XlsParser xlsParser;

    @Autowired
    public ApiController(XlsParser xlsParser) {
        this.xlsParser = xlsParser;
    }

    @RequestMapping(
            path = "/convert/xls/to/xml",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public String convertXlsToXml(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            List<Product> productList = xlsParser.parseXls(workbook);
            return fileName + workbook.getSheetName(0);
        } catch (IOException e) {
            return "Issue with file processing";
        }
    }
}
