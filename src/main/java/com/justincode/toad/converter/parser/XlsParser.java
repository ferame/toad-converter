package com.justincode.toad.converter.parser;

import com.google.common.collect.ImmutableSet;
import com.justincode.toad.converter.dao.Attribute;
import com.justincode.toad.converter.dao.Product;
import com.justincode.toad.converter.dao.Variant;
import com.justincode.toad.converter.parser.constants.ExcelColumnsConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class XlsParser {
    public List<Product> parseXls(final Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        List<String> columnsNames = getColumnsNames(sheet.getRow(1));
        List<Product> products;
        if (checkMandatoryColumns(columnsNames)) {
            sheet.removeRow(sheet.getRow(0));
            sheet.removeRow(sheet.getRow(1));
            products = getProducts(sheet, columnsNames);
        } else {
            products = new ArrayList<>();
        }
        return products;
    }

    private List<String> getColumnsNames(Row row) {
        List<String> columnsNames = new ArrayList<>();
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()){
            Cell currentCell = cellIterator.next();
            columnsNames.put(currentCell.getStringCellValue());
        }
        return columnsNames;
    }

    private List<Product> getProducts(Sheet sheet, List<String> columnsNames) {
        List<Product> products = new ArrayList<>();
        List<Map<String, String>> initialProductsParse = new ArrayList<>();
        Iterator<Row> rowIterator = sheet.iterator();
        int i = 0;
        while (rowIterator.hasNext()) {
            Row currentRow = rowIterator.next();
            if(isEmptyCell(currentRow.getCell(0))){
                log.info("Row " + i + " is empty");
            } else {
                Optional<Product> product = getProduct(currentRow, columnsNames);
//                TODO: do smth
            }
        }
        return products;
    }

    private boolean isEmptyCell(Cell cell) {
        return cell == null || cell.getCellType() == CellType.BLANK;
    }

//    TODO: change to return an actual Product object
    private Optional<Product> getProduct(Row row, Map<Integer, String> columnsNames) {
        Long id;
        String url, category, description, title, manufacturer;
        double price;
        Optional<Double> oldPrice, primeCost = Optional.empty();
        Optional<Integer> warranty = Optional.empty();
        Optional<String> deliveryDate, deliveryText = Optional.empty();
        Optional<List<Attribute>> attributes;
        Optional<List<Variant>> variants;



        columnsNames.forEach((index, name) -> {
            Cell cell = row.getCell(index);
//            cell.setCellType(CellType.STRING);
            productMap.put(name, cell.getStringCellValue());
        });

        Set<String> mandatoryStrings = ImmutableSet.of();

        Optional<Product> product;
        if(checksPass) {
            product = Optional.of(new Product());
        } else {
            product = Optional.empty();
        }
        return productMap;
    }

    private boolean checkMandatoryColumns(List<String> columnsNames) {
        return columnsNames.containsAll(
                ExcelColumnsConstants.mandatoryColumns.stream().map(Enum::name).collect(Collectors.toList()));
    }
}
