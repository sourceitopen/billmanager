package com.billmanager.app.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.joda.time.DateTime;
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
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public User saveUserData(User user) {
		// TODO Auto-generated method stub
		System.out.println("reached to save user" + user);
		Session hibSession = sessionFactory.openSession();
		try {
			Transaction tx = hibSession.beginTransaction();
			hibSession.save(user);
			tx.commit();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hibSession.close();
		}

	}

	@Override
	public List<User> getUsersForCustomer(Customer customer) {
		// TODO Auto-generated method stub
		ArrayList<User> users = new ArrayList<User>();
		Session hibSession = sessionFactory.openSession();
		try {
			Query query = hibSession
					.createQuery("from User u where u.amountToBePaid!=0");
			users = (ArrayList<User>) query.list();
			System.out.println("users are" + users);

			return users;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hibSession.close();
		}
	}

	@Override
	public List<User> findUserByName(Customer customer, String name) {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<User>();
		Session hibSession = sessionFactory.openSession();
		try {
			Query query = hibSession
					.createQuery("from User u where u.name like :name AND u.amountToBePaid!=0");
			query.setParameter("name", name);
			users = query.list();
			System.out.println("user after finding by username" + users);

			return users;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hibSession.close();
		}
	}

	@Override
	public User updateUserData(User user) {
		// System.out.println("reached to save user" + user);
		Session hibSession = sessionFactory.openSession();
		User updateUser = (User) hibSession.load(User.class, user.getId());
		updateUser.setName(user.getName());
		updateUser.setAmount(user.getAmount());
		if (user.getAmountPaid() != null || user.getAmountPaid() != 0) {
			updateUser.setLastUpdatedOn(user.getLastUpdatedOn());
			updateUser.setInterestDate(user.getInterestDate());
		}
		updateUser.setAmountPaid(user.getAmountPaid()+updateUser.getAmountPaid());
		updateUser.setBillDate(user.getBillDate());
		updateUser.setInterest(user.getInterest());
		updateUser.setAmountToBePaid(user.getAmountToBePaid()
				- user.getAmountPaid());
		System.out.println("user found is - " + updateUser.getId());
		try {
			Transaction tx = hibSession.beginTransaction();
			hibSession.saveOrUpdate(updateUser);
			tx.commit();
			return updateUser;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hibSession.close();
		}
	}

}
