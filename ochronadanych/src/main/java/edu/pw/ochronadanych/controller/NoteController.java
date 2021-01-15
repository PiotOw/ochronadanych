package edu.pw.ochronadanych.controller;

import edu.pw.ochronadanych.dto.NoteDTO;
import edu.pw.ochronadanych.services.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class NoteController {

    // swoje + wszystkie publiczne notatki -- wszystkie public, private i protected dla zalegowanego uzytkownika

    // zapis notatki  - tytul, tresc, typ: publiczna, prywatna (tylko dla uzytkownika zalogownaeog), zaszyfrowana
//                        title  content  type

    private final NoteService noteService;

        @GetMapping()
    public ResponseEntity<List<NoteDTO>> getAllUserAndPublicNotes() {
        return ResponseEntity.ok(noteService.getAllPublicAndAllLoggedUserNotes());
    }

    @PostMapping()
    public ResponseEntity<NoteDTO> saveNote(@RequestBody NoteDTO note) {
        return ResponseEntity.ok(noteService.addNote(note));
    }


    // opoznienie w aotoryzacji


    // zwrotka z proba logowania, po 3 probie blokada na 10 minut
}
