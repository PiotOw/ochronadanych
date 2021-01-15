package edu.pw.ochronadanych.services.file;

import edu.pw.ochronadanych.dto.FileDTO;
import edu.pw.ochronadanych.entity.File;
import edu.pw.ochronadanych.entity.User;
import edu.pw.ochronadanych.repository.FileRepository;
import edu.pw.ochronadanych.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    @Override
    public FileDTO saveFile(FileDTO file) throws IOException, SQLException {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        User loggedUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));


        File savedFile = fileRepository.save(File.builder()
                .fileName(file.getFileName())
                .fileType(file.getContent().getContentType())
                .fileExtension(file.getFileExtension())
                .content(file.getContent().getBytes())
                .size(file.getContent().getSize())
                .user(loggedUser)
                .build());


        return FileDTO.builder()
                .id(savedFile.getId())
                .fileName(savedFile.getFileName())
                .fileType(savedFile.getFileType())
                .fileExtension(savedFile.getFileExtension())
                .fileSize(savedFile.getSize())
                .savedContent(Arrays.toString(savedFile.getContent()))
                .build();
    }

    @Override
    public List<FileDTO> getAllLoggedUserFiles() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        return fileRepository.getAllLoggedUserFiles(username).stream().map(FileDTO::new).collect(Collectors.toList());
    }
}
