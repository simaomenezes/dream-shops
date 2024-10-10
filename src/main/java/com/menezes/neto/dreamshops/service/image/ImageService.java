package com.menezes.neto.dreamshops.service.image;

import com.menezes.neto.dreamshops.dto.ImageDTO;
import com.menezes.neto.dreamshops.model.Image;
import com.menezes.neto.dreamshops.model.Product;
import com.menezes.neto.dreamshops.repository.ImageRepository;
import com.menezes.neto.dreamshops.service.product.IProductService;
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
public class ImageService implements IImageService{

    private final ImageRepository repository;
    private final IProductService productService;

    @Override
    public Image getById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<ImageDTO> save(Long productId, List<MultipartFile> files) {
        Product product = productService.getById(productId);
        List<ImageDTO> imageDTOS = new ArrayList<>();
        for (MultipartFile file : files){
            try {

                Image image = new Image();
                image.setImage(new SerialBlob(file.getBytes()));
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl+image.getId();
                image.setDownloadUrl(downloadUrl);

                Image imageSaved = repository.save(image);
                imageSaved.setDownloadUrl(buildDownloadUrl+imageSaved.getId());
                ImageDTO imageDTO = new ImageDTO();
                imageDTO.setId(imageSaved.getId());
                imageDTO.setDownloadUrl(imageSaved.getDownloadUrl());
                imageDTO.setFileName(imageSaved.getFileName());
                imageDTOS.add(imageDTO);

            } catch (IOException | SQLException e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return imageDTOS;
    }

    @Override
    public void update(MultipartFile file, Long id) {

    }
}
