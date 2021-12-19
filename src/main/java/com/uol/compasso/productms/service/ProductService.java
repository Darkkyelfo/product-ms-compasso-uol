package com.uol.compasso.productms.service;

import com.uol.compasso.productms.dto.ProductDTO;
import com.uol.compasso.productms.exception.ParamsInvalidException;
import com.uol.compasso.productms.exception.ProductNotFoundException;
import com.uol.compasso.productms.mapper.ProductMapper;
import com.uol.compasso.productms.model.ProductSearchParam;
import com.uol.compasso.productms.model.entity.Product;
import com.uol.compasso.productms.repository.product.ProductRepository;
import com.uol.compasso.productms.repository.product.ProductSpecification;
import com.uol.compasso.productms.repository.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.uol.compasso.productms.util.GeneralMessages.SUCCESS_TO_DELETE;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> getAll() {
        return ProductMapper.listProductDTOS(this.productRepository.findAll());
    }

    public ProductDTO getOne(Long id) throws ProductNotFoundException {
        Product product = this.productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        return ProductMapper.productToDTO(product);

    }

    public List<ProductDTO> searchItens(ProductSearchParam searchParams) throws ParamsInvalidException {

        if (!searchParams.isValid()) {
            throw new ParamsInvalidException(searchParams.getInvalidField());
        }
        Specification<Product> specification = null;
        if (searchParams.isSearchTextValid()) {
            specification = Specification.where(new ProductSpecification(new SearchCriteria("description", "like", searchParams.getSearchText())))
                    .or(new ProductSpecification(new SearchCriteria("name", "like", searchParams.getSearchText())));
        }
        if (searchParams.isMaxPriceValid()) {
            if (specification != null) {
                specification = specification.and(new ProductSpecification(new SearchCriteria("price", "<=", searchParams.getMaxPrice())));
            } else {
                specification = Specification.where(new ProductSpecification(new SearchCriteria("price", "<=", searchParams.getMaxPrice())));
            }
        }
        if (searchParams.isMinPriceValid()) {
            if (specification != null) {
                specification = specification.and(new ProductSpecification(new SearchCriteria("price", ">=", searchParams.getMinPrice())));
            } else {
                specification = Specification.where(new ProductSpecification(new SearchCriteria("price", ">=", searchParams.getMinPrice())));
            }
        }

        return ProductMapper.listProductDTOS(this.productRepository.findAll(specification));
    }

    public ProductDTO insertProduct(ProductDTO productDTO) {
        Product product = this.productRepository.save(ProductMapper.DTOtoProduct(productDTO));
        productDTO.setId(product.getId());
        return productDTO;
    }

    public String deleteProduct(Long id) throws ProductNotFoundException {
        Product product = this.productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        this.productRepository.delete(product);
        return SUCCESS_TO_DELETE.get();
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) throws ProductNotFoundException {
        Product product = this.productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        return ProductMapper.productToDTO(this.productRepository.save(product));

    }
}
