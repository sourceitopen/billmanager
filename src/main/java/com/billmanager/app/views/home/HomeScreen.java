package com.billmanager.app.views.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.billmanager.app.dao.UserDAO;
import com.billmanager.app.domain.auth.Customer;
import com.billmanager.app.domain.auth.User;

@Component
public class HomeScreen extends JFrame {

	@Autowired
	UserDAO userDAO;

	JTable jTable;

	public void startScreen(Customer customer) {
		displayForm(customer);
	}

	void displayForm(Customer customer) {

		setTitle("Home");
		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridLayout(7, 2));
		JPanel searchPanel = new JPanel(new GridLayout(1, 3));
		JPanel dataPanel = new JPanel(new GridLayout(1, 3));
		add(panel, BorderLayout.NORTH);
		add(searchPanel, BorderLayout.CENTER);
		add(dataPanel, BorderLayout.SOUTH);
		setSize(1000, 900);
		setLocation(100, 100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		billInputPanel(panel, customer);
		searchFieldsPanel(searchPanel, customer);
		userListPanel(dataPanel, searchPanel, customer);
		pack();
		setVisible(true);
	}

	void searchFieldsPanel(JPanel searchPanel, final Customer customer) {
		Dimension d = new Dimension(200, 20);
		JLabel label = new JLabel("Search A user here");
		label.setPreferredSize(d);
		searchPanel.add(label);
		final JTextField searchField = new JTextField("Search Content");
		searchField.setColumns(30);
		searchField.setPreferredSize(d);
		searchPanel.add(searchField);
		JButton searchButton = new JButton("Search a user by name");
		searchButton.setPreferredSize(d);
		searchPanel.add(searchButton);
		JButton listAllButton = new JButton("Show all data");
		searchButton.setPreferredSize(d);
		searchPanel.add(listAllButton);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Create a function to search fields
				findUserAndUpdateList(customer,searchField.getText());	
			}
		});
		listAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Create a function to search fields
				setUpTableData(customer);	
			}
		});
	}

	void findUserAndUpdateList(Customer customer,String searchField){
		
		DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
		List<User> users = userDAO.findUserByName(customer, searchField);
		tableModel.setRowCount(0);
		String[] data = new String[8];
		
		for (int i = 0; i < users.size(); i++) {
			data[0] = String.valueOf(i + 1);
			data[1] = users.get(i).getName();
			data[2] = users.get(i).getAmount().toString();
			data[3] = users.get(i).getAmountPaid().toString();
			data[4] = users.get(i).getInterest().toString();
			data[5] = users.get(i).getInterestDate();
			data[6] = users.get(i).getBillDate();
			data[7] = "today";
			tableModel.addRow(data);
		}
		jTable.setModel(tableModel);
	}
	void userListPanel(JPanel dataPanel, JPanel searchPanel, Customer customer) {
		getJTable(dataPanel);
		setUpTableData(customer);

	}

	public void setUpTableData(Customer customer) {
		DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
		// TODO Get User data from database and populate
		tableModel.setRowCount(0);
		String[] data = new String[8];
		List<User> users = userDAO.getUsersForCustomer(customer);

		for (int i = 0; i < users.size(); i++) {

			data[0] = String.valueOf(i + 1);
			data[1] = users.get(i).getName();
			data[2] = users.get(i).getAmount().toString();
			data[3] = users.get(i).getAmountPaid().toString();
			data[4] = users.get(i).getInterest().toString();
			data[5] = users.get(i).getInterestDate();
			data[6] = users.get(i).getBillDate();
			data[7] = "today";
			tableModel.addRow(data);
		}
		jTable.setModel(tableModel);
	}

	private void updateTable(User user, Customer customer) {
		List<User> users = userDAO.getUsersForCustomer(customer);
		DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
		// TODO Get User data from database and populate
		String[] data = new String[8];
		data[0] = String.valueOf(users.size());
		data[1] = user.getName();
		data[2] = user.getAmount().toString();
		data[3] = user.getAmountPaid().toString();
		data[4] = user.getInterest().toString();
		data[5] = user.getInterestDate();
		data[6] = user.getBillDate();
		data[7] = "today";
		tableModel.addRow(data);
		jTable.setModel(tableModel);
	}

	private JTable getJTable(JPanel dataPanel) {
		String[] colNames = { "id", "Name", "Amount", "Amount Paid",
				"Interest", "Interest Date", "Bill Date", "Last Updated On" };
		if (jTable == null) {
			jTable = new JTable() {

				public boolean isCellEditable(int nRow, int nCol) {
					return false;
				}
			};
			JTableHeader header = jTable.getTableHeader();
			header.setBackground(Color.GRAY);
			JScrollPane tableContainer = new JScrollPane(jTable);
			dataPanel.add(tableContainer, BorderLayout.EAST);

		}
		DefaultTableModel contactTableModel = (DefaultTableModel) jTable
				.getModel();
		contactTableModel.setColumnIdentifiers(colNames);
		jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		return jTable;
	}

	void billInputPanel(final JPanel panel, final Customer customer) {
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
		panel.add(new JLabel(""));
		JButton button = new JButton();
		button.setText("Submit");
		panel.add(button);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User user = saveBillInfo(name.getText(), billDate.getText(),
						amount.getText(), amountPaid.getText(),
						interest.getText(), interestDate.getText(), customer);
				if (user instanceof User) {
					JOptionPane.showMessageDialog(null, "User" + user.getName()
							+ " has been successfully saved");
					updateTable(user,customer);
					System.out.println();

				} else
					JOptionPane
							.showMessageDialog(null,
									"There was some error saving user. Please call system admin");
			}
		});
	}

	User saveBillInfo(String name, String billDate, String amount,
			String amountPaid, String interest, String interestDate,
			Customer customer) {
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
