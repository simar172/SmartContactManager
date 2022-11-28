package com.example.demo.Controller;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Enitities.Contact;
import com.example.demo.Enitities.User;
import com.example.demo.Repositries.ContactRepo;
import com.example.demo.Repositries.UserRepo;
import com.example.demo.helper.Message;
import com.example.demo.helper.Upload;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/normal")
public class NormalController {
	@Autowired
	UserRepo ur;
	@Autowired
	ContactRepo cr;

	@ModelAttribute
	public void data(Model m, Principal p) {
		String email = p.getName();
		User user = ur.findByEmail(email);
		m.addAttribute("user", "Hello " + user.getName());
	}

	@GetMapping("/dashboard")
	public String normalDash(Model m, Principal p) {

		return "normal/dashboard";
	}

	@GetMapping("/add-contact")
	public String addContact(Model m) {
		m.addAttribute("contact", new Contact());
		return "normal/add-contact";
	}

	@PostMapping("/process")
	public String contactProcessing(@ModelAttribute Contact contact, Principal p, HttpSession hs,
			@RequestParam("profImage") MultipartFile mf) throws Exception {
		try {
			System.out.println(contact);
			String name = p.getName();
			Upload u = new Upload();
			String filename = u.upload(mf, name, "contact");
			User user = ur.findByEmail(name);

			System.out.println("Image   " + user.getImage());
			contact.setU(user);
			contact.setImage(filename);
			if (user == null || user.getImage() == null) {
				throw new Exception("error");
			}
			user.getContacts().add(contact);
			System.out.println("Imaeg   " + mf.getOriginalFilename());
			ur.save(user);
			hs.setAttribute("msg", new Message("Success", "alert-success"));

			hs.removeAttribute("msg");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			hs.setAttribute("msg", new Message("Error", "alert alert-danger"));

		}
		return "normal/add-contact";
	}

	@GetMapping("/contacts/{pageno}")
	public String allContacts(Model m, @PathVariable int pageno, Principal p) {
		String uname = p.getName();
		m.addAttribute("uname", uname);
		User user = ur.findByEmail(uname);
		PageRequest pages = PageRequest.of(pageno, 5);

		Page<Contact> list = cr.getContactsByUser(user.getId(), pages);
		m.addAttribute("list", list);
		m.addAttribute("currentPage", pageno);
		m.addAttribute("totalPages", list.getTotalPages());
		return "normal/allContacts";
	}

	@GetMapping("/cuser/{cid}")
	public String oneContactDetail(@PathVariable int cid, Model m, Principal p) {
		Contact contact = cr.findById(cid).get();
		String uname = p.getName();
		User user = ur.findByEmail(uname);
		if (user.getId() == contact.getU().getId()) {

			m.addAttribute("user", uname);
			m.addAttribute("cdetail", contact);
		}
		return "normal/particularContact";
	}

	@GetMapping("/delete/{cid}")
	public String deleteContact(@PathVariable int cid, Principal p) {
		Contact contact = cr.findById(cid).get();
		String name = p.getName();
		User findByEmail = ur.findByEmail(name);
		if (contact.getU().getId() == findByEmail.getId()) {
			contact.setU(null);
			cr.delete(contact);
		}
		return "redirect:/normal/contacts/0";
	}
}
