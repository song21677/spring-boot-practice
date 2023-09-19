package com.example.practice2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class FileStore {

	@Value("${file.dir}")
	private String fileDir;

	public String getFullPath(String fileName) {
		log.info(fileDir + fileName);
		return fileDir + fileName;
	}

	public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
		List<UploadFile> storeFileResult = new ArrayList<>();
		for (MultipartFile multipartFile : multipartFiles) {
			if (!multipartFile.isEmpty()) {
				storeFileResult.add(storeFile(multipartFile));
			}
		}
		return storeFileResult;
	}

	public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
		if (multipartFile.isEmpty()) {
			return null;
		}

		String originalFileName =multipartFile.getOriginalFilename();
		String storeFileName = createStoreFileName(originalFileName);
		multipartFile.transferTo(new File(getFullPath(storeFileName)));
		// log.info("파일 저장 fllPath={}", fullPath);

		return new UploadFile(originalFileName, storeFileName);
	}

	private static String createStoreFileName(String originalFileName) {
		String uuid = UUID.randomUUID().toString();
		String ext = extractExt(originalFileName);
		log.info("파일 확장자 = {}", ext);
		return uuid + "." + ext;
	}

	private static String extractExt(String originalFileName) {
		int pos = originalFileName.lastIndexOf(".");
		return originalFileName.substring(pos+1);
	}

}
