package com.marketbase.app;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;



@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class LoginTest {

	@Autowired
	private MockMvc mockMvc;

	@Value("${server.port}")
	private String serverPort;

	@Test
	public void testMainPageUnauthorized() throws Exception {
		this.mockMvc.perform(get("/"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Register")));
	}

	@Test
	public void testAccessUnauthorized() throws Exception {
		this.mockMvc.perform(get("/orders"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
	}


	@Test
	public void testUserLogin() throws Exception {
		this.mockMvc.perform(formLogin().user("test1").password("1"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));
	}

//	@Test
//	public void badCredential() throws Exception {
//		this.mockMvc.perform(formLogin().user("test2").password("123"))
//				.andDo(print())
//				.andExpect(status().is3xxRedirection())
//				.andExpect(redirectedUrl())
//	}


	@Test
	public void test() throws Exception {

	}
}
