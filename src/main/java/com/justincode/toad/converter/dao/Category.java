package com.justincode.toad.converter.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@AllArgsConstructor
@Data
public class Category {
    private Long id;
    private Optional<Long> parentId;
    private String name;
}
