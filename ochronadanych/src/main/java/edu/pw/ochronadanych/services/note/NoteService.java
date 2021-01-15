package edu.pw.ochronadanych.services.note;

import edu.pw.ochronadanych.dto.NoteDTO;
import org.springframework.lang.Nullable;

import java.util.List;

public interface NoteService {
    NoteDTO addNote(NoteDTO note);

    List<NoteDTO> getAllPublicAndAllLoggedUserNotes();
}
