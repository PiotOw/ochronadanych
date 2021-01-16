package edu.pw.ochronadanych.repository;

import edu.pw.ochronadanych.dto.FileDTO;
import edu.pw.ochronadanych.entity.File;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends CrudRepository<File, Long> {

	// new edu.pw.ochronadanych.dto.FileDTO(f)
	@Query("select f from File f left join f.user u where u.username = ?1")
	List<File> getAllLoggedUserFiles(String username);

}
