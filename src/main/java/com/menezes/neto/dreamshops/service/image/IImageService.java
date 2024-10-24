package com.menezes.neto.dreamshops.service.image;

import com.menezes.neto.dreamshops.dto.ImageDTO;
import com.menezes.neto.dreamshops.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getById(Long id);
    void deleteById(Long id);
    List<ImageDTO> save(Long productId, List<MultipartFile> files);
    void update(MultipartFile file, Long id);
}
