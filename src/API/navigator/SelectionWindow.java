package API.navigator;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;

import API.DBObjectType;

public class SelectionWindow extends JFrame{
	
	public SelectionWindow(){
		
		JSplitPane splitPane = new JSplitPane();
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		NavigatorTree tree = new NavigatorTree(new DBObjectType(0));
		scrollPane.setViewportView(tree);
		
		setSize(800, 500);
		setVisible(true);
		
	}

}
