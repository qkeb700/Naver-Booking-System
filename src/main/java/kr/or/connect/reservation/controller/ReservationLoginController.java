package kr.or.connect.reservation.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes("inputEmail")
@Controller
public class ReservationLoginController {

	@GetMapping("/bookinglogin")
	public String bookingLogin() {
		return "bookinglogin";
	}
	
	@GetMapping("/bookingLoginOk")
	public String bookingLogingOk(@RequestParam(name="resrv_email") String inputEmail, HttpSession session) {
		session.setAttribute("inputEmail", inputEmail);
		return "redirect:/myreservation";
	}
}
