package org.springframework.samples.mvc.jpa.service;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.samples.mvc.jpa.dao.IUserDao;
import org.springframework.samples.mvc.jpa.dao.JUserDao;
import org.springframework.samples.mvc.jpa.entity.User;
import org.springframework.stereotype.Service;
/**
 * Userҵ��㣬�����־ò�  IUserDao
 * @author liuyt
 * @date  2014-10-30 ����2:37:21
 */
@Service
public class UserService {
	// �Ƽ���Resource�����AutoWriteע��
	@Resource
	private IUserDao userDao;
	
	@Resource
	private JUserDao juserDao;
	
	// �����û�
	public void saveUser(User user) {
		userDao.save(user);
	}
	
	// ɾ���û�������Ҳ����Ϊһ������id��User����
	public void deleteUser(Long id) {
		userDao.delete(id);
	}
	
	// ��ѯ����user����findOneΪ��ѯ����
	public List<User> findAllUsers() {
		return (List<User>) userDao.findAll();
	}
	
	// ��ѯ����user����findOneΪ��ѯ����
	public User findOneUser(Long id) {
		return juserDao.findOne(id);
	}
	
	// ��ѯ����user����findOneΪ��ѯ����
	public User findOneUser(String username,String password) {
		return juserDao.findByUsernameAnPassword(username, password);
	}
		
	
	/**
	 * ����һ����ҳ�����ѯuser���ϣ����������һ��Store�������ԣ�
	 * PageRequest	��spring�Լ���װ�������ҳ�࣬ʵ����Pageable�ӿڣ������������л�õķ�ҳ���ԣ���ǰҳ�ʹ�С���ͻ�ȡ����
	 * ͨ�����÷�ҳ����������һ��Page<>һ�����ͼ��ϵķ�ҳ�������������ͨ����ѯ������ĸ������Ժͽ����
	 * ��ϸ��ṹ�����������Դ��
	 * @param page
	 * @return
	 */
	public Page<User> findAllUserByPage(PageRequest page) {
		return (Page<User>) userDao.findAll(page);
	}
}
