package com.marketbase.managers.services;

import com.marketbase.managers.models.User;
import com.marketbase.managers.models.UserRole;
import com.marketbase.managers.proxies.ResourcesServiceProxy;
import com.marketbase.managers.repositories.UserRepository;
import com.marketbase.managers.tools.ImageCrop;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Component
public class UserService {

	@Value("${upload.path}")
	String uploadPath;

	@Autowired
	ServletContext context;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ImageCrop imageCrop;

	@Value("${resources.server}")
	String resourcesServerName;

	@Autowired
	ResourcesServiceProxy resourcesServiceProxy;

	public User save(String username, String first_name,
					 String last_name, String email, String password, Set<UserRole> role, MultipartFile avatar) throws Exception {

		// check username
		if (userRepository.findByUsername(username).isPresent()) {
			throw new Exception("User with such username exists");
		}

		User user = new User(username, first_name, last_name, password, email);

		if (avatar != null && !avatar.getOriginalFilename().isEmpty()) {
			File uploadDir = new File(uploadPath);

			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}

			// cropping and temporary saving image
			File file = new File(imageCrop.cropImage(avatar));
			FileInputStream input = new FileInputStream(file);

			MultipartFile multipartFile = new MockMultipartFile("file",
					file.getName(),
					FilenameUtils.getExtension(avatar.getOriginalFilename()),
					IOUtils.toByteArray(input));

			// saving cropped image
			String resultFilename = resourcesServiceProxy.saveFile(multipartFile);

			// deleting temporary saved image
			file.delete();

			user.setAvatar(resultFilename);
			user.setActive(true);
			user.setRoles(role);
		}

		// saving
		userRepository.save(user);

		return user;
	}

	public User getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username = "";

		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}

		Optional<User> user = userRepository.findByUsername(username);
		return user.orElse(null);
	}

}
