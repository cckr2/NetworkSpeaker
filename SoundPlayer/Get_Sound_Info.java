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
 
 
public class Get_Sound_Info {
 
    Mixer mixer;
    public static TargetDataLine tdl;
    AudioFormat af;
    
    DataLine.Info dataLine_info;
    
    byte[] data = new byte[44100];
    public static int[] amp;
    Timer timer;
    boolean _runner = false;
    
    JDialog dialog;
    
    public Get_Sound_Info(){
        try{
            
            amp = new int[Grapher.MAX_LENGTH];
            
            Mixer.Info[] mixer_info = AudioSystem.getMixerInfo();
            af = getAudioFormat();
            
            
            dialog = new JDialog();
            
            JButton[] buttons = new JButton[mixer_info.length];
            
            dialog.setLayout(new GridLayout(0,1,10,10));
            
            for(int i = 0; i < buttons.length; i++){
                buttons[i] = new JButton(mixer_info[i].toString());
                dialog.add(buttons[i]);
                
                buttons[i].addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent ar){
                        for(int i = 0; i < buttons.length; i++){
                            if(ar.getSource() == buttons[i]){
                                open_device(mixer_info[i]);
                                start_read();
                            }
                        }
                    }
                });
            }
            
            dialog.pack();
            dialog.setVisible(true);
            
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void open_device(Mixer.Info info){
        
            mixer = AudioSystem.getMixer(info);
            try{
                dataLine_info = new DataLine.Info(TargetDataLine.class, af);
                tdl = (TargetDataLine) mixer.getLine(dataLine_info);
                tdl.open(af);
                tdl.start();
            }catch(Exception e){
                e.printStackTrace();
            }
        
            
    }
    
    public void start_read(){
        _runner = true;
        timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run(){
                while (_runner) {
                    tdl.read(data, 0, data.length);
                    
                    int max = 0, min = 0;
                    int count = 0;
                    for (int i = 0; i < data.length; i++) {
                        
                        if ((i + 1) % (data.length/Grapher.Length) == 0) {
                            max = Integer.max(max, data[i]);
                            min = Integer.min(min, data[i]);
                            
                            amp[count] = ((max+min))+50;
                            max = 0;
                            min = 0;
                            count++;
                        }
                    }
                    
                }
            }
        }, 0, 1);
        
    }
    
    public void stop(){
        _runner = false;
        timer.cancel();
    }
    
    public AudioFormat getAudioFormat() {
 
        float sampleRate = 96000.0F;
        int sampleSizeInBits = 16;
        int channels = 2;
        boolean singed = true;
        boolean bigEndian = false;
 
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, singed,
                bigEndian);
 
    }
    
}