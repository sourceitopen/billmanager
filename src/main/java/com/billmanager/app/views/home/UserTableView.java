package com.billmanager.app.views.home;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.billmanager.app.domain.auth.User;

public class UserTableView extends AbstractTableModel {

	private List<User> users;

	public UserTableView(List<User> userList) {
		this.users = userList;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 9;
	}

	@Override
	public int getRowCount() {
		return users.size();
	}

	public User getUserAt(int row) {
		return users.get(row);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		Object value = "??";
		User user = users.get(rowIndex);
		switch (columnIndex) {
		case 0:
			value = user.getName();
			break;
		case 1:
			value = user.getName();

			break;
		case 2:
			value = user.getAmount().toString();

			break;
		case 3:
			value = user.getAmountPaid().toString();

			break;
		case 4:
			value = user.getAmountToBePaid().toString();

			break;
		case 5:
			value = user.getInterest().toString();
			break;
		case 6:
			value = user.getInterestDate();
			break;
		case 7:
			value = user.getBillDate();
			break;
		case 8:
			value = user.getLastUpdatedOn();
			break;
		}

		return value;
	}

}
