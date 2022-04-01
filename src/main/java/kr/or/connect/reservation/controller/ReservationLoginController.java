package kr.or.connect.reservation.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import kr.or.connect.reservation.service.ReservationService;

@SessionAttributes("reservationEmail")
@Controller
public class ReservationLoginController {
	@Autowired
	ReservationService reservationService;
	
	@GetMapping("/bookinglogin")
	public String bookingLogin() {
		return "bookinglogin";
	}
	
	@PostMapping("/bookinglogin")
	public String bookingLogingOk(@RequestParam(name="resrv_email") String reservationEmail, HttpSession session,
			HttpServletResponse response, Model model) {
		boolean existing = reservationService.existReservation(reservationEmail);
		Cookie loginCookie;
		
		if(session.getAttribute("login") != null) {
			session.removeAttribute("login");
		}
		if(existing) {
			session.setAttribute("loginInfo", reservationEmail);
			session.setMaxInactiveInterval(1800);
			loginCookie = new Cookie("loginCookie", reservationEmail);
			response.addCookie(loginCookie);
			return "redirect:/myreservation";
		} else {
			model.addAttribute("noMatchedEmail", true);
			return "bookinglogin";
		}
	}
}
