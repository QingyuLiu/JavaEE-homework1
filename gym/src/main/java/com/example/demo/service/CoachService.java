package com.example.demo.service;

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

import com.example.demo.entity2.Coach;
import com.example.demo.mapper.CoachMapper;
import com.example.demo.repository2.CoachRepository;


@Service
@CacheConfig(cacheNames="coachCache") // 本类内方法指定使用缓存时，默认的名称就是userCache

@Transactional
public class CoachService {
	@Autowired
	private CoachRepository coachRepository;
	
	@Autowired
	private CoachMapper coachMapper;
	
	@Resource
	private RedisTemplate<String, Coach> redisTemplate;
	
	@CachePut(key="#p0.id")  //#p0表示第一个参数
	//必须要有返回值，否则没数据放到缓存中

		public Coach insertCoach(Coach u){

			this.coachMapper.insert(u);

			//u对象中可能只有只几个有效字段，其他字段值靠数据库生成，比如id

			return this.coachMapper.find(u.getId());

		}
		
		@Cacheable(key="#p0") // @Cacheable 会先查询缓存，如果缓存中存在，则不执行方法

		public Coach findById(Integer id){

			String key = "coach-" + id;
			ValueOperations<String, Coach> operations = redisTemplate.opsForValue(); 
			boolean hasKey = redisTemplate.hasKey(key); 
			
			if (hasKey) {          
				Coach coach = operations.get(key);            
				System.out.println("从缓存中获取了用户 >> " +"id: "+ coach.getId()+", username: "+coach.getUsername());
			        return coach;      
			        
			}  
			
			Coach coach;        
		// 从 DB 中获取用户信息     
			coach =coachRepository.findCoachById(id);           
			// 插入缓存        
			if(coach!=null){       
				operations.set(key, coach, 30, TimeUnit.SECONDS);     
				System.out.println("用户插入缓存 >> " +"id: "+ coach.getId()+", username: "+coach.getUsername());  
		             }       
			return coach;	
		}
		

		@CacheEvict(key="#p0")  //删除缓存名称为userCache,key等于指定的id对应的缓存

		public void deleteById(Integer id){
			

			this.coachMapper.delete(id);

		}

		

		//清空缓存名称为userCache（看类名上的注解)下的所有缓存

		//如果数据失败了，缓存时不会清除的

		@CacheEvict(allEntries = true)  

		public void deleteAll(){

			this.coachMapper.deleteAll();

		}
}
