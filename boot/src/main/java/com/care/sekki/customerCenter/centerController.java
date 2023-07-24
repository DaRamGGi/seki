package com.care.sekki.customerCenter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class centerController {
@RequestMapping("announcement")
public String test() {
	
	return "customerCenter/announcement";
}

}
