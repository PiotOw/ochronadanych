package edu.pw.ochronadanych.dto;

import edu.pw.ochronadanych.entity.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.util.Arrays;
import java.util.Base64;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDTO {

	@Nullable
	private Long id;
	private String fileName;
	private String fileType;
	private String fileExtension;
	private Long fileSize;
	private String savedContent;
	private MultipartFile content;

	public FileDTO(File file) {
		this.id = file.getId();
		this.fileName = file.getFileName();
		this.fileType = file.getFileType();
		this.fileExtension = file.getFileExtension();
		this.fileSize = file.getSize();
		this.savedContent = Arrays.toString(file.getContent());
	}
}
