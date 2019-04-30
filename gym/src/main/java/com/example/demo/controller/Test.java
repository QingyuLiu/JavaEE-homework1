package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;


import com.example.demo.entity.User;
import com.example.demo.entity2.Coach;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository2.CoachRepository;
import com.example.demo.service.CoachService;
import com.example.demo.service.UserService;

import org.springframework.data.domain.Sort;




//
@Controller
public class Test {
//	
//	

	@Autowired
	  private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	  private CoachService coachService;
	@Autowired
	private CoachRepository coachRepository;
	  
	private static final String SUCCESS = "success";
//	
//	
	@PostMapping(value="/login")
	public String list(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		
		
		
		User user=new User();
		user.setUsername(username);
		user.setPassword(password);
		        if(userService.login(user)==false)
		        	return "redirect:/login";
		        else
		        	return "redirect:list";
		
	}
//	
//	
//	
//	
	@PostMapping(value="/register")
	public String addUser(@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("email") String email) {
		   System.out.println(username+password);
		   User user=new User();
		   user.setUsername(username);
		   user.setPassword(password);
		   user.setEmail(email);
		   
		  if(userService.save(user)==true)
			  return "redirect:/login";
		  else
		   return "redirect:/register";
		
	}
	
	@GetMapping(value="/login")
	public String Login() {
		
		 return "login";
	}
	@GetMapping(path="register")
	
	public String register() {
		
		 return "register";
	}
	

	
	//分页显示
	@RequestMapping(value="/list")
	public String list(Model m ,@RequestParam(value="pageNo", required=false, defaultValue="0") int pageNo, 
			@RequestParam(value="size",defaultValue="5") int size) throws Exception{
		
		pageNo=pageNo<0?0:pageNo;
		Sort sort=new Sort(Sort.Direction.ASC,"id");
		
		
		Pageable pageable = new PageRequest(pageNo, size,sort);
		
		  Page<User> page = userRepository.findAll(pageable);
		  
		    System.out.println("page.getNumber()"+page.getNumber());  //当前页start
	        System.out.println("page.getNumberOfElements()"+page.getNumberOfElements());  //当前页start
	        System.out.println("page.getSize()"+page.getSize());   //每页数量size
	        System.out.println("page.getTotalElements()"+page.getTotalElements());  //总数量
	        System.out.println("page.getTotalPages()"+page.getTotalPages());    //总页数

	        m.addAttribute("page", page);
	        System.out.println("m="+m);
				return "list";
	
	}
	
	@GetMapping("/deleteUser")
	  public String deleteUser(User u)throws Exception{
		userRepository.delete(u);
        return "redirect:/list";  //删除成功,重定向到分类查询页面
    }

	@RequestMapping("/updateUser")   //修改方法
    public String updateUser(User u)throws Exception{
		userRepository.saveAndFlush(u); //CrudRepository:JPA 新增和修改用的都是save. 它根据实体类的id是否为0来判断是进行增加还是修改
        return "redirect:list";  //修改成功,重定向到分类查询页面
    }

	
	 @RequestMapping("/editUser")     //获取方法（先走查询，再走修改）
	    public String editUser(int id ,Model m)throws Exception{
	        Optional<User> u =userRepository.findById(id);  //根据id查询
	        m.addAttribute("u",u.get()); //查到展示到修改页面
	        return "editUser";      }
	 
	 @GetMapping(value="/find")
		public String Find(Model m,@RequestParam(value="pageNo", required=false, defaultValue="0") int pageNo, 
				@RequestParam(value="size",defaultValue="5") int size) throws Exception{
			
			pageNo=pageNo<0?0:pageNo;
			Sort sort=new Sort(Sort.Direction.ASC,"id");
			
			
			Pageable pageable = new PageRequest(pageNo, size,sort);
			
			  Page<Coach> page = coachRepository.findAll(pageable);
			  
			    System.out.println("page.getNumber()"+page.getNumber());  //当前页start
		        System.out.println("page.getNumberOfElements()"+page.getNumberOfElements());  //当前页start
		        System.out.println("page.getSize()"+page.getSize());   //每页数量size
		        System.out.println("page.getTotalElements()"+page.getTotalElements());  //总数量
		        System.out.println("page.getTotalPages()"+page.getTotalPages());    //总页数

		        m.addAttribute("page", page);
		        System.out.println("m="+m);
					return "find";
		
		}
	 
	 @PostMapping(value = "/find") 
	 public String findUser(@RequestParam("id") String id,Model m)
	 
	 {       
		
		 Integer id1=Integer.parseInt(id);
			
		 List<Coach> list= new ArrayList<Coach>();
		 list.add(coachService.findById(id1));
			  Page<Coach> page = new PageImpl<Coach>(list, new PageRequest(0, 5), 1);
					  
			  

		        m.addAttribute("page", page);
		        return "find";
	 }

}
