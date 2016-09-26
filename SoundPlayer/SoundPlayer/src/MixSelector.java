import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
 
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;
import javax.swing.JDialog;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
 
public class MixSelector {   
	public final int DEFAULT_BUFFER_SIZE = 1000;
	byte Buffer[] = new byte[DEFAULT_BUFFER_SIZE];
	public Mixer mixer;
	public Mixer.Info[] mixer_Info;
	public TargetDataLine target_dataLine;
	public DataLine.Info dataLine_info;
	public PlayerController player;
	static public ArrayList<OutputStream> osList,temp_osList;
	static JDialog mixList;
	Mixer.Info[] mixer_info;
	
	static int length = SoundPlayer.length;
	public static int[] amp = new int[length];
	
	Timer timer=null;
	int readBytes;
	
    public MixSelector(){
    	osList = new ArrayList();
    	temp_osList = new ArrayList();
    	mixer_info = AudioSystem.getMixerInfo();
    	mixList = new JDialog();
    	mixList.setLayout(new GridLayout(0,1,10,10));
    	JButton[] buttons = new JButton[mixer_info.length];
    	for(int i = 0; i < buttons.length; i++){
            buttons[i] = new JButton(i+". "+mixer_info[i].toString());
            mixList.add(buttons[i]);
            
            buttons[i].addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent ar){
                    for(int i = 0; i < buttons.length; i++){
                        if(ar.getSource() == buttons[i]){
                        	open_Mixer(mixer_info[i],i);
                        	start_read();
                        }
                    }
                }
            });
        }
    	mixList.pack();
    }
    
    public static void view_MixList(){
    	mixList.setVisible(true);
    }
    
    void open_Mixer(Mixer.Info info,int i){    
    	player.mix_change(i+". "+info.toString());
    	AudioFormat audioFormat = getAudioFormat();
        mixer = AudioSystem.getMixer(info);
        try{
        	if(timer!=null){
        		target_dataLine.stop();
            	target_dataLine.close();
        		timer.cancel();
        	}else{
        		dataLine_info = new DataLine.Info(TargetDataLine.class, audioFormat);
        	}
        	target_dataLine = (TargetDataLine) mixer.getLine(dataLine_info);
        	target_dataLine.open(audioFormat);
        	target_dataLine.start();
        }catch(Exception e){
            e.printStackTrace();
        }    
    }
    
    public void start_read(){
        timer = new Timer();
        Timer soundTimer = new Timer();
        soundTimer.schedule(new TimerTask(){
            @Override
            public void run(){	
                while (true) {      
                	readBytes = target_dataLine.read(Buffer, 0, DEFAULT_BUFFER_SIZE);
                	for(OutputStream item: osList){
                    	try {
                    		try{
                    			item.write(Buffer, 0, readBytes);
							}catch(SocketException e2){
								//여기에 사라진 소켓은 죽이는거 넣어함 
								//즉 OutputStram을 Speaker로 변경해야함 ㅋㅋㅋ
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }	    	
                }
            }
        }, 0, 1);
        
        timer.schedule(new TimerTask(){
            @Override
            public void run(){
	            while (true) {
	                int max = 0, min = 0;
	                int count = 0;
	                for (int i = 0; i < DEFAULT_BUFFER_SIZE; i++) {      
	                    if ((i + 1) % (DEFAULT_BUFFER_SIZE/SoundPlayer.length) == 0) {
	                        max = Integer.max(max, Buffer[i]);
	                        min = Integer.min(min, Buffer[i]);
	                        count = i%SoundPlayer.length;
	                        amp[count] = ((max+min))+50;
	                        max = 0;
	                        min = 0;                            
	                    }
	                }            
	            }
            }
        }, 0, 1);
        SoundPlayer.length = length-10;
    }
       
    AudioFormat getAudioFormat(){
        float sampleRate = 44100.0F;       //8000,11025,16000,22050,44100
        int sampleSizeInBits = 16;       //8,16
        int channels = 2;       //1,2
        boolean signed = true;       //true,false
        boolean bigEndian = false;       //true,false
        return new AudioFormat(sampleRate,sampleSizeInBits,channels,signed,bigEndian);
    }
}