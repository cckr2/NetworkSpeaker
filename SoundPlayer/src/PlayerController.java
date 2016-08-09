

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.Timeline;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 *
 * @author fajar
 */
public class PlayerController extends AnchorPane implements Initializable {

    @FXML
    Button playButton;
    @FXML
    Button mixButton;
    @FXML
    ListView speakerListView;
	public boolean speaker[];
	public final int MAX = 10;
	public boolean sig_allcontrol = true;
	public boolean sig_speaker[];


    
    public PlayerController(Group root) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Player.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
        root.getChildren().add(this);
    
        //for list manage
        speaker = new boolean[10];
        sig_speaker = new boolean[10];
        for(int i=0; i<MAX; i++) speaker[i]=false;
        for(int i=0; i<MAX; i++) sig_speaker[i]=true;
        
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert playButton != null : "playButton gagal injeksi"; 
        assert mixButton != null : "pauseButton gagal injeksi"; 

        playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // TODO Auto-generated method stub
            	if(sig_allcontrol) {
            		playButton.textProperty().set("PAUSE");
            	}
            	else {
            		playButton.textProperty().set("PLAY");
            	}
            	sig_allcontrol = !sig_allcontrol;
            }
        });
        mixButton.setOnMouseClicked(new EventHandler<MouseEvent>(){
        	@Override
        	public void handle(MouseEvent envent){
        		Speaker spaekr = new Speaker(speakerListView);
        	}
        });
    }
    
   
 
}

