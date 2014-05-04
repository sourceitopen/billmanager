package com.billmanager.app.daoimpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.billmanager.app.dao.UserDAO;
import com.billmanager.app.domain.auth.Customer;
import com.billmanager.app.domain.auth.User;
import com.billmanager.app.utils.HibernateUtils;

@Service
@Transactional
@Component
public class UserDAOImpl implements UserDAO{
	
	
	public User saveUserData(User user) {
		// TODO Auto-generated method stub
		System.out.println("reached to save user"+user);
		Session hibSession =HibernateUtils.getSessionFactory().openSession();
		try{
		Transaction tx= hibSession.beginTransaction();
		hibSession.save(user);
		tx.commit();
		return user;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			hibSession.close();
		}
		
	}

	public User getUserData(User user) {
		// TODO Auto-generated method stub
		return null;
	}

}
