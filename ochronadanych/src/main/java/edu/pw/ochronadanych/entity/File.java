package edu.pw.ochronadanych.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Clob;

@Entity
@Table(name = "files")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fileName;

    @Column
    private String fileType;

    @Column
    private String fileExtension;

    @Column
    private Long size;

//    @Lob
    @Column(name = "content", columnDefinition="bytea")
    private byte[] content;

    @OneToOne(targetEntity = User.class)
    private User user;
}
