import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.Mixer;



public class SoundClient extends Thread{
	public static final String serverIP = "192.168.0.4";
	public static final int port = 6000;
	public static final int DEFAULT_BUFFER_SIZE = 2000;
	byte Buffer[] = new byte[DEFAULT_BUFFER_SIZE];
	
	private SourceDataLine dataLine=null;
	private Socket socket ;
	private InputStream is;
	
	public SoundClient(){
	    AudioFormat audioFormat = getAudioFormat();
            Mixer.Info[] mixerinfo = AudioSystem.getMixerInfo();
            
            for(int i = 0; i < mixerinfo.length; i++){
                System.out.println(i + " : " + mixerinfo[i].getName());
            }

	    
	    DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,audioFormat);
            Mixer mixer = AudioSystem.getMixer(mixerinfo[1]); 


	    try {
	    	dataLine = (SourceDataLine)mixer.getLine(dataLineInfo);
	    	dataLine.open(audioFormat);
                dataLine.start();
	    } catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	    try {
			socket = new Socket(serverIP, port);
			if(!socket.isConnected()){
		    	System.out.println("Socket Connect Error.");
	            System.exit(0);
		    }
		    
			InputStream is = socket.getInputStream();
  		    int readBytes;
  		    System.out.println("Socket is Connect.");
  		    try{
			    while (true) {
			    	readBytes = is.read(Buffer);
			    	dataLine.write(Buffer,0,readBytes);
			    }
		    } catch(SocketException e){
		    	finish();
		    }
  		    
		        
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	public void finish(){
		dataLine.drain();
		dataLine.close();
	    try {
			is.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void main(String[] args){
		SoundClient recorder = new SoundClient();
    }
	
	private static AudioFormat getAudioFormat(){
	    float sampleRate = 44100.0F;	    //8000,11025,16000,22050,44100
	    int sampleSizeInBits = 16;	    //8,16
	    int channels = 2;	    //1,2
	    boolean signed = true;	    //true,false
	    boolean bigEndian = false;	    //true,false
	    return new AudioFormat(sampleRate,sampleSizeInBits,channels,signed,bigEndian);
	}
}
