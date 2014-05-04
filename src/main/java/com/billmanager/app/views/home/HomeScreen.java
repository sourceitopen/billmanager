package com.billmanager.app.views.home;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.GregorianCalendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.billmanager.app.dao.UserDAO;
import com.billmanager.app.domain.auth.Customer;
import com.billmanager.app.domain.auth.User;

@Component
public class HomeScreen extends JFrame{

	@Autowired
	UserDAO userDAO;
	
	public void startScreen(Customer customer){
		displayForm(customer);
	}
	void displayForm(Customer customer){
		
		setTitle("Home");
		setLayout(new GridLayout(0,2));
		
		JPanel panel = new JPanel(new GridLayout(7,2));
		add(panel);
		setSize(500, 800);
		//setLocation(100, 100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		customizePanel(panel,customer);
		pack();
		setVisible(true);
		
	}
	void customizePanel(JPanel panel,final Customer customer){
		panel.add(new JLabel("Name : "));
		final JTextField name = new JTextField();
		panel.add(name);
		panel.add(new JLabel("Billing Date:"));
		final JTextField billDate = new JTextField();
		panel.add(billDate);
		panel.add(new JLabel("Amount : "));
		final JTextField amount = new JTextField();
		panel.add(amount);
		
		panel.add(new JLabel("Amount Paid : "));
		final JTextField amountPaid = new JTextField();
		panel.add(amountPaid);
		panel.add(new JLabel("Interest : "));
		final JTextField interest = new JTextField();
		panel.add(interest);
		panel.add(new JLabel("Interest Date : "));
		final JTextField interestDate = new JTextField();
		panel.add(interestDate);
		JButton button = new JButton();
		button.setText("Submit");
		panel.add(button);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User user =  saveBillInfo(name.getText(),billDate.getText(),amount.getText(),amountPaid.getText(),interest.getText(),interestDate.getText(),customer);
			}
		});
	}
	User saveBillInfo(String name,String billDate,String amount,String amountPaid,String interest,String interestDate,Customer customer){
		GregorianCalendar calender = new GregorianCalendar();
		calender.getTime();
		User user = new User();
		user.setName(name);
		user.setAmount(Double.parseDouble(amount));
		user.setAmountPaid(Double.parseDouble(amountPaid));
		user.setInterest(Double.parseDouble(interest));
		
		user.setInterestDate(interestDate);
		user.setBillDate(billDate);
		user.setLastUpdatedOn("04/05/2014");
		user.setCustomer(customer);
		
		return userDAO.saveUserData(user);
	}
}
