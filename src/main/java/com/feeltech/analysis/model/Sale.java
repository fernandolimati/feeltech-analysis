package com.feeltech.analysis.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fernando Lima
 * @date 26/10/2020
 **/

@Data
public class Sale {
    private String saleID;
    private List<SaleItem> saleItens;
    private String salesmanName;

    public Sale(String saleID, String item, String salesmanName){
        this.saleID = saleID;
        this.salesmanName = salesmanName;
        item = item.replaceAll("\\[","");
        item = item.replaceAll("\\]","");
        String[] itens = item.split(",");
        this.saleItens = new ArrayList<>();
        for(String i:itens) this.saleItens.add(new SaleItem(i));
    }

    public Double getTotalSaleValue(){
        Double totalValue = 0.d;
        for(SaleItem sale:saleItens) totalValue += sale.getItemPrice()*sale.getItemQtd();
        return totalValue;
    }

    public String getBiggerSaleID(){
        saleItens.sort((o1, o2) -> Double.compare(o2.getItemQtd()*o2.getItemPrice(), o1.getItemQtd()*o1.getItemPrice()));
        return (saleItens.get(0).getItemID());
    }

    public Double getBiggerSaleValue(){
        saleItens.sort((o1, o2) -> Double.compare(o2.getItemQtd()*o2.getItemPrice(), o1.getItemQtd()*o1.getItemPrice()));
        return (saleItens.get(0).getItemQtd()*saleItens.get(0).getItemPrice());
    }
}
