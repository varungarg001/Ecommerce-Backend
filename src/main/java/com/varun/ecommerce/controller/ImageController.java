package com.varun.ecommerce.controller;


import com.varun.ecommerce.dto.ImageDto;
import com.varun.ecommerce.exception.ImageNotFounException;
import com.varun.ecommerce.model.Image;
import com.varun.ecommerce.response.ApiResponse;
import com.varun.ecommerce.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {

    private final IImageService imageService;


    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam long productId){
        try{
            List<ImageDto> imageDtos = imageService.saveImages(files, productId);
            return ResponseEntity.ok(new ApiResponse("Upload success!",imageDtos));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("upload Failed",e.getMessage()));
        }
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId ) throws SQLException {
        Image imageById = imageService.getImageById(imageId);
        ByteArrayResource resource=new ByteArrayResource(imageById.getImages().getBytes(1,(int)imageById.getImages().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(imageById.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\""+imageById.getFileName()+"\"")
                .body(resource);
    }


    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId,@RequestBody MultipartFile file){
        try{

            Image imageById = imageService.getImageById(imageId);
            if(imageById!=null){
                imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new ApiResponse("update Successfully! ",null));
            }
        }catch (ImageNotFounException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Update failed",null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("update failed! ",HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId){
        try{

            Image imageById = imageService.getImageById(imageId);
            if(imageById!=null){
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("Deleted Successfully! ",null));
            }
        }catch (ImageNotFounException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Delete failed",null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed! ",HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
