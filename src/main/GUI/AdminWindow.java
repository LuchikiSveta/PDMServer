package main.GUI;

import java.awt.BorderLayout;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;

import API.AttributeTypeProperties;
import API.ObjectTypeProperties;
import API.interfaces.IDBAttributeType;
import API.interfaces.IDBAttributeTypeCollection;
import API.interfaces.IDBObjectTypeCollection;
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
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultMutableTreeNode;

public class AdminWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable attrTable;
	
	private JButton btnCancel = new JButton("Отменить");
	private JButton btnConfirm = new JButton("Применить");
	
	private JButton btnObjsCancel = new JButton("Отменить");
	private JButton btnObjsConfirm = new JButton("Применить");
	
	private JList<AttributeTypeProperties> list = new JList<>();
	
	private AttributeTypeProperties editAttr = null;
	
	private TreePath editObj = null;
	
	IUserSession session;
	private JTable objsTable;
	private JTree objsTree;
	
	/**
	 * Create the frame.
	 */
	public AdminWindow(IUserSession session) {
		
		this.session = session;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 629, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JSplitPane attrsSplitPane = new JSplitPane();
		tabbedPane.addTab("Атрибуты", null, attrsSplitPane, null);
		
		attrTable = new JTable();
		
		IDBAttributeTypeCollection coll = session.getAttributeTypeCollection();
		
		List<AttributeTypeProperties> attrs = coll.select();
		
		JPanel attrTablePanel = new JPanel();
		attrsSplitPane.setRightComponent(attrTablePanel);
		SpringLayout sl_attrTablePanel = new SpringLayout();
		attrTablePanel.setLayout(sl_attrTablePanel);
		
		JScrollPane attrTableScrollPane = new JScrollPane();
		sl_attrTablePanel.putConstraint(SpringLayout.WEST, attrTableScrollPane, 0, SpringLayout.WEST, attrTablePanel);
		sl_attrTablePanel.putConstraint(SpringLayout.NORTH, attrTableScrollPane, 0, SpringLayout.NORTH, attrTablePanel);
		sl_attrTablePanel.putConstraint(SpringLayout.EAST, attrTableScrollPane, 10, SpringLayout.EAST, btnCancel);
		attrTablePanel.add(attrTableScrollPane);
		
		
		btnCancel.setEnabled(false);
		sl_attrTablePanel.putConstraint(SpringLayout.SOUTH, attrTableScrollPane, -6, SpringLayout.NORTH, btnCancel);
		
		//table.getColumnModel().getColumn(1).setPreferredWidth(250);
		attrTableScrollPane.setViewportView(attrTable);
		sl_attrTablePanel.putConstraint(SpringLayout.SOUTH, btnCancel, -10, SpringLayout.SOUTH, attrTablePanel);
		sl_attrTablePanel.putConstraint(SpringLayout.EAST, btnCancel, -10, SpringLayout.EAST, attrTablePanel);
		attrTablePanel.add(btnCancel);
		
		btnConfirm.setEnabled(false);
		sl_attrTablePanel.putConstraint(SpringLayout.SOUTH, btnConfirm, 0, SpringLayout.SOUTH, btnCancel);
		sl_attrTablePanel.putConstraint(SpringLayout.EAST, btnConfirm, -6, SpringLayout.WEST, btnCancel);
		attrTablePanel.add(btnConfirm);
		
		JScrollPane attrListScrollPane = new JScrollPane();
		attrsSplitPane.setLeftComponent(attrListScrollPane);
		
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
							
							btnCancel.setEnabled(true);
							btnConfirm.setEnabled(true);
							
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
		attrListScrollPane.setViewportView(list);
		
		attrsSplitPane.setAutoscrolls(true);
		
		JSplitPane objsSplitPane = new JSplitPane();
		tabbedPane.addTab("Объекты", null, objsSplitPane, null);
		
		JScrollPane objsScrollPane = new JScrollPane();
		objsSplitPane.setLeftComponent(objsScrollPane);
		
		JSplitPane relSplitPane = new JSplitPane();
		tabbedPane.addTab("Связи", null, relSplitPane, null);
		
		btnCancel.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				
				if(btnCancel.isEnabled() == false) return;
				
				btnCancel.setEnabled(false);
				btnConfirm.setEnabled(false);
				
				editAttr = null;
				list.setSelectedIndex(0);
				setTable(list.getSelectedValue());
				
			}
			
		});
		
		btnConfirm.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				
				if(btnConfirm.isEnabled() == false) return;
				
				TableCellEditor editor = attrTable.getCellEditor();
				
				if(editor != null)
					editor.stopCellEditing();
				
				int id = (int)attrTable.getValueAt(0, 1);
				
				if(id == -1) {
					
					editAttr.attributeName = (String) attrTable.getValueAt(1, 1);
					editAttr.valueAttributeType = Integer.parseInt(attrTable.getValueAt(2, 1).toString());
					
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
						
						btnCancel.setEnabled(false);
						btnConfirm.setEnabled(false);
						
						list.setSelectedValue(editAttr, true);
						
						editAttr = null;
						
					}
					
				} else {
					
					editAttr.attributeName = (String) attrTable.getValueAt(1, 1);
					editAttr.valueAttributeType = Integer.parseInt(attrTable.getValueAt(2, 1).toString());
					
					IDBAttributeType attrType = session.getAttributeType(id);
					
					attrType.setName(editAttr.attributeName);
					attrType.setAttributeType(editAttr.valueAttributeType);
					
					setTable(editAttr);
					
					btnCancel.setEnabled(false);
					btnConfirm.setEnabled(false);
					
					list.setSelectedValue(editAttr, true);
					
					editAttr = null;
					
				}
				
			}
			
		});
		
		ObjectTypeProperties rootObj = new ObjectTypeProperties();
		
		rootObj.objectTypeID = 0;
		rootObj.objectName = "Объекты";
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootObj);
		
		root.add(new DefaultMutableTreeNode());
		
		DefaultTreeModel treeModel = new DefaultTreeModel(root);
		
		objsTree = new JTree();
		
		objsTree.setModel(treeModel);
		
		objsTree.collapseRow(0);
		
		objsScrollPane.setViewportView(objsTree);
		
		JPanel objsTalePanel = new JPanel();
		objsSplitPane.setRightComponent(objsTalePanel);
		SpringLayout sl_objsTalePanel = new SpringLayout();
		sl_objsTalePanel.putConstraint(SpringLayout.NORTH, btnObjsConfirm, 0, SpringLayout.NORTH, btnObjsCancel);
		sl_objsTalePanel.putConstraint(SpringLayout.EAST, btnObjsConfirm, -6, SpringLayout.WEST, btnObjsCancel);
		sl_objsTalePanel.putConstraint(SpringLayout.EAST, btnObjsCancel, -10, SpringLayout.EAST, objsTalePanel);
		objsTalePanel.setLayout(sl_objsTalePanel);
		
		JScrollPane objsTableScrollPane = new JScrollPane();
		sl_objsTalePanel.putConstraint(SpringLayout.NORTH, btnObjsCancel, 6, SpringLayout.SOUTH, objsTableScrollPane);
		sl_objsTalePanel.putConstraint(SpringLayout.NORTH, objsTableScrollPane, 0, SpringLayout.NORTH, objsTalePanel);
		sl_objsTalePanel.putConstraint(SpringLayout.WEST, objsTableScrollPane, 0, SpringLayout.WEST, objsTalePanel);
		sl_objsTalePanel.putConstraint(SpringLayout.SOUTH, objsTableScrollPane, -39, SpringLayout.SOUTH, objsTalePanel);
		sl_objsTalePanel.putConstraint(SpringLayout.EAST, objsTableScrollPane, 0, SpringLayout.EAST, objsTalePanel);
		objsTalePanel.add(objsTableScrollPane);
		
		objsTable = new JTable();
		objsTableScrollPane.setViewportView(objsTable);
		btnObjsConfirm.setEnabled(false);
		objsTalePanel.add(btnObjsConfirm);
		btnObjsCancel.setActionCommand("Отменить");
		
		btnObjsCancel.setEnabled(false);
		objsTalePanel.add(btnObjsCancel);
		
		objsTree.addTreeExpansionListener(new TreeExpansionListener() {
			
			public void treeExpanded(TreeExpansionEvent event) {
				
				DefaultMutableTreeNode lastNode = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
				
				addNodeTree(lastNode, treeModel);
				
			}
			
			public void treeCollapsed(TreeExpansionEvent event) { }
			
		});
		
		objsTree.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				
				if(editObj != null) {
					
					objsTree.setSelectionPath(editObj);
					
					//editObj
					
					//objsTree.setSelectedValue(editAttr, true);
					//objsTree.repaint();
					return;
					
				}
				
				TreePath path = objsTree.getSelectionPath();
				
				if(path == null) return;
				
				ObjectTypeProperties obj = (ObjectTypeProperties) ((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject();
				
				if(obj == null) return;
				
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
							
							btnCancel.setEnabled(true);
							btnConfirm.setEnabled(true);
							
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
					
					setTable(obj);
					
				}
				
			}
			
		});
		
		btnObjsCancel.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				
				if(btnObjsCancel.isEnabled() == false) return;
				
				btnObjsCancel.setEnabled(false);
				btnObjsConfirm.setEnabled(false);
				
				editObj = null;
				//list.setSelectedIndex(0);
				//setTable(list.getSelectedValue());
				
			}
			
		});
		
		btnObjsConfirm.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				
				if(btnObjsConfirm.isEnabled() == false) return;
				
				TableCellEditor editor = objsTable.getCellEditor();
				
				if(editor != null)
					editor.stopCellEditing();
				
				int id = (int)objsTable.getValueAt(0, 1);
				
				if(id == -1) {
					
					ObjectTypeProperties obj = (ObjectTypeProperties) ((DefaultMutableTreeNode) editObj.getLastPathComponent()).getUserObject();
					
					obj.objectName = (String) objsTable.getValueAt(1, 1);
					obj.attributes = (String) objsTable.getValueAt(2, 1);
					
					//session
					
					//id = coll.create(editObj);
					
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
						
						btnCancel.setEnabled(false);
						btnConfirm.setEnabled(false);
						
						list.setSelectedValue(editAttr, true);
						
						editAttr = null;
						
					}
					
				} else {
					
					editAttr.attributeName = (String) attrTable.getValueAt(1, 1);
					editAttr.valueAttributeType = Integer.parseInt(attrTable.getValueAt(2, 1).toString());
					
					IDBAttributeType attrType = session.getAttributeType(id);
					
					attrType.setName(editAttr.attributeName);
					attrType.setAttributeType(editAttr.valueAttributeType);
					
					setTable(editAttr);
					
					btnCancel.setEnabled(false);
					btnConfirm.setEnabled(false);
					
					list.setSelectedValue(editAttr, true);
					
					editAttr = null;
					
				}
				
			}
			
		});
		
		setVisible(true);
		
	}
	
	void addNodeTree(DefaultMutableTreeNode rootNode, DefaultTreeModel treeModel) {
		
		ObjectTypeProperties obj = (ObjectTypeProperties) rootNode.getUserObject();
		
		IDBObjectTypeCollection objCollection = session.getObjectTypeCollection(obj.objectTypeID);
		
		while(rootNode.getChildCount() > 0) {
			rootNode.remove(0);
		}
		
		for (ObjectTypeProperties prop : objCollection.select()) {
			
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(prop);
			
			node.insert(new DefaultMutableTreeNode(), 0);
			
			rootNode.add(node);
			
		}
		
		treeModel.reload(rootNode);
		
	}
	
	void setTable(AttributeTypeProperties attr) {
		
		attrTable.setModel(new DefaultTableModel(
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
		
		attrTable.getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
			     
				btnCancel.setEnabled(true);
				btnConfirm.setEnabled(true);
				
				editAttr = list.getSelectedValue();
				  
			}
		});
		
	}
	
	void setTable(ObjectTypeProperties attr) {
		
		objsTable.setModel(new DefaultTableModel(
			new Object[][] {
				{"Идентификатор", attr.objectTypeID},
				{"Наименование", attr.objectName},
				{"Атрибуты", ""},
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
		
		objsTable.getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
			     
				btnObjsCancel.setEnabled(true);
				btnObjsConfirm.setEnabled(true);
				
				TreePath path = objsTree.getSelectionPath();
				
				if(path == null) return;
				
				editObj = path;
				  
			}
		});
		
	}
	
}
