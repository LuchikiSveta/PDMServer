package main.GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.util.EventObject;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.TableModelListener;

import API.AttributeTypeProperties;
import API.interfaces.IDBAttributeTypeCollection;
import API.interfaces.IUserSession;

import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.AbstractListModel;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;

public class AdminWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	/**
	 * Create the frame.
	 */
	public AdminWindow(IUserSession session) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JSplitPane splitPane = new JSplitPane();
		tabbedPane.addTab("Атрибуты", null, splitPane, null);
		
		table = new JTable();
		
		JList<Object> list = new JList<Object>();
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				System.out.println(list.getSelectedValue());
				
				table.setModel(new DefaultTableModel(
					new Object[][] {
						{"5", "1"},
						{"6", "2"},
						{"7", "3"},
					},
					new String[] {
						"\u041F\u0430\u0440\u0430\u043C\u0435\u0442\u0440", "\u0417\u043D\u0430\u0447\u0435\u043D\u0438\u0435"
					}
				) {
					boolean[] columnEditables = new boolean[] {
					false, true
				};
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});
				
			}
		});
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		IDBAttributeTypeCollection coll = session.getAttributeTypeCollection();
		
		list.setModel(new AbstractListModel<Object>() {
			List<AttributeTypeProperties> values = coll.select();
			public int getSize() {
				return values.size();
			}
			public Object getElementAt(int index) {
				return values.get(index);
			}
		});
		splitPane.setLeftComponent(list);
		
		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);
		
		JScrollPane scrollPane = new JScrollPane();
		sl_panel.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, panel);
		panel.add(scrollPane);
		
		JButton btnNewButton = new JButton("Отменить");
		sl_panel.putConstraint(SpringLayout.SOUTH, scrollPane, -6, SpringLayout.NORTH, btnNewButton);
		sl_panel.putConstraint(SpringLayout.EAST, scrollPane, 8, SpringLayout.EAST, btnNewButton);
		
		//table.getColumnModel().getColumn(1).setPreferredWidth(250);
		scrollPane.setViewportView(table);
		sl_panel.putConstraint(SpringLayout.SOUTH, btnNewButton, -10, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, btnNewButton, -10, SpringLayout.EAST, panel);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Применить");
		sl_panel.putConstraint(SpringLayout.SOUTH, btnNewButton_1, 0, SpringLayout.SOUTH, btnNewButton);
		sl_panel.putConstraint(SpringLayout.EAST, btnNewButton_1, -6, SpringLayout.WEST, btnNewButton);
		panel.add(btnNewButton_1);
		
		setVisible(true);
		
	}
}
