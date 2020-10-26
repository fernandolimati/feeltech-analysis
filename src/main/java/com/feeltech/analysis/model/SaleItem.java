package com.feeltech.analysis.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Fernando Lima
 * @date 26/10/2020
 **/

@Data
@AllArgsConstructor
public class SaleItem {
    private String itemID;
    private long itemQtd;
    private double itemPrice;

    public SaleItem(String item) {
        this.itemID = item.split("-")[0];
        this.itemQtd = Long.parseLong(item.split("-")[1]);
        this.itemPrice = Double.parseDouble(item.split("-")[2]);
    }
}
