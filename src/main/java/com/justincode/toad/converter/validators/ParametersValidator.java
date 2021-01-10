package com.justincode.toad.converter.validators;

import com.justincode.toad.converter.parser.constants.ColumnsNames;
import com.justincode.toad.converter.parser.constants.ExcelColumnsConstants;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParametersValidator {
    public Map<String, String> checkProductParameters(Map<String, String> productParameters, List<String> columnsNames) {
        Map<String, String> checkedProductParameters = new HashMap<>();
        Map<String, String> checkedMandatoryColumns = checkMandatoryColumns(productParameters);
        Map<String, String> checkedOptionalColumns = checkOptionalColumns(productParameters);
        Map<String, String> checkedDeliveryColumns = checkDeliveryColumns(productParameters);
        if ((checkedMandatoryColumns.size() + checkedOptionalColumns.size() + checkedDeliveryColumns.size()) == productParameters.size()){
            checkedProductParameters.putAll(checkedMandatoryColumns);
            checkedProductParameters.putAll(checkedOptionalColumns);
            checkedProductParameters.putAll(checkedDeliveryColumns);
        }
        return checkedProductParameters;
    }

    private Map<String, String> checkMandatoryColumns(Map<String, String> productParameters) {
//        ID, CATEGORY, TITLE, DESCRIPTION, PRICE, QUANTITY, IMAGES, MANUFACTURER
        Map<String, String> checkedMandatoryColumns = new HashMap<>();
        List<String> productParametersNames = new ArrayList<>(productParameters.keySet());

        if (productParametersNames.containsAll(ExcelColumnsConstants.mandatoryColumns)) {
            if (GenericValidators.validateIntField(productParameters.get(ColumnsNames.ID.name()))){
                checkedMandatoryColumns.put(ColumnsNames.ID.name(), productParameters.get(ColumnsNames.ID.name()).trim());
            }
            if (GenericValidators.validateCategory(productParameters.get(ColumnsNames.CATEGORY.name()))){
                checkedMandatoryColumns.put(ColumnsNames.CATEGORY.name(), productParameters.get(ColumnsNames.CATEGORY.name()).trim());
            }
            if (!productParameters.get(ColumnsNames.TITLE.name()).isEmpty()){
                checkedMandatoryColumns.put(ColumnsNames.TITLE.name(), productParameters.get(ColumnsNames.TITLE.name()).trim());
            }
            if (!productParameters.get(ColumnsNames.DESCRIPTION.name()).isEmpty()){
                checkedMandatoryColumns.put(ColumnsNames.DESCRIPTION.name(), productParameters.get(ColumnsNames.DESCRIPTION.name()).trim());
            }
            if (GenericValidators.validateNumericField(productParameters.get(ColumnsNames.PRICE.name()))){
                checkedMandatoryColumns.put(ColumnsNames.PRICE.name(), productParameters.get(ColumnsNames.PRICE.name()).trim());
            }
            if (GenericValidators.validateIntField(productParameters.get(ColumnsNames.QUANTITY.name()))){
                checkedMandatoryColumns.put(ColumnsNames.QUANTITY.name(), productParameters.get(ColumnsNames.QUANTITY.name()).trim());
            }
            if (GenericValidators.validateImages(productParameters.get(ColumnsNames.IMAGES.name()))){
                checkedMandatoryColumns.put(ColumnsNames.IMAGES.name(), productParameters.get(ColumnsNames.IMAGES.name()).trim());
            }
            if (!productParameters.get(ColumnsNames.MANUFACTURER.name()).isEmpty()){
                checkedMandatoryColumns.put(ColumnsNames.MANUFACTURER.name(), productParameters.get(ColumnsNames.MANUFACTURER.name()).trim());
            }
        }
        return checkedMandatoryColumns;
    }

    private Map<String, String> checkOptionalColumns(Map<String, String> productParameters) {
//        URL, BARCODE, PRICE_OLD, PRIME_COST, WARRANTY, ATTRIBUTES, VARIANTS
        Map<String, String> checkedOptionalColumns = new HashMap<>();
        List<String> productParametersNames = new ArrayList<>(productParameters.keySet());

        if (productParametersNames.containsAll(ExcelColumnsConstants.optionalColumns)) {
            if (GenericValidators.validateUrl(productParameters.get(ColumnsNames.URL.name()))){
                checkedOptionalColumns.put(ColumnsNames.URL.name(), productParameters.get(ColumnsNames.URL.name()).trim());
            }
            if (GenericValidators.validateLongField(productParameters.get(ColumnsNames.BARCODE.name()))){
                checkedOptionalColumns.put(ColumnsNames.BARCODE.name(), productParameters.get(ColumnsNames.BARCODE.name()).trim());
            }
            if (GenericValidators.validateNumericField(productParameters.get(ColumnsNames.PRICE_OLD.name()))){
                checkedOptionalColumns.put(ColumnsNames.PRICE_OLD.name(), productParameters.get(ColumnsNames.PRICE_OLD.name()).trim());
            }
            if (GenericValidators.validateNumericField(productParameters.get(ColumnsNames.PRIME_COST.name()))){
                checkedOptionalColumns.put(ColumnsNames.PRIME_COST.name(), productParameters.get(ColumnsNames.PRIME_COST.name()).trim());
            }
            if (GenericValidators.validateIntField(productParameters.get(ColumnsNames.WARRANTY.name())) ||
                    productParameters.get(ColumnsNames.WARRANTY.name()).equalsIgnoreCase("-")){
                checkedOptionalColumns.put(ColumnsNames.WARRANTY.name(), productParameters.get(ColumnsNames.WARRANTY.name()).trim());
            }
            if (GenericValidators.validateAttributes(productParameters.get(ColumnsNames.ATTRIBUTES.name()))) {
                checkedOptionalColumns.put(ColumnsNames.ATTRIBUTES.name(), productParameters.get(ColumnsNames.ATTRIBUTES.name()).trim());
            }
            if (GenericValidators.validateVariants(productParameters.get(ColumnsNames.VARIANTS.name()))) {
                checkedOptionalColumns.put(ColumnsNames.VARIANTS.name(), productParameters.get(ColumnsNames.VARIANTS.name()).trim());
            }
        }
        return checkedOptionalColumns;
    }

    private Map<String, String> checkDeliveryColumns(Map<String, String> productParameters) {
        Map<String, String> checkedDeliveryColumns = new HashMap<>();
        if (new ArrayList<>(productParameters.keySet()).containsAll(ExcelColumnsConstants.dateColumns)){
            String deliveryDate = productParameters.get(ColumnsNames.DELIVERY_DATE.name()).trim();
            String deliveryText = productParameters.get(ColumnsNames.DELIVERY_TEXT.name()).trim();
            if (deliveryDate.isEmpty() && !deliveryText.isEmpty()){
                checkedDeliveryColumns.put(ColumnsNames.DELIVERY_DATE.name(), "");
                checkedDeliveryColumns.put(ColumnsNames.DELIVERY_TEXT.name(), deliveryText);
            } else if (!deliveryDate.isEmpty() && GenericValidators.validateDate(deliveryDate)){
                checkedDeliveryColumns.put(ColumnsNames.DELIVERY_DATE.name(), deliveryDate);
                checkedDeliveryColumns.put(ColumnsNames.DELIVERY_TEXT.name(), deliveryText);
            }
        }
        return checkedDeliveryColumns;
    }
}
