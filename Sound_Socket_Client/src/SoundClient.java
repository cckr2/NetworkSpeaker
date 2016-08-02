import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class SoundClient extends Thread{
	public static final String serverIP = "192.168.0.3";
	public static final int port = 6000;
	public static final int DEFAULT_BUFFER_SIZE = 1000;
	byte Buffer[] = new byte[DEFAULT_BUFFER_SIZE];
	
	private SourceDataLine dataLine;
	private Socket socket ;
	private InputStream is;
	
	public SoundClient(SourceDataLine line){
		dataLine = line;
	}

	public void startCapture(){
		dataLine.start();
	    super.start();
	}

	public void stopCapture(){
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

	public void run(){
		try {
			socket = new Socket(serverIP, port);
			if(!socket.isConnected()){
		    	System.out.println("Socket Connect Error.");
	            System.exit(0);
		    }
		    
			InputStream is = socket.getInputStream();
  		    int readBytes;
  		    System.out.println("Socket is Connect.");
		    while (true) {
		    	readBytes = is.read(Buffer);
		    	dataLine.write(Buffer,0,readBytes);
		    }
		        
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		AudioInputStream audioInputStream;
		SourceDataLine sourceDataLine = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    
	    byte audioData[] = byteArrayOutputStream.toByteArray();
	    InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
	    AudioFormat audioFormat = getAudioFormat();
	    audioInputStream = new AudioInputStream(byteArrayInputStream,audioFormat,audioData.length/audioFormat.getFrameSize());
	    DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,audioFormat);
	    try {
			sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLineInfo);
			sourceDataLine.open(audioFormat);
	    } catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    

        //Data read and send;
        //SoundClient is Tread;
        SoundClient recorder = new SoundClient(sourceDataLine);
        recorder.startCapture();
        
        System.out.println("Starting now...");
        System.out.println("Please press to stop the recording.");
        
        try{
            System.in.read();
        }
        catch (IOException e){
            e.printStackTrace();
        }
	
        recorder.stopCapture();
        System.out.println("stopped.");
    }
	private static AudioFormat getAudioFormat(){
	    float sampleRate = 8000.0F;	    //8000,11025,16000,22050,44100
	    int sampleSizeInBits = 16;	    //8,16
	    int channels = 1;	    //1,2
	    boolean signed = true;	    //true,false
	    boolean bigEndian = false;	    //true,false
	    return new AudioFormat(sampleRate,sampleSizeInBits,channels,signed,bigEndian);
	}
}