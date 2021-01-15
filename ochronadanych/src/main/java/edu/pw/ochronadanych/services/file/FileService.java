package edu.pw.ochronadanych.services.file;

import edu.pw.ochronadanych.dto.FileDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface FileService {
    FileDTO saveFile(FileDTO file) throws IOException, SQLException;

    List<FileDTO> getAllLoggedUserFiles();
}
