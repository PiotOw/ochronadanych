package edu.pw.ochronadanych.services.note;

import edu.pw.ochronadanych.dto.NoteDTO;
import edu.pw.ochronadanych.entity.Note;
import edu.pw.ochronadanych.entity.User;
import edu.pw.ochronadanych.repository.NoteRepository;
import edu.pw.ochronadanych.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteServiceImpl implements NoteService {

	private final NoteRepository noteRepository;
	private final UserRepository userRepository;

	@Override
	public NoteDTO addNote(NoteDTO note) {


		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		User loggedUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

		Note savedNote = noteRepository.save(Note.builder()
				.title(note.getTitle())
				.content(note.getContent())
				.type(note.getType())
				.user(loggedUser)
				.build());

		return NoteDTO.builder()
				.id(savedNote.getId())
				.title(savedNote.getTitle())
				.content(savedNote.getContent())
				.type(savedNote.getType())
				.build();

	}

	@Override
	public List<NoteDTO> getAllPublicAndAllLoggedUserNotes() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		return noteRepository.getAllPublicAndAllLoggedUserNotes(username);
	}
}
