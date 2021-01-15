package edu.pw.ochronadanych.repository;

import edu.pw.ochronadanych.dto.NoteDTO;
import edu.pw.ochronadanych.entity.Note;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {

    @Query("select new edu.pw.ochronadanych.dto.NoteDTO(n) from Note n left join n.user u where " +
            "n.type = edu.pw.ochronadanych.enums.NoteType.PUBLIC or " +
            "(n.type = edu.pw.ochronadanych.enums.NoteType.PRIVATE and u.username = ?1) or " +
            "(n.type = edu.pw.ochronadanych.enums.NoteType.PROTECTED and u.username = ?1)")
    List<NoteDTO> getAllPublicAndAllLoggedUserNotes(String username);

}
