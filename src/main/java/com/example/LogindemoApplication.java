package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@SpringBootApplication
public class LogindemoApplication {

	// Visar upp inloggningsformulär
	@GetMapping("/")
	public ModelAndView getForm() {

		return new ModelAndView("form")
				.addObject("user", new User("", ""));
	}

	// Loggar in användaren om denne angivit korrekt inloggningsinformation
	@PostMapping("login")
	public ModelAndView login(@ModelAttribute User user, HttpSession session) {

		// kolla om user finns i databas - här hårdkodat till att bara user andreas finns med lösenord password
		if (user.getUsername().equalsIgnoreCase("user") && user.getPassword().equalsIgnoreCase("pass") ) {
			session.setAttribute("user", new User(user.getUsername(), user.getPassword()));
			return new ModelAndView("redirect:/content");
		}
		return new ModelAndView("redirect:/");
	}







	// Visar skyddad sida content bara om användaren är inloggad
	@GetMapping("content")
	public ModelAndView getContent(HttpSession session) {
		if (session.getAttribute("user") != null) {
			return new ModelAndView("content");
		}
		return new ModelAndView("redirect:/");
	}

	// Loggar ut användaren
	@GetMapping("logout")
	public ModelAndView logout(HttpSession session, HttpServletResponse res) {
		session.invalidate();

		Cookie cookie = new Cookie("jsessionid", null);
		cookie.setMaxAge(0);
		res.addCookie(cookie);


		return new ModelAndView("redirect:/");
	}

	public static void main(String[] args) {
		SpringApplication.run(LogindemoApplication.class, args);
	}


	@RequestMapping("adam/{bertil}")
	public ModelAndView ceasar(HttpSession session, @PathVariable String bertil) {
		String david = (String)session.getAttribute(bertil);
		return new ModelAndView("erik").addObject("fredrik", david);
	}
}
