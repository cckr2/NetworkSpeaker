import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

public class SoundServer extends Thread{
   public static final int port = 6000;
   public static final int DEFAULT_BUFFER_SIZE = 100;
   
   public static ServerSocket server;
   public static final int clientMax = 10;

   
   private Client client[] = new Client[clientMax];
   private TargetDataLine dataLine;
  
   class Client extends Thread {
	    protected Socket socket;
	    protected int num;
	    protected OutputStream os;
	    
	    public Client(Socket socket,int num) {
	        this.socket = socket;
	        this.num = num;
	    }
	    
	    public int getnum(){
	    	return num;
	    }
	    
	    public void run() {
		    byte Buffer[] = new byte[DEFAULT_BUFFER_SIZE];
            
		    try {
				os = socket.getOutputStream();
	            int readBytes;
	            while (true) {
	               readBytes = dataLine.read(Buffer, 0, DEFAULT_BUFFER_SIZE);
	               os.write(Buffer, 0, readBytes);
	            }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    public void finish(){
	    	try {
				os.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	} 
   
   public SoundServer(){}
   public void setDataLine(){
       Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
       System.out.println("Available mixers:");
       
       for(int i = 0; i < mixerInfo.length; i++){
          System.out.println(i+" : "+ mixerInfo[i].getName());
       }
       
       AudioFormat audioFormat = getAudioFormat();
       
       DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class,audioFormat);
       Mixer mixer = AudioSystem.getMixer(mixerInfo[4]);//here

       try {
    	   dataLine = (TargetDataLine)mixer.getLine(dataLineInfo);
    	   dataLine.open(audioFormat);
    	   dataLine.start();
      } catch (LineUnavailableException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }
      System.out.println("AudioFormat settings: " + audioFormat.toString()); 
   }
   
   public void startCapture(){
	   setDataLine();
       dataLine.start();
       super.start();
   }

   public void stopCapture(){
       dataLine.stop();
       dataLine.close();
       
       try {
         server.close();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   
   public void run(){
      try {
         server = new ServerSocket(port); 
         System.out.println("This server is listening... (Port: " + port  + ")");
         
         while(true) {
	         Socket socket = server.accept();  
	         InetSocketAddress isaClient = (InetSocketAddress) socket.getRemoteSocketAddress();
	          
			 System.out.println("A client("+isaClient.getAddress().getHostAddress()+") is connected. (Port: " +isaClient.getPort() + ")");
			 int i=0;
			 while(true){
				 if(client[i]==null){
					 client[i] = new Client(socket,i);
					 client[i].start();
					 break;
				 }
				 i++;
				 if(i==clientMax){
					 System.out.println("²ËÂü");
					 break;
				 }
			 }
	     }          
          
      } catch (UnknownHostException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   
   private static AudioFormat getAudioFormat(){
       float sampleRate = 44100.0F;       //8000,11025,16000,22050,44100
       int sampleSizeInBits = 16;       //8,16
       int channels = 2;       //1,2
       boolean signed = true;       //true,false
       boolean bigEndian = false;       //true,false
       return new AudioFormat(sampleRate,sampleSizeInBits,channels,signed,bigEndian);
   }
   
   public static void main(String[] args){
	   SoundServer recorder = new SoundServer();
       recorder.startCapture();
       
       System.out.println("Starting the recording now...");
       System.out.println("Please press to stop the recording.");
       
       try{
           System.in.read();
       }
       catch (IOException e){
           e.printStackTrace();
       }
  
       recorder.stopCapture();
       System.out.println("Capture stopped.");
	   
   }
}