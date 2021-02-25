package com.marketbase.resources.controllers;

import com.marketbase.resources.beans.Module;
import com.marketbase.resources.beans.Order;
import com.marketbase.resources.proxies.AppServiceProxy;
import com.marketbase.resources.tools.ZipDirectory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

@RestController
public class ResourcesController {

	@Value("${upload.path}")
	String uploadPath;

	@Value("${host}")
	String serverName;

	@Value("${projects.path}")
	String projectsPath;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	AppServiceProxy appServiceProxy;

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
			return serverName + "/files/" + resultFilename;
		} else {
			throw new FileNotFoundException("File is not present.");
		}
	}

	@GetMapping("/order/{id}/file/{name}")
	public ResponseEntity<InputStreamResource> getFile(@PathVariable Long id, @PathVariable String name) throws Exception {
		// if such order exists
		if (appServiceProxy.getOrder(id) != null) {
			MediaType mediaType = getMediaTypeForFileName(this.servletContext, name);
			System.out.println("fileName: " + name);
			System.out.println("mediaType: " + mediaType);

			File file = new File(projectsPath + "/" + name);
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

			return ResponseEntity.ok()
					// Content-Disposition
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
					// Content-Type
					.contentType(mediaType)
					// Content-Length
					.contentLength(file.length())
					.body(resource);
		}
		throw new Exception("Order does not exist.");
	}

	@GetMapping(value = {"/order/{id}/project"}, produces = {"application/zip"})
	public ResponseEntity<InputStreamResource> getProjectFiles(@PathVariable Long id) throws Exception {
		// returns project .zip
		Order order = appServiceProxy.getOrder(id);
		if (order != null) {
			// create zip file
			String path = projectsPath + "/" + order.getTemplate().getProjectName() + "/";

			FileOutputStream f = new FileOutputStream(path + "project_" + id + ".zip");
			ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(f));

			List<String> dirs = new ArrayList<>();

			// put modules into zip
			for (Module module : order.getModules()) {
				File moduleDir = new File(path + module.getModuleName());
				dirs.add(moduleDir.getAbsolutePath());
			}

			// put settings directory
			File settingsDir = new File(path + order.getTemplate().getProjectName());
			dirs.add(settingsDir.getAbsolutePath());
			zip.close();

			// save zip
			ZipDirectory.zipDirs(
					projectsPath + "/zipped_projects",
					"project_" + id + ".zip",
					true, fp -> true,
					dirs
			);

			MediaType mediaType = getMediaTypeForFileName(this.servletContext, "project_" + id + ".zip");
			File file = new File(projectsPath + "/zipped_projects/" + "project_" + id + ".zip");
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

			return ResponseEntity.ok()
					// Content-Disposition
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
					// Content-Type
					.contentType(mediaType)
					// Content-Length
					.contentLength(file.length())
					.body(resource);
		}
		throw new Exception("Order does not exist.");
	}

	public static MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
		// application/pdf
		// application/xml
		// image/gif, ...
		String mineType = servletContext.getMimeType(fileName);
		try {
			return MediaType.parseMediaType(mineType);
		} catch (Exception e) {
			return MediaType.APPLICATION_OCTET_STREAM;
		}
	}
}
