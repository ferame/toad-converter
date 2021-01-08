package com.justincode.toad.converter.parser.constants;

import java.util.EnumSet;

public final class ExcelColumnsConstants {
    private static EnumSet<ColumnsNames> mandatoryColumns =
            EnumSet.of(ColumnsNames.ID, ColumnsNames.CATEGORY, ColumnsNames.TITLE, ColumnsNames.DESCRIPTION,
                    ColumnsNames.PRICE, ColumnsNames.QUANTITY, ColumnsNames.IMAGES);

    private static EnumSet<ColumnsNames> optionalColumns =
            EnumSet.of(ColumnsNames.URL, ColumnsNames.BARCODE, ColumnsNames.PRICE_OLD, ColumnsNames.PRIME_COST,
                    ColumnsNames.WARRANTY, ColumnsNames.DELIVERY_DATE, ColumnsNames.ATTRIBUTES, ColumnsNames.VARIANTS);

    private static EnumSet<ColumnsNames> stringColumns =
            EnumSet.of(ColumnsNames.URL, ColumnsNames.CATEGORY, ColumnsNames.TITLE, ColumnsNames.DESCRIPTION,
                    ColumnsNames.MANUFACTURER, ColumnsNames.IMAGES, ColumnsNames.ATTRIBUTES, ColumnsNames.VARIANTS);
}
