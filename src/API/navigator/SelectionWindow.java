package API.navigator;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;

import API.DBObjectType;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;

public class SelectionWindow extends JFrame{
	
	public SelectionWindow(){
		
		NavigatorObjectBrowser browser = new NavigatorObjectBrowser();
		
		JSplitPane splitPane = new JSplitPane();
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		NavigatorTree tree = new NavigatorTree(new DBObjectType(0));
		scrollPane.setViewportView(tree);
		
		JPanel mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		SpringLayout sl_mainPanel = new SpringLayout();
		sl_mainPanel.putConstraint(SpringLayout.NORTH, splitPane, 0, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, splitPane, 0, SpringLayout.WEST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, splitPane, -40, SpringLayout.SOUTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, splitPane, 0, SpringLayout.EAST, mainPanel);
		mainPanel.setLayout(sl_mainPanel);
		
		mainPanel.add(splitPane);
		
		tree.addNavigatorNodeTreeListener(new NavigatorNodeTreeListener() {
			
			public void nodeSelect(NavigatorNodeTreeEvent e) {
				
				if(e.node.type != null) {
					
					browser.show(e.node.type);
					
				} else if(e.node.object != null) {
					
				}
				
			}
		});
		
		splitPane.setRightComponent(browser);
		
		setSize(529, 407);
		setVisible(true);
		
	}

}
