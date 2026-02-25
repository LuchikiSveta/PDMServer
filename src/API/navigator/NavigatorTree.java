package API.navigator;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import API.ObjectTypeProperties;
import API.SessionKeeper;
import API.interfaces.IDBObject;
import API.interfaces.IDBObjectType;
import API.interfaces.IDBObjectTypeCollection;

public class NavigatorTree extends JTree{
	
	public NavigatorTree(IDBObjectType type) {
		
		setModel(new DefaultTreeModel(new NavigatorTreeNode(type)));
		
		collapseRow(0);
		
		addTreeExpansionListener(new TreeExpansionListener() {
			
			public void treeExpanded(TreeExpansionEvent event) {
				
				System.out.println(event.getPath().getLastPathComponent());
				
				((NavigatorTreeNode)event.getPath().getLastPathComponent()).loadChildTreeNode();
				
				((DefaultTreeModel)getModel()).reload(((NavigatorTreeNode)event.getPath().getLastPathComponent()));
				
			}
			
			public void treeCollapsed(TreeExpansionEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	public NavigatorTree(IDBObject object) {
		
		super(new NavigatorTreeNode(object));
		
		collapseRow(0);
		
	}

}

class NavigatorTreeNode extends DefaultMutableTreeNode {
	
	IDBObjectType type;
	IDBObject object;
	
	NavigatorTreeNode(IDBObjectType type){
		this.type = type;
		super(type);
		insert(new DefaultMutableTreeNode(), 0);
		
		
		
	}
	
	NavigatorTreeNode(IDBObject object){
		this.object = object;
		super(object);
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
