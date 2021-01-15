package edu.pw.ochronadanych.controller;

import edu.pw.ochronadanych.dto.FileDTO;
import edu.pw.ochronadanych.dto.NoteDTO;
import edu.pw.ochronadanych.services.file.FileService;
import edu.pw.ochronadanych.services.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class FileController {


    // pliki tylko swoje -- wszystkie

    // zapis pojedynczego pliku  -- fileName, fileType, content

    private final FileService fileService;

    @GetMapping()
    public ResponseEntity<List<FileDTO>> getAllLoggedUserFiles() {
        return ResponseEntity.ok(fileService.getAllLoggedUserFiles());
    }

    @PostMapping()
    public ResponseEntity<FileDTO> saveFile(@ModelAttribute FileDTO file) throws IOException, SQLException {
        return ResponseEntity.ok(fileService.saveFile(file));
    }


    // opoznienie w aotoryzacji


    // zwrotka z proba logowania, po 3 probie blokada na 10 minut


    // spring wpięcie ssl



}
