package com.uol.compasso.productms.mapper;

import com.uol.compasso.productms.dto.ProductRequestDTO;
import com.uol.compasso.productms.dto.ProductResponseDTO;
import com.uol.compasso.productms.model.entity.Product;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {


    public static ProductResponseDTO productToDTO(Product product) {
        if (product == null) {
            return new ProductResponseDTO();
        }
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(product, ProductResponseDTO.class);
    }

    public static ArrayList<ProductResponseDTO> listProductDTOS(List<Product> productList) {
        ArrayList<ProductResponseDTO> productDTOS = new ArrayList<>();
        for (Product product : productList) {
            productDTOS.add(ProductMapper.productToDTO(product));
        }
        return productDTOS;
    }

    public static Product DTOtoProduct(ProductResponseDTO product) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(product, Product.class);
    }

    public static Product DTOCreatetoProduct(ProductRequestDTO product) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(product, Product.class);
    }
}
