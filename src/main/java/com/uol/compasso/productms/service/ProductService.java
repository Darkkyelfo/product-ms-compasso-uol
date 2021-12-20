package com.uol.compasso.productms.service;

import com.uol.compasso.productms.dto.ProductRequestDTO;
import com.uol.compasso.productms.dto.ProductResponseDTO;
import com.uol.compasso.productms.exception.ParamsInvalidException;
import com.uol.compasso.productms.exception.ProductNotFoundException;
import com.uol.compasso.productms.mapper.ProductMapper;
import com.uol.compasso.productms.model.ProductSearchParam;
import com.uol.compasso.productms.model.entity.Product;
import com.uol.compasso.productms.repository.SearchCriteria;
import com.uol.compasso.productms.repository.product.ProductRepository;
import com.uol.compasso.productms.repository.product.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.uol.compasso.productms.util.GeneralMessages.SUCCESS_TO_DELETE;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductResponseDTO> getAll() {
        return ProductMapper.listProductDTOS(this.productRepository.findAll(Sort.by("id")));
    }

    public List<ProductResponseDTO> getAll(int pageNum, int size) {
        Pageable page = PageRequest.of(pageNum - 1, size, Sort.by("id"));
        return ProductMapper.listProductDTOS(this.productRepository.findAll(page).toList());
    }

    public ProductResponseDTO getOne(Long id) throws ProductNotFoundException {
        Product product = this.productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        return ProductMapper.productToDTO(product);

    }

    public List<ProductResponseDTO> searchItens(ProductSearchParam searchParams) throws ParamsInvalidException {

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
        if (searchParams.hasPage()) {
            Pageable page = PageRequest.of(searchParams.getPageNumFormated(), searchParams.getPageSize(), Sort.by("id"));
            return ProductMapper.listProductDTOS(this.productRepository.findAll(specification, page).toList());
        }
        return ProductMapper.listProductDTOS(this.productRepository.findAll(specification));
    }

    public ProductResponseDTO insertProduct(ProductRequestDTO productDTO) {
        Product product = this.productRepository.save(ProductMapper.DTOCreatetoProduct(productDTO));
        return ProductMapper.productToDTO(product);
    }

    public String deleteProduct(Long id) throws ProductNotFoundException {
        Product product = this.productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        this.productRepository.delete(product);
        return SUCCESS_TO_DELETE.get();
    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productDTO) throws ProductNotFoundException {
        Product product = this.productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product = this.productRepository.save(product);
        return ProductMapper.productToDTO(product);

    }
}
