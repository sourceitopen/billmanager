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

	@Autowired
	CustomerDAO customerDao;
	
	@Autowired
	HomeScreen homeScreen;
	
		private static final long serialVersionUID = 1L;

		

		public void createAndShowLogin() {
			setTitle("Login Please");
			setLayout(new GridLayout(0,1));
			JPanel pane = new JPanel(new GridLayout(0,1));
			add(pane);
			setSize(300, 200);
			setVisible(true);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			JLabel nameLabel = new JLabel();
			nameLabel.setText("Name :");
			final JTextField txt = new JTextField();
			JLabel passwordLabel = new JLabel();
			
			passwordLabel.setText("Password :");
			final JPasswordField ps = new JPasswordField(10);
			JButton button = new JButton();
			button.setText("Submit");
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
					System.out.println("customer found , next step to chage layout");	
					}else{
						JOptionPane.showMessageDialog(null, "Wrong username or password");
						System.out.println("prompt error message");
					}
				}
			});
			
		}
		
		Customer findCustomer(String username,char[] password){
			String pass = new String(password);
			System.out.println("password to be checked is "+pass+" and its current class is"+pass.getClass());
			System.out.println("customer dao is"+customerDao);
			Customer customer = customerDao.findCustomer(username, pass);
			return customer;
		}
}
