package com.marketbase.resources;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
public class UploadsController {

	@Value("${upload.path}")
	String uploadPath;

	@Value("${host}")
	String serverName;

	@PostMapping("/upload")
	public String saveFile(@RequestParam MultipartFile file) throws IOException {
		if (file != null && !file.getOriginalFilename().isEmpty()) {
			File uploadDir = new File(uploadPath);

			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}

			// saving file
			String resultFilename = UUID.randomUUID().toString() + "." + file.getOriginalFilename();

			File resultPath = new File(uploadPath + "/" + resultFilename);
			file.transferTo(resultPath);
			return serverName + "/uploads/" + resultFilename;
		}
		else {
			throw new FileNotFoundException("File is not present.");
		}
	}

}
