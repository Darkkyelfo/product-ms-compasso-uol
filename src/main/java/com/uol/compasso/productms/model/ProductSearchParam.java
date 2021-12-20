package com.uol.compasso.productms.model;

import lombok.Data;

@Data
public class ProductSearchParam {

    private String searchText;
    private Double minPrice;
    private Double maxPrice;
    private Long pageNum;
    private int pageSize = 5;

    public boolean isValid() {
        return this.isSearchTextValid() || this.isMaxPriceValid() || this.isMinPriceValid();
    }

    public String getInvalidField() {
        if (!this.isMaxPriceValid()) {
            return "maxPrice";
        } else if (!this.isMinPriceValid()) {
            return "minPrice";
        } else if (!this.isSearchTextValid()) {
            return "q";
        }
        return null;
    }

    public boolean isMaxPriceValid() {
        return this.getMaxPrice() != null && this.getMaxPrice() >= 0;
    }

    public boolean isMinPriceValid() {
        return this.getMinPrice() != null && this.getMinPrice() >= 0;
    }

    public boolean isSearchTextValid() {
        return this.getSearchText() != null && !this.getSearchText().isEmpty();
    }

    public boolean hasPage() {
        return this.getPageNum() != null && this.getPageNum() > 0;
    }


    public int getPageNumFormated() {
        if (!this.hasPage()) {
            return 0;
        }
        return this.getPageNum().intValue() - 1;
    }

}
