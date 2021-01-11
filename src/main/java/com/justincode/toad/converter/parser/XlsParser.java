package com.justincode.toad.converter.parser;

import com.justincode.toad.converter.dao.Product;
import com.justincode.toad.converter.formatters.ParametersFormatter;
import com.justincode.toad.converter.parser.constants.ExcelColumnsConstants;
import com.justincode.toad.converter.validators.ParametersValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class XlsParser {
    ParametersValidator parametersValidator;

    @Autowired
    public XlsParser(ParametersValidator parametersValidator) {
        this.parametersValidator = parametersValidator;
    }

    public List<Product> parseXls(final Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        List<String> columnsNames = getColumnsNames(sheet.getRow(1));
        List<Product> products;
        if (checkMandatoryColumns(columnsNames)) {
            sheet.removeRow(sheet.getRow(0));
            sheet.removeRow(sheet.getRow(1));
            products = getProducts(sheet, columnsNames);
        } else {
            log.warn("Mandatory columns {} not found", String.join(", ", missingMandatoryColumns(columnsNames)));
            products = new ArrayList<>();
        }
        return products;
    }

    private List<String> getColumnsNames(Row row) {
        List<String> columnsNames = new ArrayList<>();
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell currentCell = cellIterator.next();
            columnsNames.add(currentCell.getStringCellValue());
        }
        return columnsNames;
    }

    private List<Product> getProducts(Sheet sheet, List<String> columnsNames) {
        List<Product> products = new ArrayList<>();
        Iterator<Row> rowIterator = sheet.iterator();
        int i = 0;
        while (rowIterator.hasNext()) {
            Row currentRow = rowIterator.next();
            if (isEmptyCell(currentRow.getCell(0))) {
                log.info("Row " + i + " is empty");
            } else {
                Map<String, String> productParameters = getProductParameters(currentRow, columnsNames);
                Map<String, String> checkedProductParameters = parametersValidator.checkProductParameters(productParameters, columnsNames);
                if (!checkedProductParameters.isEmpty()) {
                    products.add(ParametersFormatter.createProduct(checkedProductParameters));
                }
            }
        }
        return products;
    }

    private boolean isEmptyCell(Cell cell) {
        return cell == null || cell.getCellType() == CellType.BLANK;
    }

    //    TODO: change to return an actual Product object
    private Map<String, String> getProductParameters(Row row, List<String> columnsNames) {
        Map<String, String> productMap = new HashMap<>();
        int index = 0;
        for (String name : columnsNames) {
            Cell cell = row.getCell(index);
            DataFormatter formatter = new DataFormatter();
            productMap.put(name, formatter.formatCellValue(cell));
            index++;
        }
        return productMap;
    }

    private boolean checkMandatoryColumns(List<String> columnsNames) {
        return columnsNames.containsAll(
                ExcelColumnsConstants.mandatoryColumns);
    }

    private List<String> missingMandatoryColumns(List<String> columnsNames) {
        return ExcelColumnsConstants.mandatoryColumns.stream()
                .filter(name -> !columnsNames.contains(name))
                .collect(Collectors.toList());
    }
}
