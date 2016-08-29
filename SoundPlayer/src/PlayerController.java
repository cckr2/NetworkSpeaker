import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class PlayerController extends AnchorPane implements Initializable {
	@FXML AnchorPane  listView;
    @FXML Button playButton, mixButton;
    @FXML ListView speakerList;
    @FXML AnchorPane ButtonArea;
    public boolean runner = true;
	
    public PlayerController(Group root) throws IOException {
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
        playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // TODO Auto-generated method stub
            	if(runner) {
            		playButton.textProperty().set("PAUSE");
            	}
            	else {
            		playButton.textProperty().set("PLAY");
            	}
            	runner = !runner;
            }
        });
        mixButton.setOnMouseClicked(new EventHandler<MouseEvent>(){
        	@Override
        	public void handle(MouseEvent envent){
        		new Speaker(speakerList);
        	}
        });
    }
    
   
 
}

