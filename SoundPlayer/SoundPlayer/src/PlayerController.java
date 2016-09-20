import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PlayerController extends AnchorPane implements Initializable {
	@FXML AnchorPane  listView;
    @FXML Button playButton, mixButton, clearButton;
    @FXML ListView speakerList;
    @FXML AnchorPane ButtonArea;
    @FXML Label txtLabel;
   
    public static boolean runner = true;
	public ArrayList<Speaker> speaker;
    public PlayerController(Group root, MixSelector mix) throws IOException {
    	mix.player = this;
        speaker = new ArrayList();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Player.fxml"));
        this.getStylesheets().add("application.css");
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
        root.getChildren().add(this); 
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
  
    	assert listView != null : "listView not exist in fxml";
    	assert playButton != null : "playButton not exist in fxml"; 
        assert mixButton != null : "mixButton not exist in fxml"; 
        int height = SoundPlayer.remain_Height/7;
        ButtonArea.setPrefSize(500, 25+height); 
        System.out.println(2+height);
        clearButton.setTextFill(Color.RED);
        
        playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // TODO Auto-generated method stub
            	ArrayList<OutputStream> temp_osList = MixSelector.temp_osList;
            	ArrayList<OutputStream> osList = MixSelector.temp_osList;
            	if(runner) {
            		playButton.textProperty().set("Play");
            		temp_osList.addAll(osList);
            		for(OutputStream item: osList){
            			osList.remove(item);	
            		}
            	}
            	else {
            		playButton.textProperty().set("Pause");
            		osList.addAll(temp_osList);
            		for(OutputStream item: temp_osList){
            			osList.remove(item);	
            		}
            	}
            	runner = !runner;
            }
        });
        clearButton.setOnMouseClicked(new EventHandler<MouseEvent>(){
        	@Override
        	public void handle(MouseEvent envent){
        		for(Speaker item: speaker){
        			item.remove();	
        		}
        	}
        });
        
        mixButton.setOnMouseClicked(new EventHandler<MouseEvent>(){
        	@Override
        	public void handle(MouseEvent envent){
        		MixSelector.view_MixList();
        	}
        });

		Mixer.Info[] mixer_info = AudioSystem.getMixerInfo();
		txtLabel.setText(mixer_info[mixer_info.length-1].toString());
    }
    
    public void speaker_add(Socket socket, String ipaddr){
    	speaker.add(new Speaker(speakerList,socket, ipaddr));
    }
 
    public void mix_change(String mix_name){
    	 Platform.runLater(new Runnable() {
             @Override public void run() {
            	 txtLabel.setText(mix_name);
             }
         });
				
    }
}

