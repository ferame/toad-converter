package com.justincode.toad.converter.parser.constants;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ExcelColumnsConstants {
    public static List<String> mandatoryColumns = Stream.of(ColumnsNames.ID, ColumnsNames.CATEGORY,
            ColumnsNames.TITLE, ColumnsNames.DESCRIPTION, ColumnsNames.PRICE, ColumnsNames.QUANTITY,
            ColumnsNames.IMAGES, ColumnsNames.MANUFACTURER)
            .map(Enum::name)
            .collect(Collectors.toList());

    public static List<String> optionalColumns = Stream.of(ColumnsNames.URL, ColumnsNames.BARCODE,
            ColumnsNames.PRICE_OLD, ColumnsNames.PRIME_COST, ColumnsNames.WARRANTY,
            ColumnsNames.ATTRIBUTES, ColumnsNames.VARIANTS)
            .map(Enum::name)
            .collect(Collectors.toList());

    public static List<String> dateColumns = Stream.of(ColumnsNames.DELIVERY_DATE, ColumnsNames.DELIVERY_TEXT)
            .map(Enum::name)
            .collect(Collectors.toList());
}
