package com.varun.ecommerce.service.image;


import com.varun.ecommerce.dto.ImageDto;
import com.varun.ecommerce.exception.ImageNotFounException;
import com.varun.ecommerce.model.Image;
import com.varun.ecommerce.model.Product;
import com.varun.ecommerce.repository.ImageRepo;
import com.varun.ecommerce.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final IProductService productService;
    private final ImageRepo imageRepo;


    @Override
    public Image getImageById(Long id) {
        return imageRepo.findById(id).orElseThrow(()->new ImageNotFounException("Image is not present with id "+id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepo.findById(id).ifPresentOrElse(imageRepo::delete,()->{
            throw new ImageNotFounException("Image is not present with the id "+id);
        });
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product productById = productService.getProductById(productId);
        List<ImageDto>imagesDtos=new ArrayList<>();
        for(MultipartFile file:files){
            try{
                Image image=new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImages(new SerialBlob(file.getBytes()));

                image.setProduct(productById);

                String buildUrl="/api/v1/images/image/download/";
                String downloadUrl=buildUrl+image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage = imageRepo.save(image);
                savedImage.setDownloadUrl(buildUrl+savedImage.getId());
                imageRepo.save(savedImage);

                ImageDto imageDto=new ImageDto();
                imageDto.setId(savedImage.getId());
                imageDto.setFileName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());

                imagesDtos.add(imageDto);



            }catch (IOException | SQLException e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return imagesDtos;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image=getImageById(imageId);
        try{
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImages(new SerialBlob(file.getBytes()));
            imageRepo.save(image);
        }catch(Exception e){
            throw new ImageNotFounException("Image is not present with id "+imageId);
        }
    }
}
