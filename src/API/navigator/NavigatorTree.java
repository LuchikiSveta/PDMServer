package API.navigator;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import API.interfaces.IDBObject;
import API.interfaces.IDBObjectType;

public class NavigatorTree extends JTree{
	
	public NavigatorTree(IDBObjectType type) {
		
		
		
	}
	
	public NavigatorTree(IDBObject object) {
		
		super(new NavigatorTreeNode(object));
		
		collapseRow(0);
		
	}

}

class NavigatorTreeNode extends DefaultMutableTreeNode {
	
	NavigatorTreeNode(IDBObjectType type){
		
	}
	
	NavigatorTreeNode(IDBObject object){
		super(object);
		insert(new DefaultMutableTreeNode(), 0);
	}
	
}
