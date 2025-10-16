package com.varun.ecommerce.service.product;

import com.varun.ecommerce.dto.ProductDto;
import com.varun.ecommerce.model.Product;
import com.varun.ecommerce.request.AddProductRequest;
import com.varun.ecommerce.request.UpdateProductRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(UpdateProductRequest product, Long productId);
    List<Product> getAllProduct();
    List<Product> getAllProductByCategoryName(String categoryName);
    List<Product> getAllProductByBrand(String brandName);
    List<Product> getAllProductByCategoryAndBrand(String category,String brandName);
    List<Product> getProductByName(String name);
    List<Product> getProductByBrandAndName(String brandName,String productName);
    Long countProductByBrandAndName(String brandName,String name);

    List<ProductDto> getConvertedToProductDto(List<Product> products);

    ProductDto convertToProductDto(Product product);
}
