package com.justincode.toad.converter.api;

import com.justincode.toad.converter.clients.AmazonClient;
import com.justincode.toad.converter.dao.Product;
import com.justincode.toad.converter.generators.XmlGenerator;
import com.justincode.toad.converter.parser.XlsParser;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final XlsParser xlsParser;
    private final XmlGenerator xmlGenerator;
    private final AmazonClient amazonClient;

    @Autowired
    public ApiController(XlsParser xlsParser, XmlGenerator xmlGenerator, AmazonClient amazonClient) {
        this.xlsParser = xlsParser;
        this.xmlGenerator = xmlGenerator;
        this.amazonClient = amazonClient;
    }

    @RequestMapping(
            path = "/convert/xls/to/xml",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public String convertXlsToXml(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            List<Product> productList = xlsParser.parseXls(workbook);
            return xmlGenerator.generateXmlFile(productList);
        } catch (IOException e) {
            return "Issue with file processing";
        }
    }

    @RequestMapping(
            path = "/bucket/upload/file",
            method = RequestMethod.POST
//            consumes = MediaType.APPLICATION_XML_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.amazonClient.uploadFile(file);
    }

    @RequestMapping(
            path = "/bucket/delete/file",
            method = RequestMethod.DELETE
    )
    public String deleteFile(@ApiParam("Path to the file") @RequestBody String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }
}
