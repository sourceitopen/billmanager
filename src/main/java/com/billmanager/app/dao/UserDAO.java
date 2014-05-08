package com.billmanager.app.dao;

import java.util.List;

import com.billmanager.app.domain.auth.Customer;
import com.billmanager.app.domain.auth.User;

public interface UserDAO {

	public User saveUserData(User user);
	public List<User> getUsersForCustomer(Customer customer);
}
