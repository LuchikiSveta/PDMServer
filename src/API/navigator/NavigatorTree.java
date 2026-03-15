package API.navigator;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import API.ObjectTypeProperties;
import API.SessionKeeper;
import API.interfaces.IDBObject;
import API.interfaces.IDBObjectType;
import API.interfaces.IDBObjectTypeCollection;

public class NavigatorTree extends JTree{
	
	
	/**
	 * @wbp.parser.constructor
	 */
	public NavigatorTree(IDBObjectType type) {
		
		setModel(new DefaultTreeModel(new NavigatorTreeNode(type)));
		
		collapseRow(0);
		
		addTreeExpansionListener(new TreeExpansionListener() {
			
			public void treeExpanded(TreeExpansionEvent event) {
				
				((NavigatorTreeNode)event.getPath().getLastPathComponent()).loadChildTreeNode();
				
				((DefaultTreeModel)getModel()).reload(((NavigatorTreeNode)event.getPath().getLastPathComponent()));
				
			}
			
			public void treeCollapsed(TreeExpansionEvent event) {}
		});
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(this, popupMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Создать");
		mntmNewMenuItem.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				
				TreePath path = getSelectionPath();
				
				if(path == null) return;
				
				NavigatorTreeNode node = ((NavigatorTreeNode) path.getLastPathComponent());
				
			}
		});
		popupMenu.add(mntmNewMenuItem);
		
	}
	
	public void addNavigatorNodeTreeListener(NavigatorNodeTreeListener listner){
		
		addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				
				TreePath path = getSelectionPath();
				
				if(path == null) return;
				
				NavigatorTreeNode node = ((NavigatorTreeNode) path.getLastPathComponent());
				
				listner.nodeSelect(new NavigatorNodeTreeEvent(node));
				
			}
		
		});
		
	}
	
	public NavigatorTree(IDBObject object) {
		
		super(new NavigatorTreeNode(object));
		
		collapseRow(0);
		
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

class NavigatorTreeNode extends DefaultMutableTreeNode {
	
	IDBObjectType type;
	IDBObject object;
	
	NavigatorTreeNode(IDBObjectType type){
		super(type);
		this.type = type;
		insert(new DefaultMutableTreeNode(), 0);
		
		
		
	}
	
	NavigatorTreeNode(IDBObject object){
		super(object);
		this.object = object;
		insert(new DefaultMutableTreeNode(), 0);
	}
	
	void loadChildTreeNode() {
		
		if (type != null) {
			
			IDBObjectTypeCollection colection = SessionKeeper.session.getObjectTypeCollection(type.getObjectType());

			removeAllChildren();
			
			for (ObjectTypeProperties type : colection.select()) {
				
				IDBObjectType objType = SessionKeeper.session.getObjectType(type.objectTypeID);
				
				insert(new NavigatorTreeNode(objType), getChildCount());
				
			}
			
			
			
		} else if (object != null) {
			
			
			
		}
		
	}
	
}
