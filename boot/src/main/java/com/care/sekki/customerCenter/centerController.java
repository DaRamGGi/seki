package com.care.sekki.customerCenter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class centerController {
	@RequestMapping("announcement")
	public String announcement() {
		
		return "customerCenter/announcement";
	}
	
	@GetMapping("writeAnnouncement")
	public String writeAnnouncement() {
		
		return "customerCenter/writeAnnouncement";
	}

}
