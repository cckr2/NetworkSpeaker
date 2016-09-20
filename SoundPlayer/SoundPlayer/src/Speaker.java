import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class Speaker{
	public HBox contentsBox;
	public TextArea speakerID;
	public Button playBtn;
	public Button disconnectBtn;
	public Button soundUpBtn;
	public Button soundDownBtn;
	boolean running = true;
	static ListView listView;
	public static final int DEFAULT_BUFFER_SIZE = 1000;
	protected Socket socket;
	protected OutputStream os;
	
	Speaker(ListView listView,Socket socket, String ipaddr){
		System.out.println("in add function");
    	this.listView = listView;
    	this.listView.setFixedCellSize(30);
    	this.socket = socket;
    	try {  		
			os = socket.getOutputStream();
			MixSelector.osList.add(os);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	contentsBox = new HBox();
    	contentsBox.setSpacing(20);
        
    	speakerID = new TextArea(ipaddr);
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

        Image playImg = new Image(getClass().getResourceAsStream("image/play.png"));
        Image pauseImg = new Image(getClass().getResourceAsStream("image/pause.png"));
        ImageView playImgView = new ImageView(playImg);
        ImageView pauseImgView = new ImageView(pauseImg);
        playBtn.setGraphic(pauseImgView);
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
        
        speakerID.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    System.out.println(speakerID.getText());
                }
            }
        });
        
        playBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(running) {
                	playBtn.setGraphic(playImgView);
                }
                else{
                	playBtn.setGraphic(pauseImgView);
                }
                running = !running;
            }
        });
        
        disconnectBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	remove();
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
	public void remove(){
	   	System.out.println("disconnect " );
    	listView.getItems().remove(contentsBox);
		MixSelector.osList.remove(os);
		try {
			os.close();
			socket.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}
	@Override
    protected void finalize() throws Throwable { //¼Ò¸êÀÚ ÇÔ¼ö                                           
		System.out.println("¼Ò¸ê");
    }
}
