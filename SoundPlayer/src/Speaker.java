import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Speaker {
	public HBox contentsBox;
	public TextArea speakerID;
	public Button playBtn;
	public Button disconnectBtn;
	public Button soundUpBtn;
	public Button soundDownBtn;
	boolean running = true;;
	static ListView listView;
	Speaker(ListView listView){
		System.out.println("in add function");
    	this.listView = listView;
    	this.listView.setFixedCellSize(30);
    	contentsBox = new HBox();
    	contentsBox.setSpacing(20);
        
    	speakerID = new TextArea("ip ... ");
    	playBtn = new Button("");
        disconnectBtn = new Button("");
        soundUpBtn = new Button("");
        soundDownBtn = new Button("");
        
        int size = 28;
        speakerID.setId("iplabel");
        speakerID.setEditable(true);
        speakerID.setMinSize(330, size);
        speakerID.setMaxSize(330, size);
        playBtn.setMinSize(size, size);
        playBtn.setMaxSize(size, size);
        disconnectBtn.setMinSize(size, size);
        disconnectBtn.setMaxSize(size, size);
        soundUpBtn.setMinSize(size, size);
        soundUpBtn.setMaxSize(size, size);
        soundDownBtn.setMinSize(size, size);
        soundDownBtn.setMaxSize(size, size);

        Image playimg = new Image(getClass().getResourceAsStream("image/play.png"));
        playBtn.setGraphic(new ImageView(playimg));
        Image disconnectimg = new Image(getClass().getResourceAsStream("image/stop.png"));
        disconnectBtn.setGraphic(new ImageView(disconnectimg));
        Image soundupimg = new Image(getClass().getResourceAsStream("image/sound_up.png"));
        soundUpBtn.setGraphic(new ImageView(soundupimg));
        Image sounddownimg = new Image(getClass().getResourceAsStream("image/sound_down.png"));
        soundDownBtn.setGraphic(new ImageView(sounddownimg));
        
        
        speakerID.setId("iplabel");
        playBtn.setId("playbtn");
        disconnectBtn.setId("disconnectbtn");
        soundUpBtn.setId("soundupbtn");
        soundDownBtn.setId("sounddownbtn");
        
        
        playBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(running) {
                	System.out.println("stop");
                    Image stopimg = new Image(getClass().getResourceAsStream("/image/pause.png"));//.getScaledInstance(230, 310,  java.awt.Image.SCALE_SMOOTH);
                    playBtn.setGraphic(new ImageView(stopimg));
                    //playBtn.setId("stopbtn");
                }
                else{
                	System.out.println("play");
                    Image playimg = new Image(getClass().getResourceAsStream("/image/play.png"));//.getScaledInstance(230, 310,  java.awt.Image.SCALE_SMOOTH);
                    playBtn.setGraphic(new ImageView(playimg));
                    //playBtn.setId("playbtn");
                }
                running = !running;
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
        contentsBox.getChildren().addAll(speakerID, playBtn, disconnectBtn, soundDownBtn, soundUpBtn);
        
        listView.getItems().add(contentsBox);
 
	}
}
