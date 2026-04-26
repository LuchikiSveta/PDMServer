package API.navigator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import API.SessionKeeper;
import API.interfaces.IDBAttribute;
import API.interfaces.IDBObject;

import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AttributeTable extends JPanel {
	
	private JTable table;

	private long versionID;
	
	/**
	 * Create the panel.
	 */
	public AttributeTable(long versionID) {
		
		this.versionID = versionID;
		
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -42, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, this);
		add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		DefaultTableModel model = new DefaultTableModel(null, new String[] {"Атрибут", "Значение"}) {
			public boolean isCellEditable(int row, int column) {
				return (column == 0) ? false : true;
			}
		};
		
		IDBObject obj = SessionKeeper.getSession().getObject(this.versionID);
		
		Object[][] attrs = obj.getAttributeCollection().GetAttributesDataTable();
		
		model.setDataVector(attrs, new String[] {"Атрибут", "Значение"});
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(table, popupMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Добавить атрибут");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String out = JOptionPane.showInputDialog("Введите ID атрибута");
				
				if(!out.isEmpty()) {
					try {
						
						IDBObject obj = SessionKeeper.getSession().getObject(versionID);
						
						IDBAttribute atr = obj.getAttributeCollection().addAttribute(Integer.parseInt(out));
						
						atr.setValue(JOptionPane.showInputDialog("Введите значение атрибута"));
						
					}catch (Exception ex) {
						
					}
				}
				
			}
		});
		popupMenu.add(mntmNewMenuItem);
		
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
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
