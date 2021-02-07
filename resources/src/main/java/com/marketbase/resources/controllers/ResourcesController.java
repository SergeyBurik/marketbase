package com.marketbase.resources.controllers;

import com.marketbase.resources.beans.Module;
import com.marketbase.resources.beans.Order;
import com.marketbase.resources.proxies.AppServiceProxy;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.UUID;
import java.util.zip.ZipEntry;
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
		}
		else {
			throw new FileNotFoundException("File is not present.");
		}
	}

	@GetMapping("/order/{id}/file/{name}")
	public @ResponseBody byte[] getFile(@PathVariable Long id, @PathVariable String name) throws Exception {
		// if such order exists
		if (appServiceProxy.getOrder(id) != null) {
			InputStream in = getClass()
					.getResourceAsStream(projectsPath + "/" + name);
			return IOUtils.toByteArray(in);
		}
		throw new Exception("Order does not exist.");
	}

	@GetMapping(value = {"/order/{id}/project"}, produces = {"application/zip"})
	public @ResponseBody byte[] getProjectFiles(@PathVariable Long id) throws Exception {
		// returns project .zip
		Order order = appServiceProxy.getOrder(id);
		if (order != null) {
			// create zip file
			String path = projectsPath + order.getTemplate().getProjectName() + "/";

			FileOutputStream f = new FileOutputStream(path + "project_" + id + ".zip");
			ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(f));

			// put modules into zip
			for (Module module: order.getModules()) {
				zip.putNextEntry(new ZipEntry(path + module.getModuleName()));
			}

			// put settings
			zip.putNextEntry(new ZipEntry(path + order.getTemplate().getProjectName()));
			zip.close();

			// get byte array and delete file
			File file = new File(path + "project_" + id + ".zip");
			byte[] res = Files.readAllBytes(file.toPath());

			file.delete();
			return res;
		}
		throw new Exception("Order does not exist.");
	}
}
