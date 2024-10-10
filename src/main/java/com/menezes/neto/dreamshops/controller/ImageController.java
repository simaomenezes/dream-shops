package com.menezes.neto.dreamshops.controller;

import com.menezes.neto.dreamshops.dto.ImageDTO;
import com.menezes.neto.dreamshops.response.ApiResponse;
import com.menezes.neto.dreamshops.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

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
}
