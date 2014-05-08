package com.billmanager.app.views.home;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.springframework.beans.factory.annotation.Autowired;

import com.billmanager.app.dao.CustomerDAO;
import com.billmanager.app.domain.auth.Customer;

public class LoginScreen extends JFrame{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	CustomerDAO customerDao;	
	@Autowired
	HomeScreen homeScreen;
	

		public void createAndShowLogin() {
			setTitle("Login Please");
			setLayout(new GridLayout(0,1));
			JPanel pane = new JPanel(new GridLayout(0,1));
			add(pane);
			setSize(400, 200);
			setLocation(600, 300);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			JLabel nameLabel = new JLabel("Name :");
			final JTextField txt = new JTextField();
			JLabel passwordLabel = new JLabel("Password :");
			final JPasswordField ps = new JPasswordField(10);
			JButton button = new JButton("Submit");
			pane.add(nameLabel);
			pane.add(txt);
			pane.add(passwordLabel);
			pane.add(ps);
			pane.add(button);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Customer customer = findCustomer(txt.getText(),ps.getPassword());
					System.out.println("customer found is"+customer);
					if(customer instanceof Customer){
					setVisible(false);	
					homeScreen.startScreen(customer);	
					}else{
						JOptionPane.showMessageDialog(null, "Wrong username or password");
					}
				}
			});
			setVisible(true);
		}
		
		Customer findCustomer(String username,char[] password){
			String pass = new String(password);
			Customer customer = customerDao.findCustomer(username, pass);
			return customer;
		}
}
