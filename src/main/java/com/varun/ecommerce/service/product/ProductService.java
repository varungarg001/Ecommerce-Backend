package com.varun.ecommerce.service.product;

import com.varun.ecommerce.dto.ImageDto;
import com.varun.ecommerce.dto.ProductDto;
import com.varun.ecommerce.exception.ProductNotFoundException;
import com.varun.ecommerce.model.Category;
import com.varun.ecommerce.model.Image;
import com.varun.ecommerce.model.Product;
import com.varun.ecommerce.repository.CategoryRepo;
import com.varun.ecommerce.repository.ImageRepo;
import com.varun.ecommerce.repository.ProductRepo;
import com.varun.ecommerce.request.AddProductRequest;
import com.varun.ecommerce.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{


    private final ProductRepo productRepo;

    private final CategoryRepo categoryRepo;

    private final ModelMapper modelMapper;

    private final ImageRepo imageRepo;

    @Override
    public Product addProduct(AddProductRequest product) {
        // find the category in the db,
        // if it found , then add the product to it
        // if no, then create a new category and then save the product
        Category category= Optional.ofNullable(categoryRepo.findByName(product.getCategory().getName()))
                .orElseGet(()->{
                    Category newCategory=new Category(product.getCategory().getName());
                    return categoryRepo.save(newCategory);
                });

        product.setCategory(category);
        return productRepo.save(createProduct(product,category));
    }

    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category

        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepo.findById(id)
                .orElseThrow(()->new ProductNotFoundException("product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepo.findById(id).ifPresentOrElse(productRepo::delete,()-> {
            throw new ProductNotFoundException("product not found");
        });

    }

    @Override
    public Product updateProduct(UpdateProductRequest product, Long productId) {
        return productRepo.findById(productId)
                .map(existingProduct->updateExistingProduct(existingProduct,product))
                .map(productRepo::save)
                .orElseThrow(()->new ProductNotFoundException("product not found"));
    }

    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setDescription(request.getDescription());
        Category category = categoryRepo.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> getAllProductByCategoryName(String categoryName) {
        return productRepo
                .findAll()
                .stream()
                .filter(product -> product.getCategory().getName().equalsIgnoreCase(categoryName))
                .toList();
    }

    @Override
    public List<Product> getAllProductByBrand(String brandName) {
        return productRepo
                .findAll()
                .stream()
                .filter(product -> product.getBrand().equals(brandName))
                .toList();
    }

    @Override
    public List<Product> getAllProductByCategoryAndBrand(String category, String brandName) {
        return productRepo.findByCategoryNameAndBrand(category,brandName);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepo
                .findAll()
                .stream()
                .filter(product -> product.getName().equals(name))
                .toList();
    }

    @Override
    public List<Product> getProductByBrandAndName(String brandName, String productName) {
        return productRepo.findByBrandAndName(brandName, productName);
    }

    @Override
    public Long countProductByBrandAndName(String brandName, String name) {
        return productRepo.countByBrandAndName(brandName,name);
    }

    @Override
    public List<ProductDto> getConvertedToProductDto(List<Product> products){
        return products.stream().map(product->convertToProductDto(product)).toList();
    }

    @Override
    public ProductDto convertToProductDto(Product product){
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images=imageRepo.findByProductId(product.getId());

        List<ImageDto> imageDtos=images
                .stream()
                .map(image-> modelMapper.map(image,ImageDto.class))
                .toList();

        productDto.setImages(imageDtos);
        return productDto;
    }
}
