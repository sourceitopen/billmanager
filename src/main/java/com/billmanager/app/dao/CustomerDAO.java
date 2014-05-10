package com.billmanager.app.dao;

import com.billmanager.app.domain.auth.Customer;

public interface CustomerDAO {

	public Customer findCustomer(String username,String password);
	public void updateUsersInterest(Customer customer);
}
