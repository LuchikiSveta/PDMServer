package API.navigator;

import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import API.SessionKeeper;
import API.interfaces.IDBObjectCollection;
import API.interfaces.IDBObjectType;
import API.kernel.search.ColumnDescriptor;
import API.kernel.search.DBRecordSetParams;

import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NavigatorObjectBrowser extends JPanel {
	private JTable table;

	public NavigatorObjectBrowser() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, panel, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, panel, 26, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, panel, 0, SpringLayout.EAST, this);
		add(panel);
		
		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.SOUTH, panel);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -26, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, this);
		add(scrollPane);
		
		table = new JTable();
		//table.setModel();
		scrollPane.setViewportView(table);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(table, popupMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Карточка объекта");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				long id = Long.parseLong(table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
				
				JFrame frame = new JFrame();
				frame.setSize(new Dimension(400, 300));
				frame.add(new AttributeTable(id));
				frame.setVisible(true);
				
			}
		});
		popupMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Открыть");
		popupMenu.add(mntmNewMenuItem_1);

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
	
	public void show(IDBObjectType type) {
		
		DefaultTableModel model = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		
		//SessionKeeper.session.
		
		model.addColumn("Идентификатор версии объекта");
		model.addColumn("Обозначение");
		model.addColumn("Наименование");
		
		IDBObjectCollection collection = SessionKeeper.session.getObjectCollection(type.getObjectType());
		
		ColumnDescriptor[] cols = new ColumnDescriptor[] {
			new ColumnDescriptor("Обозначение"),
			new ColumnDescriptor("Наименование"),
		};
		
		DBRecordSetParams setParam = new DBRecordSetParams(null, cols);
		
		Object[][] rows;
		try {
			rows = collection.select(setParam);
			
			for (Object[] row : rows) {
				
				model.addRow(row);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		table.setModel(model);
		
	}
	
}
