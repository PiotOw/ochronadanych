package edu.pw.ochronadanych.controller;

import edu.pw.ochronadanych.dto.FileDTO;
import edu.pw.ochronadanych.dto.NoteDTO;
import edu.pw.ochronadanych.services.file.FileService;
import edu.pw.ochronadanych.services.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class FileController {

	private final FileService fileService;

	@Value("${edu.app.apiResponseTimeoutSec}")
	private int apiTimeoutSec;


	@GetMapping()
	public ResponseEntity<List<FileDTO>> getAllLoggedUserFiles() throws InterruptedException {
		TimeUnit.SECONDS.sleep(apiTimeoutSec);
		return ResponseEntity.ok(fileService.getAllLoggedUserFiles());
	}

	@PostMapping()
	public ResponseEntity<FileDTO> saveFile(@ModelAttribute FileDTO file) throws IOException, SQLException, InterruptedException {
		TimeUnit.SECONDS.sleep(apiTimeoutSec);
		return ResponseEntity.ok(fileService.saveFile(file));
	}
}
