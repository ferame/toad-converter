package com.justincode.toad.converter.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Data
public class Product {
    //    TODO: add a checker if it is indeed a url
    private String url;

    private Long id;
    private Optional<Long> barcode;

    private int category;

    //  TODO: add a surround as in here: <![CDATA[Galaxy SIII]]>
    private String description;
    //  TODO: add a surround as in here: <![CDATA[Galaxy SIII]]>
    private String title;
    //  TODO: add rounding to two decimal points
    private double price;
    //  TODO: add rounding to two decimal points
    private Optional<Double> oldPrice;
    //  TODO: add rounding to two decimal points
    private Optional<Double> primeCost;
    private int quantity;
//    Should be set to "-" (be kabuciu), if warranty is not applicable
    private Optional<Integer> warranty;

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
