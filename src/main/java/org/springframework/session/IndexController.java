package org.springframework.session;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**��������ҳת���Ŀ�����
 * @author Arison
 *
 */
@Controller(value="IndexController")
public class IndexController {
   
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request) {
		return "me/index";
	}
	
}
