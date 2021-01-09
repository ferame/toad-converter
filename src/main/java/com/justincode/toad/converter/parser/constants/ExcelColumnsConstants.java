package com.justincode.toad.converter.parser.constants;

import org.apache.poi.ss.usermodel.CellType;

import java.util.AbstractMap;
import java.util.EnumSet;
import java.util.Map;

public final class ExcelColumnsConstants {
    public static EnumSet<ColumnsNames> mandatoryColumns =
            EnumSet.of(ColumnsNames.ID, ColumnsNames.CATEGORY, ColumnsNames.TITLE, ColumnsNames.DESCRIPTION,
                    ColumnsNames.PRICE, ColumnsNames.QUANTITY, ColumnsNames.IMAGES);

    public static EnumSet<ColumnsNames> optionalColumns =
            EnumSet.of(ColumnsNames.URL, ColumnsNames.BARCODE, ColumnsNames.PRICE_OLD, ColumnsNames.PRIME_COST,
                    ColumnsNames.WARRANTY, ColumnsNames.DELIVERY_DATE, ColumnsNames.ATTRIBUTES, ColumnsNames.VARIANTS);


    Map<String, CellType> columnsTypes = Map.ofEntries(
            new AbstractMap.SimpleEntry<String, CellType>(ColumnsNames.URL.name(), CellType.STRING),
            new AbstractMap.SimpleEntry<String, CellType>(ColumnsNames.ID.name(), CellType.NUMERIC),
            new AbstractMap.SimpleEntry<String, CellType>(ColumnsNames.BARCODE.name(), CellType.NUMERIC),
            new AbstractMap.SimpleEntry<String, CellType>(ColumnsNames.CATEGORY.name(), CellType.NUMERIC),
            new AbstractMap.SimpleEntry<String, CellType>(ColumnsNames.TITLE.name(), CellType.STRING),
            new AbstractMap.SimpleEntry<String, CellType>(ColumnsNames.DESCRIPTION.name(), CellType.STRING),
            new AbstractMap.SimpleEntry<String, CellType>(ColumnsNames.PRICE.name(), CellType.NUMERIC),
            new AbstractMap.SimpleEntry<String, CellType>(ColumnsNames.PRICE_OLD.name(), CellType.NUMERIC),
            new AbstractMap.SimpleEntry<String, CellType>(ColumnsNames.PRIME_COST.name(), CellType.NUMERIC),
            new AbstractMap.SimpleEntry<String, CellType>(ColumnsNames.QUANTITY.name(), CellType.NUMERIC),
            new AbstractMap.SimpleEntry<String, CellType>(ColumnsNames.WARRANTY.name(), CellType.NUMERIC),
            new AbstractMap.SimpleEntry<String, CellType>(ColumnsNames.DELIVERY_DATE.name(), CellType.STRING),
            new AbstractMap.SimpleEntry<String, CellType>(ColumnsNames.DELIVERY_TEXT.name(), CellType.STRING),
            new AbstractMap.SimpleEntry<String, CellType>(ColumnsNames.MANUFACTURER.name(), CellType.STRING),
            new AbstractMap.SimpleEntry<String, CellType>(ColumnsNames.IMAGES.name(), CellType.STRING),
            new AbstractMap.SimpleEntry<String, CellType>(ColumnsNames.ATTRIBUTES.name(), CellType.STRING),
            new AbstractMap.SimpleEntry<String, CellType>(ColumnsNames.VARIANTS.name(), CellType.STRING)
    );
}
