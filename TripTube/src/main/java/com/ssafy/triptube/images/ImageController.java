package com.ssafy.triptube.images;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public/images")
public class ImageController {

	@Value("${app.fileupload.uploadDir}")
	private String uploadFolder;

	@Value("${app.fileupload.uploadPath}")
	private String uploadPath;

	@GetMapping(value = "/logo", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<?> getLogo() throws IOException {
		Path file = Paths.get(uploadPath + File.separator + uploadFolder, "logo.png");
		InputStreamResource resource = new InputStreamResource(Files.newInputStream(file));

		InputStream image = resource.getInputStream();

		return ResponseEntity.ok().body(IOUtils.toByteArray(image));
	}

	@GetMapping(value = "/no-images", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<?> getNoImage() throws IOException {
		Path file = Paths.get(uploadPath + File.separator + uploadFolder, "no-images.png");
		InputStreamResource resource = new InputStreamResource(Files.newInputStream(file));

		InputStream image = resource.getInputStream();

		return ResponseEntity.ok().body(IOUtils.toByteArray(image));
	}

}
