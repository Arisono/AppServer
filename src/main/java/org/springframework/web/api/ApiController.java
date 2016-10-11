
package org.springframework.web.api;


import java.security.PublicKey;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.RSAUtils;


/** �ӿڴ���
 * @author Arison
 *
 */
@Controller(value="ApiController")
public class ApiController {
   
	/**����api���Խ���
	 * @param request
	 * 
	 * @return
	 */
	@RequestMapping(value = "/api/index")
	public String index(HttpServletRequest request) {
		return "me/index";
	}
	
	/**��������ͷ
	 * @return
	 */
	@RequestMapping(value = "/api/getHeaders")
	private @ResponseBody LinkedHashMap<String, Object>  receiveHeaders(
			HttpServletRequest request,
			@CookieValue(value = "JSESSIONID", required = false)
			String sessionId,
			@RequestBody String postBody) {
		LinkedHashMap<String, Object> result=new LinkedHashMap<String, Object>();
		Map<String, Object> header=new HashMap<String, Object>();
		Map<String, Object> params=new HashMap<String, Object>();
		result.put("postBody", postBody);
//		result.put("postBody1", postBody1);
//		result.put("postBody2", postBody2);
		@SuppressWarnings("rawtypes")
		Enumeration paramNames  =request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String key = (String) paramNames.nextElement();
			Object value =  request.getParameter(key);
			params.put(key, value);
		}
		result.put("params", params);
		@SuppressWarnings("rawtypes")
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			header.put(key, value);
		}
		result.put("headers", header);
		result.put("JSESSIONID", sessionId);
		System.out.println(result.toString());
		String public_key=request.getParameter("publicKey");
		String miwen=request.getParameter("miwen");
		
		String token=RSAUtils.RSADecode(Base64.decodeBase64(public_key), Base64.decodeBase64(miwen));
	    System.out.println("token:"+token);
		result.put("token", token);
		return result;
	}
	
}
