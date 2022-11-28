package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.Enitities.User;
import com.example.demo.Repositries.UserRepo;
import com.example.demo.helper.Message;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	@Autowired
	UserRepo ur;
	@Autowired
	BCryptPasswordEncoder bc;

	@GetMapping("/")
	public String home() {

		return "home";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}

	@GetMapping("/signup")
	public String signup(Model m) {
		m.addAttribute("user", new User());
		return "signup";
	}

	@PostMapping("/data")
	public String regUser(@ModelAttribute("user") User u, Model m, HttpSession session) {
		try {
			System.out.println("use is  " + u);
			if (u.getEmail() == "") {
				throw new Exception("error");
			}
			u.setEnabled(true);
			u.setImage("default.jpg");
			u.setRole("ROLE_NORMAL");
			u.setPassword(bc.encode(u.getPassword()));
			ur.save(u);
			session.setAttribute("msg", new Message("Success!!", "alert-success"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.setAttribute("msg", new Message("Error", "alert-danger"));

		}
		return "signup";
	}

}
