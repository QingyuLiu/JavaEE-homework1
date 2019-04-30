package com.example.demo.service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;




@Service
@CacheConfig(cacheNames="userCache") // 本类内方法指定使用缓存时，默认的名称就是userCache

@Transactional
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;
	
	@Resource
	private RedisTemplate<String, User> redisTemplate;
	
	@CachePut(key="#p0.id")  //#p0表示第一个参数

	//必须要有返回值，否则没数据放到缓存中

	public User insertUser(User u){

		this.userMapper.insert(u);

		//u对象中可能只有只几个有效字段，其他字段值靠数据库生成，比如id

		return this.userMapper.find(u.getId());

	}
	
	@Cacheable(key="#p0") // @Cacheable 会先查询缓存，如果缓存中存在，则不执行方法

	public User findById(Integer id){

		String key = "user-" + id;
		ValueOperations<String, User> operations = redisTemplate.opsForValue(); 
		boolean hasKey = redisTemplate.hasKey(key); 
		
		if (hasKey) {          
			User user = operations.get(key);            
			System.out.println("从缓存中获取了用户 >> " +"id: "+ user.getId()+", username: "+user.getUsername()+",password: "+user.getPassword());
		        return user;      
		        
		}  
		
		User user;        
	// 从 DB 中获取用户信息     
		user =userRepository.findUserById(id);           
		// 插入缓存        
		if(user!=null){       
			operations.set(key, user, 30, TimeUnit.SECONDS);     
			System.out.println("用户插入缓存 >> " +"id: "+ user.getId()+", username: "+user.getUsername()+",password: "+user.getPassword());  
	             }       
		return user;	
		
	
	}
	

	@CacheEvict(key="#p0")  //删除缓存名称为userCache,key等于指定的id对应的缓存

	public void deleteById(Integer id){
		

		this.userMapper.delete(id);

	}

	

	//清空缓存名称为userCache（看类名上的注解)下的所有缓存

	//如果数据失败了，缓存时不会清除的

	@CacheEvict(allEntries = true)  

	public void deleteAll(){

		this.userMapper.deleteAll();

	}

	
	
	
	@Transactional
	public boolean save(User user) {
		User user1=userRepository.findByUsername(user.getUsername());
		if(user1==null) {
			userRepository.save(user);
			return true;
		}
			
		else
		{
			return false;
			
		}
		
		    
	}
	
	@Transactional
	public boolean login(User user) {
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		
		User user1=userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
		System.out.println("user1"+user1);
	     if(user1==null)
	    	 return false;
	     else
		     return true;
	}
	
	
//	@Override	
//	public User findById(int id) {		
//		// 从缓存中获取信息
//		String key = "user-" + id;       
//		ValueOperations<String, userInfo> operations = redisTemplate.opsForValue();  
//		// 缓存存在      
//		boolean hasKey = redisTemplate.hasKey(key);      
//		if (hasKey) {          
//			userInfo user = operations.get(key);            
//			LOGGER.info("从缓存中获取了用户 >> " +"id: "+ user.getId()+", username: "+user.getUsername()+",password: "+user.getPassword());  
//			return user;        }       
//		userInfo user;        
//		// 从 DB 中获取用户信息     
//		user = userDao.finduserById(id);                  
//		// 插入缓存        
//		if(user!=null){       
//			operations.set(key, user, 30, TimeUnit.SECONDS);     
//			LOGGER.info("用户插入缓存 >> " +"id: "+ user.getId()+", username: "+user.getUsername()+",password: "+user.getPassword());  
//			}         return user;	}
//	}
	
	
}
