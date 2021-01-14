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

        Optional<String> url = isValuePresent(parameters, ColumnsNames.URL) ?
                Optional.of(parameters.get(ColumnsNames.URL.name())) :
                Optional.empty();

        Long id = Long.parseLong(parameters.get(ColumnsNames.ID.name()));

        Optional<Long> barcode = isValuePresent(parameters, ColumnsNames.BARCODE) ?
                Optional.of(formatLong(parameters.get(ColumnsNames.BARCODE.name()))) :
                Optional.empty();

        String category = parameters.get(ColumnsNames.CATEGORY.name());
        String title = parameters.get(ColumnsNames.TITLE.name());
        String description = formatText(parameters.get(ColumnsNames.DESCRIPTION.name()));
        Double price = formatPrice(parameters.get(ColumnsNames.PRICE.name()));

        Optional<Double> priceOld = isValuePresent(parameters, ColumnsNames.PRICE_OLD) ?
                Optional.of(formatPrice(parameters.get(ColumnsNames.PRICE_OLD.name()))) :
                Optional.empty();

        Optional<Double> primeCosts = isValuePresent(parameters, ColumnsNames.PRIME_COST) ?
                Optional.of(formatPrice(parameters.get(ColumnsNames.PRIME_COST.name()))) :
                Optional.empty();

        int quantity = Integer.parseInt(parameters.get(ColumnsNames.QUANTITY.name()));

        Optional<String> warranty = isValuePresent(parameters, ColumnsNames.WARRANTY) ?
                formatWarranty(parameters.get(ColumnsNames.WARRANTY.name())) :
                Optional.empty();

        Optional<String> deliveryDate = isValuePresent(parameters, ColumnsNames.DELIVERY_DATE) ?
                Optional.of(parameters.get(ColumnsNames.DELIVERY_DATE.name())) :
                Optional.empty();

        Optional<String> deliveryText = isValuePresent(parameters, ColumnsNames.DELIVERY_TEXT) ?
                Optional.of(parameters.get(ColumnsNames.DELIVERY_TEXT.name())) :
                Optional.empty();

        String manufacturer = parameters.get(ColumnsNames.MANUFACTURER.name());

        List<Image> images = formatImages(parameters.get(ColumnsNames.IMAGES.name()));

        Optional<List<Attribute>> attributes = isValuePresent(parameters, ColumnsNames.ATTRIBUTES) ?
                formatAttributes(parameters.get(ColumnsNames.ATTRIBUTES.name())) :
                Optional.empty();

        Optional<List<Variant>> variants = isValuePresent(parameters, ColumnsNames.VARIANTS) ?
                formatVariants(parameters.get(ColumnsNames.VARIANTS.name())) :
                Optional.empty();

        return new Product(url, id, barcode, category, title, description, price, priceOld, primeCosts, quantity, warranty, deliveryDate, deliveryText, manufacturer, images, attributes, variants);
    }

    private static boolean isValuePresent(Map<String, String> parameters, ColumnsNames field) {
        return parameters.containsKey(field.name()) && !parameters.get(field.name()).isEmpty();
    }

    private static String formatText(String text) {
        return text.startsWith("[") && text.endsWith("]") ?
                text.substring(1, text.length() - 1) :
                text;
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
