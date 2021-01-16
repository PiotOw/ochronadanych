package edu.pw.ochronadanych.controller;

import edu.pw.ochronadanych.dto.NoteDTO;
import edu.pw.ochronadanych.services.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class NoteController {

	private final NoteService noteService;

	@Value("${edu.app.apiResponseTimeoutSec}")
	private int apiTimeoutSec;

	@GetMapping()
	public ResponseEntity<List<NoteDTO>> getAllUserAndPublicNotes() throws InterruptedException {
		TimeUnit.SECONDS.sleep(apiTimeoutSec);
		return ResponseEntity.ok(noteService.getAllPublicAndAllLoggedUserNotes());
	}

	@PostMapping()
	public ResponseEntity<NoteDTO> saveNote(@RequestBody NoteDTO note) throws InterruptedException {
		TimeUnit.SECONDS.sleep(apiTimeoutSec);
		return ResponseEntity.ok(noteService.addNote(note));
	}
}
