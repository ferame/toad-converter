package com.justincode.toad.converter.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Data
public class Product { // V1A_2
    //    TODO: add a checker if it is indeed a url
    private Optional<String> url;

    private Long id;
    private Optional<Long> barcode;

    // TODO: adda a check for it to be all lowercase and separated by dashes as mobile-phones
    private String category;

    //  TODO: add a surround as in here: <![CDATA[Galaxy SIII]]>
    private String title;
    //  TODO: add a surround as in here: <![CDATA[Galaxy SIII]]>
    private String description;
    //  TODO: add rounding to two decimal points
    private double price;
    //  TODO: add rounding to two decimal points
    private Optional<Double> oldPrice;
    //  TODO: add rounding to two decimal points
    private Optional<Double> primeCost;
    private int quantity;
    //    Should be set to "-" (be kabuciu), if warranty is not applicable
    private Optional<String> warranty;

    //    Nurodyti arba tikslia data arba tekstine forma
    private Optional<String> deliveryDate;
    //    Pristatymo data, matoma prie kiekvienos prekes [Privalomas - jeigu nenurodyta tiksli data]
    private Optional<String> deliveryText;
    //    Keep blank if there is no manufacturer
    private String manufacturer;

    //    Has to be at least one image, first image is the main one
    private List<Image> images;

    private Optional<List<Attribute>> attributes;
    private Optional<List<Variant>> variants;
}
