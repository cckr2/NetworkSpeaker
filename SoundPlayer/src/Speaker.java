import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class Speaker extends Thread {
	public HBox contentsBox;
	public Label speakerID;
	public Button playBtn;
	public Button disconnectBtn;
	public Button soundUpBtn;
	public Button soundDownBtn;
	static boolean running = true;
	static ListView listView;
	public static final int DEFAULT_BUFFER_SIZE = 100;
	protected Socket socket;
	protected int num;
	protected OutputStream os;
	public Boolean Send = false;
	
	Speaker(ListView listView, Socket socket){
		
		System.out.println("in add function");
    	this.listView = listView;
    	contentsBox = new HBox();
    	contentsBox.setSpacing(8);
        
    	speakerID = new Label("ip ... ");
    	playBtn = new Button("play");
        disconnectBtn = new Button("disconnect");
        soundUpBtn = new Button("Sound_up");
        soundDownBtn = new Button("Sound_down");
        speakerID.setMinSize(200, 15);
        
        playBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(running) {
                	System.out.println("stop");
                	playBtn.setText("stop");
                }
                else{
                	System.out.println("play");
                	playBtn.setText("play");
                }
                
            }
        });
        
        disconnectBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	System.out.println("disconnect " );
            	listView.getItems().remove(contentsBox);
            }
        });
        soundUpBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	System.out.println("sound_up " );
            }
        });
        soundDownBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	System.out.println("sound_down " );
            }
        });
        contentsBox.getChildren().addAll(speakerID, playBtn, disconnectBtn, soundUpBtn, soundDownBtn);
        
        listView.getItems().add(contentsBox);
	    this.socket = socket;
	   
	    super.start();
	}

	
	public void run() {
	    byte Buffer[] = new byte[DEFAULT_BUFFER_SIZE];
	    
	    try {
			os = socket.getOutputStream();
	        int readBytes;
	        while(true){
		        while (Send) {
		           readBytes = SoundPlayer.target_dataLine.read(Buffer, 0, DEFAULT_BUFFER_SIZE);
		           os.write(Buffer, 0, readBytes);
		        }
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
			
	public void restart(){
		Send = true;
	}
	
	public void pause(){
		Send = false;
	}
	public void finish(){
		try {
			os.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
