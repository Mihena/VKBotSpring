package ru.mihena.VKBot.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Slf4j
@Table(name = "images")
public class ImageEntity {

    public ImageEntity(byte[] image, String type) {
        this.image = image;
        this.type = type;
    }

    public ImageEntity(Path path) {
        try {
            this.image = Files.readAllBytes(path);
            this.type = path.toString().split("\\.")[1];
        } catch (IOException e) {
            log.error("Cannot create image file " + e.getMessage());
        }
    }

    public ImageEntity(String path) {
        try {
            this.image = Files.readAllBytes(Path.of(path));
            this.type = path.split("\\.")[1];
        } catch (IOException e) {
            log.error("Cannot create image file " + e.getMessage());
        }
    }
    @Id
    @GeneratedValue(generator = "hibernate_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "hibernate_sequence", sequenceName = "hibernate_sequence",allocationSize = 1)
    private Integer id;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "image")
    private byte[] image;
}
