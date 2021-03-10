package com.marketbase.app.services;

import com.marketbase.app.models.User;
import com.marketbase.app.models.UserRole;
import com.marketbase.app.proxies.ResourcesServiceProxy;
import com.marketbase.app.repositories.UserRepository;
import com.marketbase.app.tools.ImageCrop;
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
import java.io.File;
import java.io.FileInputStream;
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

	@Autowired
	ResourcesServiceProxy resourcesServiceProxy;

	public User save(String username, String first_name,
					 String last_name, String email,
					 String phoneNumber, String password) throws Exception {

		// check username
		if (userRepository.findByUsername(username).isPresent()) {
			throw new Exception("User with such username exists");
		}

		User user = new User(username, first_name, last_name, password, email, phoneNumber);

//		if (avatar != null && !avatar.getOriginalFilename().isEmpty()) {
//
//			// cropping and temporary saving image
//			File file = new File(imageCrop.cropImage(avatar));
//			FileInputStream input = new FileInputStream(file);
//
//			MultipartFile multipartFile = new MockMultipartFile("file",
//					file.getName(),
//					FilenameUtils.getExtension(avatar.getOriginalFilename()),
//					IOUtils.toByteArray(input));
//
//			// saving cropped image
//			String resultFilename = resourcesServiceProxy.saveFile(multipartFile);
//
//			// deleting temporary saved image
//			file.delete();
//
//			user.setAvatar(resultFilename);
//
//		}
		user.setActive(true);
		user.setRoles(Collections.singleton(UserRole.USER));
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
