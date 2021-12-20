package com.uol.compasso.productms.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductSearchParamTest {

    @Test
    void isValid() {
        //Caso valido
        ProductSearchParam productSearchParam = new ProductSearchParam();
        productSearchParam.setSearchText("testo da descricao ou do nome");
        productSearchParam.setMaxPrice(50D);
        productSearchParam.setMinPrice(20D);

        Assertions.assertTrue(productSearchParam.isValid());

        productSearchParam.setMinPrice(null);

        Assertions.assertTrue(productSearchParam.isValid());

        productSearchParam.setMinPrice(20D);
        productSearchParam.setMaxPrice(null);

        Assertions.assertTrue(productSearchParam.isValid());

        productSearchParam.setMinPrice(null);
        productSearchParam.setMaxPrice(null);

        Assertions.assertTrue(productSearchParam.isValid());

        productSearchParam.setSearchText(null);
        productSearchParam.setMaxPrice(50D);
        productSearchParam.setMinPrice(20D);

        Assertions.assertTrue(productSearchParam.isValid());

        productSearchParam = new ProductSearchParam();

        Assertions.assertFalse(productSearchParam.isValid());

    }

    @Test
    void getInvalidField() {
        ProductSearchParam productSearchParam = new ProductSearchParam();
        Assertions.assertEquals(productSearchParam.getInvalidField(), "maxPrice");
        productSearchParam.setMaxPrice(10D);
        Assertions.assertEquals(productSearchParam.getInvalidField(), "minPrice");
        productSearchParam.setMinPrice(10D);
        Assertions.assertEquals(productSearchParam.getInvalidField(), "q");
    }

    @Test
    void isMaxPriceValid() {
        ProductSearchParam productSearchParam = new ProductSearchParam();
        productSearchParam.setMaxPrice(50D);
        Assertions.assertTrue(productSearchParam.isMaxPriceValid());
        productSearchParam.setMaxPrice(null);
        Assertions.assertFalse(productSearchParam.isMaxPriceValid());
    }

    @Test
    void isMinPriceValid() {
        ProductSearchParam productSearchParam = new ProductSearchParam();
        productSearchParam.setMinPrice(50D);
        Assertions.assertTrue(productSearchParam.isMinPriceValid());
        productSearchParam.setMinPrice(null);
        Assertions.assertFalse(productSearchParam.isMinPriceValid());
    }

    @Test
    void isSearchTextValid() {
        ProductSearchParam productSearchParam = new ProductSearchParam();
        productSearchParam.setSearchText("Texto de teste");
        Assertions.assertTrue(productSearchParam.isSearchTextValid());
        productSearchParam.setSearchText(null);
        Assertions.assertFalse(productSearchParam.isSearchTextValid());
        productSearchParam.setSearchText("");
        Assertions.assertFalse(productSearchParam.isSearchTextValid());
    }

    @Test
    void getPageNumFormatedWhenNull() {
        ProductSearchParam productSearchParam = new ProductSearchParam();
        Assertions.assertEquals(productSearchParam.getPageNumFormated(), 0);
    }

    @Test
    void getPageNumFormatedWhenZeroOrMinus() {
        ProductSearchParam productSearchParam = new ProductSearchParam();
        productSearchParam.setPageNum(0L);
        Assertions.assertEquals(productSearchParam.getPageNumFormated(), 0);
        productSearchParam.setPageNum(-5L);
        Assertions.assertEquals(productSearchParam.getPageNumFormated(), 0);
    }

    @Test
    void getPageNumFormatedWhenGreaterZero() {
        Long page = 5L;
        ProductSearchParam productSearchParam = new ProductSearchParam();
        productSearchParam.setPageNum(page);
        Assertions.assertEquals(productSearchParam.getPageNumFormated(), page - 1);
    }

    @Test
    void hasPageWhenGreaterThanZero() {
        ProductSearchParam productSearchParam = new ProductSearchParam();
        productSearchParam.setPageNum(1L);
        Assertions.assertTrue(productSearchParam.hasPage());
    }

    @Test
    void hasPageWhenLessOrEqualZero() {
        ProductSearchParam productSearchParam = new ProductSearchParam();
        productSearchParam.setPageNum(0L);
        Assertions.assertFalse(productSearchParam.hasPage());
        productSearchParam.setPageNum(-2L);
        Assertions.assertFalse(productSearchParam.hasPage());
    }

    @Test
    void hasPageWhenIsNull() {
        ProductSearchParam productSearchParam = new ProductSearchParam();
        Assertions.assertFalse(productSearchParam.hasPage());
    }
}