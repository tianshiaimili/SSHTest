package cn.net.msg.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import cn.net.msg.dao.UserDao;
import cn.net.msg.model.User;

/**
 * 配置<context:component-scan base-package="com.test" />解析：
使用ClassPathXmlApplicationContext("bean.xml")初始化bean.xml的时候，或者说初始化spring配置文件的时候，会自动扫描com.test下的所有包,
若发现有@component，则将该类初始化为一个对象，对象的key为@component("name")中指明的name,
若不指明，则默认为类的名字首字母小写；当调用ctx.getBean("userService")，时，则
查看容器是否有一个名字为userService；如有则在初始化这个类得过程中，如果发现@Resource(name=”u”);
则查看容器是否有名字叫u的bean,若有则将u注入到方法参数中，而后参数就会传人方法内，当然也就注入了成员变量里
 * @author zero
 *
 */
@Component("userDao")
public class UserDaoImpl implements UserDao {
	@Resource(name="hibernateTemplate")
	private HibernateTemplate template;
	//HibernateTeamplate 的使用参考http://www.blogjava.net/tbwshc/articles/380014.html
	
	/* (non-Javadoc)
	 * @see cn.net.msg.dao.impl.UserDao#insertUser(cn.net.msg.model.User)
	 */
	@Override
	public void insertUser(User user){
		 long x=(Long) template.save(user);
		 System.out.println(x);
	}
 
	/* (non-Javadoc)
	 * @see cn.net.msg.dao.impl.UserDao#deleteById(java.lang.String)
	 */
	@Override
	public void deleteById(long id){
		User user=(User) template.get(User.class, id);
		template.delete(user);
	}
	
	/* (non-Javadoc)
	 * @see cn.net.msg.dao.impl.UserDao#findById(java.lang.String)
	 */
	@Override
	public List<User> findAll(){
		@SuppressWarnings("unchecked")
		List<User> list=template.find ( "from User" );
		return list;
	}
	
	/* (non-Javadoc)
	 * @see cn.net.msg.dao.impl.UserDao#Update(cn.net.msg.model.User)
	 */
	@Override
	public void Update(User user){
		template.update(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByName(String name) {
		List<User> list=template.find("from User u where u.name=?", name);  
		return list;
	}

	@Override
	public User findById(long id) {
		return template.get(User.class, id);
	}
}
