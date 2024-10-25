package com.menezes.neto.dreamshops.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fileName")
    private String fileName;
    @Column(name = "fileType")
    private String fileType;

    @Lob
    @Column(name = "image")
    private Blob image;
    @Column(name = "downloadUrl")
    private String downloadUrl;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
