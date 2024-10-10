package com.menezes.neto.dreamshops.service.image;

import com.menezes.neto.dreamshops.dto.ImageDTO;
import com.menezes.neto.dreamshops.model.Image;
import com.menezes.neto.dreamshops.repository.ImageRepository;
import com.menezes.neto.dreamshops.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{

    private final ImageRepository repository;
    private final ProductRepository productRepository;

    @Override
    public Image getById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<ImageDTO> save(Long productId, List<MultipartFile> files) {
        return List.of();
    }

    @Override
    public void update(MultipartFile file, Long id) {

    }
}
