package com.menezes.neto.dreamshops.controller;

import com.menezes.neto.dreamshops.dto.ImageDTO;
import com.menezes.neto.dreamshops.exceptions.ResourceNotFoundException;
import com.menezes.neto.dreamshops.model.Image;
import com.menezes.neto.dreamshops.response.ApiResponse;
import com.menezes.neto.dreamshops.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final IImageService service;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> save(@RequestParam List<MultipartFile> files, @RequestParam Long productId){
        try {
            List<ImageDTO> imagesDTOSaved = service.save(productId, files);
            return ResponseEntity.ok(new ApiResponse("success", imagesDTOSaved));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("upload failed!", e.getMessage()));
        }
    }

    @PutMapping("/image/{id}/update")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody MultipartFile file){
        try {
            Image imgFound = service.getById(id);
            if(imgFound != null){
                service.update(file, id);
                return ResponseEntity.ok(new ApiResponse("success", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed", null));
    }

    @DeleteMapping("/image/{id}/delete")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id){
        try {
            Image imageFound = service.getById(id);
            if(imageFound != null){
                service.deleteById(id);
                return ResponseEntity.ok(new ApiResponse("success", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed", null));
    }

    @GetMapping("/image/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id){
        Image imgFound = service.getById(id);
        ByteArrayResource resource = null;
        ResponseEntity<Resource> returnResponse = null;
        try {
            if(imgFound != null){
                resource = new ByteArrayResource(imgFound.getImage().getBytes(1, (int) imgFound.getImage().length()));
                returnResponse = ResponseEntity.ok().contentType(MediaType.parseMediaType(imgFound.getFileType()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imgFound.getFileName() + "\"").body(resource);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return returnResponse;
    }
}
