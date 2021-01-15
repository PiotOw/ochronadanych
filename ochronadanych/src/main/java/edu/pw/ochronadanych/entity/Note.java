package edu.pw.ochronadanych.entity;

import edu.pw.ochronadanych.enums.NoteType;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "notes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank
    private String title;

    @Column(columnDefinition = "text")
    @NotBlank
    private String content;

    @Column
    @NotBlank
    private NoteType type;

    @OneToOne(targetEntity = User.class)
    private User user;


}
