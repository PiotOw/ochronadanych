package edu.pw.ochronadanych.dto;

import edu.pw.ochronadanych.entity.Note;
import edu.pw.ochronadanych.enums.NoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteDTO {

    @Nullable
    private Long id;
    private String title;
    private String content;
    private NoteType type;

    public NoteDTO(Note note) {
        this.id = note.getId();
        this.title = note.getTitle();
        this.content = note.getContent();
        this.type = note.getType();
    }
}
