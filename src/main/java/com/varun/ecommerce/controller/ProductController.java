package com.varun.ecommerce.controller;

import com.varun.ecommerce.dto.ProductDto;
import com.varun.ecommerce.exception.ProductNotFoundException;
import com.varun.ecommerce.model.Product;
import com.varun.ecommerce.request.AddProductRequest;
import com.varun.ecommerce.request.UpdateProductRequest;
import com.varun.ecommerce.response.ApiResponse;
import com.varun.ecommerce.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}")
@RequiredArgsConstructor
public class ProductController {

    static final String SUCCESS="success";
    static final String NOPRODUCT="NO Product Found";
    private final IProductService productService;



    @GetMapping("/product/all")
    public ResponseEntity<ApiResponse> getAllProduct(){
        try {
            List<Product> allProduct = productService.getAllProduct();
            if(allProduct.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(NOPRODUCT,null));
            }
            List<ProductDto> productDtos=productService.getConvertedToProductDto(allProduct);
            return ResponseEntity.ok(new ApiResponse("All product",productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Internal server error ",null));
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){
        try {
            Product productById = productService.getProductById(productId);
            ProductDto productDto=productService.convertToProductDto(productById);
            return ResponseEntity.ok(new ApiResponse("success! product found ",productDto));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/product/add")
    public ResponseEntity<ApiResponse> addNewProduct(@RequestBody AddProductRequest product){
        try {
            Product addedProduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Product Added Successfully",addedProduct));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }

    }


    @PutMapping("/product/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductRequest updatedProduct, @PathVariable Long productId){
        try {
            Product result = productService.updateProduct(updatedProduct, productId);
            return ResponseEntity.ok(new ApiResponse("updated successfully",result));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @DeleteMapping("/product/delete/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("deleted successfully",null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/product/{brand}/{productName}")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@PathVariable String brand,@PathVariable String productName){
        try {
            List<Product> productByBrandAndName = productService.getProductByBrandAndName(brand, productName);
            if(!productByBrandAndName.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(NOPRODUCT,null));
            }
            List<ProductDto> productDtos = productService.getConvertedToProductDto(productByBrandAndName);
            return ResponseEntity.ok(new ApiResponse(SUCCESS,productDtos));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("product/by-category-brand/{categoryName}/{brandName}")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@PathVariable String categoryName,@PathVariable String brandName){
        try {
            List<Product> allProductByCategoryAndBrand = productService.getAllProductByCategoryAndBrand(categoryName, brandName);
            if(!allProductByCategoryAndBrand.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(NOPRODUCT,null));
            }
            List<ProductDto> productDtos = productService.getConvertedToProductDto(allProductByCategoryAndBrand);
            return ResponseEntity.ok(new ApiResponse(SUCCESS,productDtos));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ApiResponse(e.getMessage(),null));
        }
    }


    @GetMapping("product/productname/{productName}")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String productName){
        try {
            List<Product> productByName = productService.getProductByName(productName);
            if(!productByName.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(NOPRODUCT,null));
            }
            List<ProductDto> productDtos = productService.getConvertedToProductDto(productByName);
            return ResponseEntity.ok(new ApiResponse(SUCCESS,productDtos));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("product/brand/{brandName}")
    public ResponseEntity<ApiResponse> getProductByBrandName(@PathVariable String brandName){
        try {
            List<Product> productByBrandName = productService.getAllProductByBrand(brandName);
            if(!productByBrandName.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Product Found",null));
            }
            List<ProductDto> productDtos = productService.getConvertedToProductDto(productByBrandName);
            return ResponseEntity.ok(new ApiResponse(SUCCESS,productDtos));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("product/category/{categoryName}")
    public ResponseEntity<ApiResponse> getProductByCategoryName(@PathVariable String categoryName){
        try {
            List<Product> productByCategoryName = productService.getAllProductByCategoryName(categoryName);
            if(!productByCategoryName.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Product Found",null));
            }
            List<ProductDto> productDtos = productService.getConvertedToProductDto(productByCategoryName);
            return ResponseEntity.ok(new ApiResponse(SUCCESS,productDtos));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("product/count/{brandName}/{productName}")
    public ResponseEntity<ApiResponse> countProductByBrandNameAndProductName(@PathVariable String brandName ,@PathVariable String productName){
        try {
            Long count = productService.countProductByBrandAndName(brandName,productName);
            return ResponseEntity.ok(new ApiResponse(SUCCESS,count));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ApiResponse(e.getMessage(),null));
        }
    }






}
