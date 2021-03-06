package fr.utbm.lo54projet.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sun.istack.NotNull;

import fr.utbm.lo54projet.entity.CourseSession;
import fr.utbm.lo54projet.entity.Location;
import fr.utbm.lo54projet.entity.SessionsResult;
import fr.utbm.lo54projet.service.LocationService;
import fr.utbm.lo54projet.service.SessionService;

@Controller
public class SessionController {

	@Autowired
	SessionService sessionService;

	@Autowired
	LocationService locationService;

	@RequestMapping("")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("/accueil");
		List<SessionsResult> cslist = getAllSessions();
		mv.addObject("sessionList", cslist);
		return mv;
	}

	// @RequestMapping("getAllSessions")
	public List<SessionsResult> getAllSessions() {
		return sessionService.getSession();
	}

	// @Autowired
	// CourseController courseController;

	public List<CourseSession> getSessionByDateAfter(Date a) {
		return sessionService.getByDateA(a);
	}

	public List<CourseSession> getSessionByDateBefore(Date b) {
		return sessionService.getByDateB(b);
	}

	public List<CourseSession> getSessionByLocation(Long Id) {
		return sessionService.getByLocation(Id);
	}

	public List<CourseSession> getSessionByCourse(@NotNull String code) {
		return sessionService.getByCourse(code);
	}

	@RequestMapping("filter")
	/*
	 * public String filter(Model model) { List<Location> list =
	 * locationService.getLocation(); model.addAttribute("list", list); return
	 * "filter"; }
	 */
	public ModelAndView filter() {
		ModelAndView mv = new ModelAndView("/filter");
		List<Location> list = locationService.getLocation();
		mv.addObject("list", list);
		return mv;
	}

	/*
	 * filter by location
	 * 
	 * @RequestMapping("sessions") public String getCourse(HttpServletRequest
	 * request,@RequestParam("location") String city, Model model) { city=
	 * request.getParameter("location"); model.addAttribute("location",city); Long
	 * id = locationService.findId(city); List<CourseSession> list =
	 * sessionService.getByLocation(id); model.addAttribute("sessions",list); return
	 * "sessions"; }
	 */
	/*
	 * filter by DateAfter
	 * 
	 * @RequestMapping("sessions") public String getCourse(HttpServletRequest
	 * request,@RequestParam("date") String str,Model model) throws ParseException {
	 * str= request.getParameter("date"); SimpleDateFormat format = new
	 * SimpleDateFormat("yyyy-MM-dd"); java.util.Date d = null; try { d =
	 * format.parse(str); } catch (Exception e) { e.printStackTrace(); }
	 * java.sql.Date sqlDate = new java.sql.Date(d.getTime()); List<CourseSession>
	 * list = sessionService.getByDateA(sqlDate);
	 * model.addAttribute("sessions",list); return "sessions"; }
	 */
	
	
	@RequestMapping("sessions")
	public String getCourse(HttpServletRequest request, @RequestParam("keyword") String keyword,
			@RequestParam("date") String str, @RequestParam("location") String city, Model model)
			throws ParseException {
		// get course keyword
		keyword = request.getParameter("keyword");

		city = request.getParameter("location");

		if (city.equals("All")) {
			// get date
			str = request.getParameter("date");
			if (str == "") {
				List<SessionsResult> list = sessionService.getSessions(keyword);
				model.addAttribute("sessions", list);
			} else {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date d = null;
				try {
					d = format.parse(str);
				} catch (Exception e) {
					e.printStackTrace();
				}
				java.sql.Date sqlDateA = new java.sql.Date(d.getTime());
				List<SessionsResult> list = sessionService.getSessions(sqlDateA, keyword);
				model.addAttribute("sessions", list);
			}
		} else {
			// get location id
			Long locationId = locationService.findId(city);

			// get date
			str = request.getParameter("date");
			if (str == "") {
				List<SessionsResult> list = sessionService.getSessions(locationId, keyword);
				model.addAttribute("sessions", list);
			} else {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date d = null;
				try {
					d = format.parse(str);
				} catch (Exception e) {
					e.printStackTrace();
				}
				java.sql.Date sqlDateA = new java.sql.Date(d.getTime());
				List<SessionsResult> list = sessionService.getSessions(locationId, sqlDateA, keyword);
				model.addAttribute("sessions", list);
			}
		}

		return "sessions";
	}
	
	
}
