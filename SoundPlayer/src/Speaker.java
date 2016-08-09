import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class Speaker {
	public HBox contentsBox;
	public Label speakerID;
	public Button playBtn;
	public Button disconnectBtn;
	public Button soundUpBtn;
	public Button soundDownBtn;
	static boolean running = true;
	static ListView listView;
	Speaker(ListView listView){
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
 
	}
}
