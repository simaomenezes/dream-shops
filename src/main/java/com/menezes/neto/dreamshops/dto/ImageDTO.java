package com.menezes.neto.dreamshops.dto;

import com.menezes.neto.dreamshops.model.Product;
import lombok.Data;

import java.sql.Blob;

@Data
public class ImageDTO {
    private Long id;
    private String fileName;
    private String downloadUrl;

}
