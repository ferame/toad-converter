package com.justincode.toad.converter.formatters;

import com.justincode.toad.converter.dao.Attribute;
import com.justincode.toad.converter.dao.Image;
import com.justincode.toad.converter.dao.Product;
import com.justincode.toad.converter.dao.Variant;
import com.justincode.toad.converter.parser.constants.ColumnsNames;
import com.justincode.toad.converter.validators.GenericValidators;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class ParametersFormatter {
    public static Product createProduct(Map<String, String> parameters) {
        Optional<String> url = parameters.get(ColumnsNames.URL.name()).isEmpty() ?
                Optional.empty() : Optional.of(parameters.get(ColumnsNames.URL.name()));

        Long id = Long.parseLong(parameters.get(ColumnsNames.ID.name()));

        Optional<Long> barcode = parameters.get(ColumnsNames.BARCODE.name()).isEmpty() ?
                Optional.empty() :
                Optional.of(formatLong(parameters.get(ColumnsNames.BARCODE.name())));

        String category = parameters.get(ColumnsNames.CATEGORY.name());
        String title = parameters.get(ColumnsNames.TITLE.name());
        String description = parameters.get(ColumnsNames.DESCRIPTION.name());
        Double price = formatPrice(parameters.get(ColumnsNames.PRICE.name()));

        Optional<Double> priceOld = parameters.get(ColumnsNames.PRICE_OLD.name()).isEmpty() ?
                Optional.empty() :
                Optional.of(formatPrice(parameters.get(ColumnsNames.PRICE_OLD.name())));

        Optional<Double> primeCosts = parameters.get(ColumnsNames.PRIME_COST.name()).isEmpty() ?
                Optional.empty() :
                Optional.of(formatPrice(parameters.get(ColumnsNames.PRIME_COST.name())));

        int quantity = Integer.parseInt(parameters.get(ColumnsNames.QUANTITY.name()));

        Optional<String> warranty = formatWarranty(parameters.get(ColumnsNames.WARRANTY.name()));

        Optional<String> deliveryDate = parameters.get(ColumnsNames.DELIVERY_DATE.name()).isEmpty() ?
                Optional.empty() :
                Optional.of(parameters.get(ColumnsNames.DELIVERY_DATE.name()));

        Optional<String> deliveryText = parameters.get(ColumnsNames.DELIVERY_TEXT.name()).isEmpty() ?
                Optional.empty() :
                Optional.of(parameters.get(ColumnsNames.DELIVERY_TEXT.name()));

        String manufacturer = parameters.get(ColumnsNames.MANUFACTURER.name());

        List<Image> images = formatImages(parameters.get(ColumnsNames.IMAGES.name()));

        Optional<List<Attribute>> attributes = formatAttributes(parameters.get(ColumnsNames.ATTRIBUTES.name()));

        Optional<List<Variant>> variants = formatVariants(parameters.get(ColumnsNames.VARIANTS.name()));

        return new Product(url, id, barcode, category, title, description, price, priceOld, primeCosts, quantity, warranty, deliveryDate, deliveryText, manufacturer, images, attributes, variants);
    }

    private static Optional<List<Variant>> formatVariants(String variantsString) {
        Optional<List<Variant>> variants;
        if (variantsString.isEmpty()) {
            variants = Optional.empty();
        } else {
            String[] splitVariants = variantsString.substring(1, variantsString.length() - 1).split("]\\[");
            variants = Optional.of(Arrays.stream(splitVariants).map(variant -> {
                String[] items = variant.split(";");
                List<String> variantOptions = Arrays.stream(items, 1, items.length).map(String::trim).collect(Collectors.toList());
                return new Variant(items[0].trim(), variantOptions);
            }).collect(Collectors.toList()));
        }
        return variants;
    }

    private static Optional<List<Attribute>> formatAttributes(String attributesString) {
        Optional<List<Attribute>> attributes;
        if (attributesString.isEmpty()) {
            attributes = Optional.empty();
        } else {
            String[] splitAttributes = attributesString.substring(1, attributesString.length() - 1).split("]\\[");
            attributes = Optional.of(Arrays.stream(splitAttributes).map(attribute -> {
                String[] items = attribute.split(";");
                return new Attribute(items[0].trim(), items[1].trim());
            }).collect(Collectors.toList()));
        }
        return attributes;
    }

    private static List<Image> formatImages(String imagesString) {
        String[] imagesUrls = imagesString.substring(1, imagesString.length() - 1).split(";");
        return Arrays.stream(imagesUrls).map(Image::new).collect(Collectors.toList());
    }

    private static Optional<String> formatWarranty(String warrantyString) {
        Optional<String> warranty;
        if (warrantyString.equalsIgnoreCase("-")) {
            warranty = Optional.of(warrantyString);
        } else if (GenericValidators.validateIntField(warrantyString)) {
            warranty = Optional.of(warrantyString);
        } else {
            log.info("Wrong format of warranty: {}", warrantyString);
            warranty = Optional.empty();
        }
        return warranty;
    }

    private static Double formatPrice(String priceString) {
        double price = Double.parseDouble(priceString);
        BigDecimal roundedPrice = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);
        return roundedPrice.doubleValue();
    }

    private static Long formatLong(String longNumber) {
        BigDecimal bd = new BigDecimal(longNumber);
        return bd.longValue();
    }
}
