package com.billmanager.app.dao;

import com.billmanager.app.domain.auth.User;

public interface UserDAO {

	public User saveUserData(User user);
	public User getUserData(User user);
}
