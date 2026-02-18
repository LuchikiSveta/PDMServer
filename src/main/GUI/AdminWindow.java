package main.GUI;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import API.AttributeTypeProperties;
import API.interfaces.IDBAttributeType;
import API.interfaces.IDBAttributeTypeCollection;
import API.interfaces.IUserSession;

import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.AbstractListModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	
	private JButton btnNewButton = new JButton("Отменить");
	private JButton btnNewButton_1 = new JButton("Применить");
	
	private JList<AttributeTypeProperties> list = new JList<>();
	
	AttributeTypeProperties editAttr = null;
	
	/**
	 * Create the frame.
	 */
	public AdminWindow(IUserSession session) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 629, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JSplitPane splitPane = new JSplitPane();
		tabbedPane.addTab("Атрибуты", null, splitPane, null);
		
		table = new JTable();
		
		IDBAttributeTypeCollection coll = session.getAttributeTypeCollection();
		
		List<AttributeTypeProperties> attrs = coll.select();
		
		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);
		
		JScrollPane scrollPane = new JScrollPane();
		sl_panel.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, panel);
		panel.add(scrollPane);
		
		
		btnNewButton.setEnabled(false);
		sl_panel.putConstraint(SpringLayout.SOUTH, scrollPane, -6, SpringLayout.NORTH, btnNewButton);
		sl_panel.putConstraint(SpringLayout.EAST, scrollPane, 8, SpringLayout.EAST, btnNewButton);
		
		//table.getColumnModel().getColumn(1).setPreferredWidth(250);
		scrollPane.setViewportView(table);
		sl_panel.putConstraint(SpringLayout.SOUTH, btnNewButton, -10, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, btnNewButton, -10, SpringLayout.EAST, panel);
		panel.add(btnNewButton);
		
		btnNewButton_1.setEnabled(false);
		sl_panel.putConstraint(SpringLayout.SOUTH, btnNewButton_1, 0, SpringLayout.SOUTH, btnNewButton);
		sl_panel.putConstraint(SpringLayout.EAST, btnNewButton_1, -6, SpringLayout.WEST, btnNewButton);
		panel.add(btnNewButton_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setLeftComponent(scrollPane_1);
		
		list.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				
				if(editAttr != null) {
					
					list.setSelectedValue(editAttr, true);
					list.repaint();
					return;
					
				}
				
				AttributeTypeProperties attr = list.getSelectedValue();
				
				if(e.getButton() == 3) {
					
					JPopupMenu menu = new JPopupMenu();
					
					JMenuItem addBtn = new JMenuItem("Добавить");
					
					menu.add(addBtn);
					
					addBtn.addActionListener(new ActionListener() {
						
						public void actionPerformed(ActionEvent e) {
							
							AttributeTypeProperties attr = new AttributeTypeProperties();
							
							attr.attributeTypeID = -1;
							attr.attributeName = "";
							attr.valueAttributeType = 1;
							
							setTable(attr);
							
							btnNewButton.setEnabled(true);
							btnNewButton_1.setEnabled(true);
							
							attrs.add(attr);
							
							list.setModel(new AbstractListModel<AttributeTypeProperties>() {
								
								public int getSize() {
									return attrs.size();
								}
								public AttributeTypeProperties getElementAt(int index) {
									return attrs.get(index);
								}
							});
							
							editAttr = attr;
							
						}
					});
					
					menu.show(e.getComponent(), e.getX(), e.getY());
					
				} else {
					
					setTable(attr);
					
				}
				
			}
		});
		list.setModel(new AbstractListModel<AttributeTypeProperties>() {
			
			public int getSize() {
				return attrs.size();
			}
			public AttributeTypeProperties getElementAt(int index) {
				return attrs.get(index);
			}
		});
		scrollPane_1.setViewportView(list);
		
		splitPane.setAutoscrolls(true);
		
		btnNewButton.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				
				if(btnNewButton.isEnabled() == false) return;
				
				btnNewButton.setEnabled(false);
				btnNewButton_1.setEnabled(false);
				
				editAttr = null;
				list.setSelectedIndex(0);
				setTable(list.getSelectedValue());
				
			}
			
		});
		
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				
				if(btnNewButton_1.isEnabled() == false) return;
				
				TableCellEditor editor = table.getCellEditor();
				
				if(editor != null)
					editor.stopCellEditing();
				
				int id = (int)table.getValueAt(0, 1);
				
				if(id == -1) {
					
					editAttr.attributeName = (String) table.getValueAt(1, 1);
					editAttr.valueAttributeType = Integer.parseInt(table.getValueAt(2, 1).toString());
					
					id = coll.create(editAttr);
					
					if(id == -1) {
						
						//---------------
						
					} else {
						
						editAttr.attributeTypeID = id;
						
						list.setModel(new AbstractListModel<AttributeTypeProperties>() {
							
							public int getSize() {
								return attrs.size();
							}
							public AttributeTypeProperties getElementAt(int index) {
								return attrs.get(index);
							}
						});
						
						setTable(editAttr);
						
						btnNewButton.setEnabled(false);
						btnNewButton_1.setEnabled(false);
						
						list.setSelectedValue(editAttr, true);
						
						editAttr = null;
						
					}
					
				} else {
					
					editAttr.attributeName = (String) table.getValueAt(1, 1);
					editAttr.valueAttributeType = Integer.parseInt(table.getValueAt(2, 1).toString());
					
					IDBAttributeType attrType = session.GetAttributeType(id);
					
					attrType.setName(editAttr.attributeName);
					attrType.setAttributeType(editAttr.valueAttributeType);
					
					setTable(editAttr);
					
					btnNewButton.setEnabled(false);
					btnNewButton_1.setEnabled(false);
					
					list.setSelectedValue(editAttr, true);
					
					editAttr = null;
					
				}
				
			}
			
		});
		
		setVisible(true);
		
	}
	
	void setTable(AttributeTypeProperties attr) {
		
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"Идентификатор", attr.attributeTypeID},
				{"Наименование", attr.attributeName},
				{"Тип значения", attr.valueAttributeType},
			},
			new String[] {
				"Параметр", "Значение"
			}
		) {
			
			public boolean isCellEditable(int row, int column) {
		        
				if(column == 0 || row == 0) return false;
				
				return true;
		    }
			
		});
		
		table.getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
			     
				btnNewButton.setEnabled(true);
				btnNewButton_1.setEnabled(true);
				
				editAttr = list.getSelectedValue();
				  
			}
		});
		
	}
	
}
