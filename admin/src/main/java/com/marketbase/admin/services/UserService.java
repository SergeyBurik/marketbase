package com.marketbase.admin.services;

import com.marketbase.admin.models.User;
import com.marketbase.admin.models.UserRole;
import com.marketbase.admin.repositories.UserRepository;
import com.marketbase.admin.tools.ImageCrop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
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

	public User save(String username, String first_name,
					 String last_name, String email, String password, Set<UserRole> role, MultipartFile avatar) throws Exception {

		// check username
		if (userRepository.findByUsername(username).isPresent()) {
			throw new Exception("User with such username exists");
		}

		User user = new User(username, first_name, last_name, email, password);

		if (avatar != null && !avatar.getOriginalFilename().isEmpty()) {
			File uploadDir = new File(uploadPath);

			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}

			// saving cropped image
			String resultFilename = imageCrop.saveSquareImage(avatar);

			user.setAvatar(resultFilename);
			user.setActive(true);
			user.setRoles(role);
		}

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
