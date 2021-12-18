package com.uol.compasso.productms.mapper;

import com.uol.compasso.productms.dto.ProductDTO;
import com.uol.compasso.productms.model.entity.Product;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {


    public static ProductDTO productToDTO(Product product) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(product, ProductDTO.class);
    }

    public static ArrayList<ProductDTO> listProductDTOS(List<Product> productList) {
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : productList) {
            productDTOS.add(ProductMapper.productToDTO(product));
        }
        return productDTOS;
    }

    public static Product DTOtoProduct(ProductDTO product) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(product, Product.class);
    }
}
