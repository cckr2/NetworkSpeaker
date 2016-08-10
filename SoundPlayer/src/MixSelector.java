import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
 
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;
import javax.swing.JDialog;
 
 
public class MixSelector {   
	JDialog mixList;
	Mixer.Info[] mixer_info;
    public MixSelector(){
    	mixer_info = SoundPlayer.mixer_Info;
    	
    	mixList = new JDialog();
    	mixList.setLayout(new GridLayout(0,1,10,10));
    	JButton[] buttons = new JButton[mixer_info.length];
    	for(int i = 0; i < buttons.length; i++){
            buttons[i] = new JButton(mixer_info[i].toString());
            mixList.add(buttons[i]);
            
            buttons[i].addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent ar){
                    for(int i = 0; i < buttons.length; i++){
                        if(ar.getSource() == buttons[i]){
                        	open_Mixer(mixer_info[i]);
                        	System.out.println("¼±ÅÃµÊ");
                        }
                    }
                }
            });
        }
    	mixList.pack();
    }
    
    public void view_MixList(){
    	mixList.setVisible(true);
    }
    void open_Mixer(Mixer.Info info){    
    	AudioFormat audioFormat = getAudioFormat();
        SoundPlayer.mixer = AudioSystem.getMixer(info);
        try{
        	SoundPlayer.dataLine_info = new DataLine.Info(TargetDataLine.class, audioFormat);
        	SoundPlayer.target_dataLine = (TargetDataLine) SoundPlayer.mixer.getLine(SoundPlayer.dataLine_info);
        	SoundPlayer.target_dataLine.open(audioFormat);
        	SoundPlayer.target_dataLine.start();
        }catch(Exception e){
            e.printStackTrace();
        }    
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