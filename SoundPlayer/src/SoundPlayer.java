import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
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
	public static Mixer mixer;
	public static Mixer.Info[] mixer_Info;
	public static TargetDataLine target_dataLine;
	public static DataLine.Info dataLine_info;
	public static int remain_Height;
	public static DrawGraph graph;
	static int height, width;
	
	public static void main(String[] args) {		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int screanHeight = gd.getDisplayMode().getHeight();
		remain_Height = screanHeight-1080;
		height = 550;
		width = 575;
		if(remain_Height>0)
			height = (remain_Height)/4 + height; 
		 
		mixer_Info = AudioSystem.getMixerInfo();
		MixSelector mixSelector = new MixSelector();
		
		System.out.println(height);
		SwingUtilities.invokeLater(new Runnable() { 
			@Override 
			public void run() { 
				JFrame frame = new JFrame();
				frame.setBounds(0, 0, width, height);
				
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLayout(new BorderLayout());
				frame.getContentPane().setBackground(new java.awt.Color(246,246,246));
				 
				DrawGraph graph = new DrawGraph();
				frame.add(graph,BorderLayout.CENTER);
								
				JFXPanel panel = new JFXPanel();
				panel.setBackground(new java.awt.Color(246,246,246));
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
		mixSelector.view_MixList();
	} 
	
	public static AudioFormat getAudioFormat() {
        float sampleRate = 96000.0F;
        int sampleSizeInBits = 16;
        int channels = 2;
        boolean singed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, singed,
                bigEndian);
    }
} 