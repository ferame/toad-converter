package com.justincode.toad.converter.parser;

import com.justincode.toad.converter.dao.Product;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class XlsParser {
    public List<Product> parseXls(final Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        Map<Integer,String> columnsNames = getColumnsNames(sheet.getRow(1));
        sheet.removeRow(sheet.getRow(0));
        sheet.removeRow(sheet.getRow(1));
        List<Product> products = getProducts(sheet, columnsNames);
        return products;
    }

    private Map<Integer, String> getColumnsNames(Row row) {
        Map<Integer, String> columnsNames = new HashMap<>();
        Iterator<Cell> cellIterator = row.cellIterator();
        int i = 0;
        while (cellIterator.hasNext()){
            Cell currentCell = cellIterator.next();
            columnsNames.put(i++,currentCell.getStringCellValue());
        }
        return columnsNames;
    }

    private List<Product> getProducts(Sheet sheet, Map<Integer, String> columnsNames) {
        List<Product> products = new ArrayList<>();
        List<Map<String, String>> initialProductsParse = new ArrayList<>();
        Iterator<Row> rowIterator = sheet.iterator();
        int i = 0;
        while (rowIterator.hasNext()) {
            Row currentRow = rowIterator.next();
            if(isEmptyCell(currentRow.getCell(0))){
                log.info("Row " + i + " is empty");
            } else {
                initialProductsParse.add(getProduct(currentRow, columnsNames));
            }
        }
        return products;
    }

    private boolean isEmptyCell(Cell cell) {
        return cell == null || cell.getCellType() == CellType.BLANK;
    }

//    TODO: change to return an actual Product object
    private Map<String, String> getProduct(Row row, Map<Integer, String> columnsNames) {
        Map<String, String> productMap = new HashMap<>();
        columnsNames.forEach((index, name) -> {
            Cell cell = row.getCell(index);
            cell.setCellType(CellType.STRING);
            productMap.put(name, cell.getStringCellValue());
        });
        return productMap;
    }
}
