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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public")
public class ImageController {

	@Value("${app.fileupload.uploadDir}")
	private String uploadFolder;

	@Value("${app.fileupload.uploadPath}")
	private String uploadPath;

	@GetMapping(value = "/images/logo", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<?> getLogo() throws IOException {
		InputStream image = getImage("logo.png");

		return ResponseEntity.ok().body(IOUtils.toByteArray(image));
	}

	@GetMapping(value = "/images/no-images", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<?> getNoImage() throws IOException {
		InputStream image = getImage("no-images.png");

		return ResponseEntity.ok().body(IOUtils.toByteArray(image));
	}

	@GetMapping(value = "/users/profiles/{filename:.+}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<?> getProfileImage(@PathVariable String filename) throws IOException {
		InputStream image = getImage(filename);

		return ResponseEntity.ok().body(IOUtils.toByteArray(image));
	}

	@GetMapping(value = "/images/contents/{filename:.+}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<?> getContentsImage(@PathVariable String filename) throws IOException {
		InputStream image = getImage(filename);

		return ResponseEntity.ok().body(IOUtils.toByteArray(image));
	}

	private InputStream getImage(String filename) throws IOException {
		Path file = Paths.get(uploadPath + File.separator + uploadFolder, filename);
		InputStreamResource resource = new InputStreamResource(Files.newInputStream(file));

		return resource.getInputStream();
	}
}
