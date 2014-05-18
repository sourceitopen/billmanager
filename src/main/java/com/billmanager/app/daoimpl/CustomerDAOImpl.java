package com.billmanager.app.daoimpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.billmanager.app.constants.AppConstants;
import com.billmanager.app.dao.CustomerDAO;
import com.billmanager.app.domain.auth.Customer;
import com.billmanager.app.domain.auth.User;
import com.billmanager.app.utils.HibernateUtils;
import com.billmanager.app.utils.Utilities;

@Service
@Transactional
@Component
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	SessionFactory sessionFactory;

	public Customer findCustomer(String username, String password) {

		Session hibSession = sessionFactory.openSession();
		hibSession.beginTransaction();
		Query query = hibSession
				.createQuery("from Customer where username = :username AND password = :password");
		query.setParameter("username", username);
		query.setParameter("password", password);
		List customerList = query.list();
		System.out.println("customer list is=" + customerList);
		hibSession.close();
		if (customerList.size() > 0)
			return (Customer) customerList.get(0);
		else
			return null;
	}

	@Override
	public void updateUsersInterest(Customer customer) {
		// TODO Auto-generated method stub

		Session hibSession = sessionFactory.openSession();
		Query query = hibSession
				.createQuery("from User u where u.customer = :customer");
		query.setParameter("customer", customer);
		List<User> users = query.list();
		// Set<User> users = customer.getUsers();
		hibSession.beginTransaction();
		for (User user : users) {
			Double interestRate = AppConstants.INTEREST_RATE, interest = 0.0, amountToBePaid = user
					.getAmountToBePaid();

			DateTime billCreatedOn = Utilities.convertToDateTime(user.getBillDate());
			Interval interval;
			interval = new Interval(billCreatedOn, new DateTime());
			System.out.println("millis for "+interval.toDurationMillis());
			if (interval.toDurationMillis() > 1296000000) {
				interval = new Interval(Utilities.convertToDateTime(user.getInterestDate()),new DateTime());
				int billForDays = (int) (interval.toDurationMillis() / 1000 * 60 * 60 * 24);
				amountToBePaid = amountToBePaid
						+ (amountToBePaid * interestRate * 15) / (100 * 365);
				interest = (amountToBePaid * interestRate * billForDays)
						/ (100 * 365);
			}
			User updateUser = (User) hibSession.load(User.class, user.getId());
			updateUser.setAmountToBePaid(amountToBePaid);
			updateUser.setInterest(interest);
			updateUser.setLastUpdatedOn(Utilities.convertDateTimeToString(new DateTime()));
			hibSession.update(updateUser);
		}
		hibSession.close();
	}

}
