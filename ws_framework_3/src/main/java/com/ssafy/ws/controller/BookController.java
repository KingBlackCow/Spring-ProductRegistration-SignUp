package com.ssafy.ws.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ws.dto.Book;
import com.ssafy.ws.dto.User;
import com.ssafy.ws.model.service.BookService;
import com.ssafy.ws.model.service.UserService;
import com.sun.xml.internal.ws.api.ResourceLoader;

@Controller
public class BookController {
	
	private static final Logger logger = LoggerFactory.getLogger(BookController.class);
	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserService userService;
	
	
//	@RequestMapping(value = "/write", method = RequestMethod.GET)
	/*
	 * @GetMapping("/write") public String write() { return "guestbook/write"; }
	 */
	
//	@RequestMapping(value = "/write", method = RequestMethod.POST)
	/*
	 * @PostMapping("/write") public String write(BookRepo book, Model model,
	 * HttpSession session) { User user = (User) session.getAttribute("userinfo");
	 * if(user != null) { logger.debug("로그인 했다면....!!!");
	 * 
	 * try { bookService.writeArticle(book); return "guestbook/writesuccess"; }
	 * catch (Exception e) { e.printStackTrace(); model.addAttribute("msg",
	 * "글작성중 문제가 발생했습니다."); return "error/error"; } } else {
	 * model.addAttribute("msg", "로그인 후 사용 가능한 페이지입니다."); return "error/error"; } }
	 */

	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insert(Book book, Model model) {
		try {
			bookService.insert(book);
			//model.addAttribute("articles", list);
			return "book/list";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "책이없습니다.");
			return "error/error";
		}
	}
	

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(@RequestParam Map<String, String> map, Model model) {
		try {
			List<Book> books = new ArrayList<>();
			books.add(new Book("111-222-3333", "홍길동", "책제목1", 10000, "좋은 책 1", null));
			books.add(new Book("111-222-4444", "임꺽정", "책제목2", 20000, "좋은 책 2", null));
			books.add(new Book("111-333-4444", "장길산", "책제목3", 30000, "좋은 책 3", null));
			model.addAttribute("books", books);
			return "book/list";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "책이없습니다.");
			return "error/error";
		}
	}
	
	@PostMapping("/login")
	public String doLogin(@ModelAttribute User user, HttpSession session, Model model) {
		if (user.getId().equals("ssafy") && user.getPass().equals("1234")) {
			user.setName("김싸피");
			session.setAttribute("loginUser", user);
			return "redirect:/";
		} else {
			model.addAttribute("msg", "로그인 실패");
			return "index";
		}
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/regist")
	public String showRegistForm() {
		return "book/regist";
	}
	
	@PostMapping("/regist")
	public String doRegist(@ModelAttribute Book book) {
		
		return "book/regist_result";
	}
	
	@RequestMapping("/error/404")
	public String showError404() {
		return "error/404";
	}
	
	@RequestMapping("/error/commonerr")
	public String showError500() {
		return "error/commonerr";
	}
}
