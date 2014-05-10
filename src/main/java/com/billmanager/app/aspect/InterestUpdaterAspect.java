package com.billmanager.app.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

import com.billmanager.app.dao.CustomerDAO;
import com.billmanager.app.domain.auth.Customer;


@Aspect
public class InterestUpdaterAspect {

	@Autowired
	CustomerDAO customerDAO;
	
	@Before("execution(* com.billmanager.app.views.home.HomeScreen.startScreen(..)) && args(customer)")
	void updateInterestForAllUsers(Customer customer){
		System.out.println("before advice customer users are is"+customer);
		customerDAO.updateUsersInterest(customer);
	}
}
