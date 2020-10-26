package com.feeltech.analysis.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Fernando Lima
 * @date 26/10/2020
 **/

@Data
@AllArgsConstructor
public class Client {
    private String cnpj;
    private String name;
    private String businessArea;
}
