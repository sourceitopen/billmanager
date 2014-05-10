package com.billmanager.app.daoimpl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.billmanager.app.dao.CustomerDAO;
import com.billmanager.app.domain.auth.Customer;
import com.billmanager.app.domain.auth.User;
import com.billmanager.app.utils.HibernateUtils;
import com.billmanager.app.utils.Utilities;

@Service
@Transactional
@Component
public class CustomerDAOImpl implements CustomerDAO{

	public Customer findCustomer(String username, String password) {
		
	Session hibSession = HibernateUtils.getSessionFactory().openSession();	
		hibSession.beginTransaction();
		Query query = hibSession.createQuery("from Customer where username = :username AND password = :password");
		query.setParameter("username",username);
		query.setParameter("password",password);
		List customerList =  query.list();
		System.out.println("customer list is="+customerList);
		hibSession.close();
		if(customerList.size()>0)
		return (Customer)customerList.get(0);
		else
			return null;
	}

	@Override
	public void updateUsersInterest(Customer customer) {
		// TODO Auto-generated method stub
		Session hibSession = HibernateUtils.getSessionFactory().openSession();
		Query query = hibSession.createQuery("from User u where u.customer = :customer");
		query.setParameter("customer", customer);
		List<User> users = query.list();		
		//Set<User> users = customer.getUsers();
		for(User user:users){
			Double interest=0.0,amountToBePaid = user.getAmountToBePaid();
			
			try {
				Date lastUpdatedOn = Utilities.convertToDate(user.getLastUpdatedOn());
				if(new Date().getDay()-lastUpdatedOn.getDay()>14){
					amountToBePaid = amountToBePaid+amountToBePaid*0.01;
					interest = amountToBePaid*0.01;
				}
				
				User updateUser =(User)hibSession.load(User.class,user.getId());
				hibSession.beginTransaction();
				updateUser.setAmountToBePaid(amountToBePaid);
				updateUser.setInterest(interest);
				updateUser.setLastUpdatedOn(lastUpdatedOn.toString());
				hibSession.update(updateUser);
				hibSession.close();
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
