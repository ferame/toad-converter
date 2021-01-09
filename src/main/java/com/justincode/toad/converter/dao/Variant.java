package com.justincode.toad.converter.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Variant {
    private String title;
    private List<String> options;
}
