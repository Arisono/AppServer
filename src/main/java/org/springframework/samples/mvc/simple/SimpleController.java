package org.springframework.samples.mvc.simple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.samples.mvc.jpa.entity.News;
import org.springframework.samples.mvc.jpa.entity.User;
import org.springframework.samples.mvc.jpa.service.NewsService;
import org.springframework.samples.mvc.jpa.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller("SimpleController")
public class SimpleController {
	@Resource
	private UserService userService;
	
	@Resource
	private NewsService newsService;
	
	@RequestMapping(value="/simple/save/{name}/{password}", produces = "application/json; charset=utf-8")
	public  String simpleSave(@PathVariable String name,@PathVariable String password) {
		User user=new User();
		user.setUsername(name);
		user.setPassword(password);
		userService.saveUser(user);
	    User user2=userService.findOneUser(name, password);
	    
	    News news=new News();
	    news.setTitle("����������");
	    news.setContent("�����ຣ");
	    news.setUserId(user2.getId());
//	    news.setUserId(user2);
	    newsService.save(news);
//	     "����ɹ�����ѯ�ӿ�  localhost:8080/spring-mvc-showcase/simple/find/"+user2.getId();
		return  "redirect:/simple/find/"+user2.getId();
	}
	
	
	
	@RequestMapping(value="/simple/find/{id}")
	public @ResponseBody User simpleFind(@PathVariable Long id) {
		User user=userService.findOneUser(id);
		return user;
	}

	@RequestMapping(value="/simple",produces="text/plain;charset=utf-8")
	public @ResponseBody String simple() {
		List<User> users=userService.findAllUsers();
		for (int i = 0; i < users.size(); i++) {
			System.out.println(users.get(i).getUsername());
		}
		return "Hello world!����";
	}
	
	@RequestMapping(value="/simple/map")
	public @ResponseBody Map<String,Object> map() {
		Map<String,Object> map=new HashMap<>();
		List<User> users=userService.findAllUsers();
		for (int i = 0; i < users.size(); i++) {
			System.out.println(users.get(i).getUsername());
			map.put("users", users);
			map.put("name", users.get(i).getUsername());
			map.put("password", users.get(i).getPassword());
		}
		
		return map;
	}
	
	   @RequestMapping(value="/index1",method=RequestMethod.GET)  
	    public ModelAndView index(){  
	        ModelAndView modelAndView = new ModelAndView("/user/index");  
	        modelAndView.addObject("name", "xxx");  
	        return modelAndView;  
	    }  
	   
	   //����ModelAndView���캯������ָ������ҳ������ƣ�Ҳ����ͨ��setViewName��������������Ҫ��ת��ҳ�棻  
	      
	    @RequestMapping(value="/index2",method=RequestMethod.GET)  
	    public ModelAndView index2(){  
	        ModelAndView modelAndView = new ModelAndView();  
	        modelAndView.addObject("name", "xxx");  
	        modelAndView.setViewName("/user/index");  
	        return modelAndView;  
	    }  
	    
	    /** 
	     * Modelһ��ģ�Ͷ��� 
	     * ��Ҫ����spring��װ�õ�model��modelMap,�Լ�java.util.Map�� 
	     * ��û����ͼ���ص�ʱ����ͼ���ƽ���requestToViewNameTranslator������  
	     * @return 
	     */  
	    @RequestMapping(value="/index3",method=RequestMethod.GET)  
	    public Map<String, String> index3(){  
	        Map<String, String> map = new HashMap<String, String>();  
	        map.put("1", "1");  
	        //map.put�൱��request.setAttribute����  
	        return map;  
	    }  
	    
	    //����String  
	    //ͨ��model����ʹ��  
	    @RequestMapping(value="/index4",method = RequestMethod.GET)  
	    public String index(Model model) {  
	     
	        return "רҵ����";  
	    }  
	      

}
