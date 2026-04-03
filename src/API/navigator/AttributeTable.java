package API.navigator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import API.SessionKeeper;
import API.interfaces.IDBObject;

import javax.swing.SpringLayout;
import javax.swing.JButton;

public class AttributeTable extends JPanel {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public AttributeTable(long versionID) {
		
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -42, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, this);
		add(scrollPane);
		
		table = new JTable();
		
		DefaultTableModel model = new DefaultTableModel(null, new String[] {"Атрибут", "Значение"}) {
			public boolean isCellEditable(int row, int column) {
				return (column == 0) ? false : true;
			}
		};
		
		IDBObject obj = SessionKeeper.getSession().getObject(versionID);
		
		Object[][] attrs = obj.getAttributeCollection().GetAttributesDataTable();
		
		model.setDataVector(attrs, new String[] {"Атрибут", "Значение"});
		
		table.setModel(model);

		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Отменить");
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton, 9, SpringLayout.SOUTH, scrollPane);
		springLayout.putConstraint(SpringLayout.EAST, btnNewButton, -10, SpringLayout.EAST, this);
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Применить");
		springLayout.putConstraint(SpringLayout.SOUTH, btnNewButton_1, 0, SpringLayout.SOUTH, btnNewButton);
		springLayout.putConstraint(SpringLayout.EAST, btnNewButton_1, -6, SpringLayout.WEST, btnNewButton);
		add(btnNewButton_1);

	}
}
