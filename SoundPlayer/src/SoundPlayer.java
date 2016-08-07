import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

import com.sun.prism.paint.Color;


import javafx.application.Platform; 
 import javafx.embed.swing.JFXPanel; 
 import javafx.embed.swing.SwingNode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene; 
 import javafx.scene.control.ToggleButton; 
 import javafx.scene.layout.ColumnConstraints; 
 import javafx.scene.layout.GridPane; 

public class SoundPlayer { 
public static int Width = 582;
public static int Height = 560;
public static int Length = 512;
public static int MAX_LENGTH = 512;

	private static Scene createScene(JComponent button1, JComponent button2) { 
		GridPane pane = new GridPane(); 
		pane.getColumnConstraints().add(new ColumnConstraints(100)); 
		pane.getColumnConstraints().add(new ColumnConstraints(200)); 
		SwingNode node1 = new SwingNode(); 
		node1.setContent(button1); 
		pane.add(node1, 0, 0); 
		ToggleButton node2 = new ToggleButton("2"); 
		pane.add(node2, 1, 0); 
		return new Scene(pane); 
	} 
	
	public static void main(String[] args) { 
		SwingUtilities.invokeLater(new Runnable() { 
			@Override 
			public void run() { 
				JFrame frame = new JFrame();
				frame.setSize(Width,Height);
				
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLayout(new BorderLayout());
				frame.getContentPane().setBackground(java.awt.Color.WHITE);
				 
				Draw_graph graph = new Draw_graph();
				frame.add(graph,BorderLayout.CENTER);
								
				JFXPanel panel = new JFXPanel();
				Scene scene = null;
				Group root = new Group();
				try {
	
					scene = new Scene(root);
				    PlayerController player = new PlayerController(root);
				    
				} catch(Exception e) {
					e.printStackTrace();
				}
				panel.setScene(scene); 
				frame.add(panel,BorderLayout.PAGE_END);
				
				frame.setVisible(true);			
				graph.starter();
			} 
		}); 
	} 
} 