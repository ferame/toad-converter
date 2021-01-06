package com.justincode.toad.converter.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Attribute {
    private String title;
//    TODO: naudoti <![CDATA[Galaxy SIII]]> jeigu viduje yra html atributai
    private String value;
}
