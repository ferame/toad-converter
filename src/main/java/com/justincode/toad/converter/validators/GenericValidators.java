package com.justincode.toad.converter.validators;

import org.apache.commons.validator.routines.UrlValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.regex.Pattern;

public class GenericValidators {
    public static boolean validateUrl(String url) {
        String[] schemes = {"http","https"}; // DEFAULT schemes = "http", "https", "ftp"
        UrlValidator urlValidator = new UrlValidator(schemes);
        return urlValidator.isValid(url);
    }

    public static boolean validateCategory(String category) {
        return Pattern.matches("^[a-z]+(-[a-z]+)*$", category);
    }

    public static boolean validateIntField(String strInt) {
        if (strInt == null) {
            return false;
        }
        try {
            Integer.parseInt(strInt);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean validateNumericField(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean validateImages(String images) {
        String[] imagesUrls = images.split(";");
        return imagesUrls.length > 0 && Arrays.stream(imagesUrls).allMatch(GenericValidators::validateUrl);
    }

    public static boolean validateAttributes(String attributes) {
        boolean isValid = false;
        if (!attributes.isEmpty() && attributes.startsWith("[") && attributes.endsWith("]")){
            String[] splitAttributes = attributes.substring(1, attributes.length() - 1).split("]\\[");
            isValid = Arrays.stream(splitAttributes).allMatch(attribute -> attribute.split(";").length == 2);
        }
        return isValid;
    }

    public static boolean validateVariants(String variants) {
        boolean isValid = false;
        if (!variants.isEmpty() && variants.startsWith("[") && variants.endsWith("]")) {
            String[] splitVariants = variants.substring(1, variants.length() - 1).split("]\\[");
            isValid = Arrays.stream(splitVariants).allMatch(variant -> variant.split(";").length >= 2);
        }
        return isValid;
    }

    public static boolean validateDate(String text) {
        if (text == null || !text.matches("\\d{4}-[01]\\d-[0-3]\\d"))
            return false;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);
        try {
            df.parse(text);
            return true;
        } catch (ParseException exception) {
            return false;
        }
    }
}