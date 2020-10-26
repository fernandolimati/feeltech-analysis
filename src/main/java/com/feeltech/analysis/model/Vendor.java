package com.feeltech.analysis.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Fernando Lima
 * @date 26/10/2020
 **/

@Data
@AllArgsConstructor
public class Vendor implements Serializable {
    private String cpf;
    private String name;
    private double salary;
}
