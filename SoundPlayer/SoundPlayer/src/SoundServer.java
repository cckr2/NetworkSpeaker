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

import javafx.scene.control.ListView;

public class SoundServer extends Thread{
   public static final int port = 6000;
   public static final int DEFAULT_BUFFER_SIZE = 100;
   
   public static ServerSocket server;
   public static final int SpeakerMax = 10;

	private TargetDataLine dataLine;
   PlayerController player;
   public SoundServer(PlayerController player){
	   this.player = player;
	   super.start();
   }
   public void run(){
      try {
         server = new ServerSocket(port); 
         System.out.println("This server is listening... (Port: " + port  + ")");
 
         while(true) {
        	 Socket socket = server.accept();  
        	 InetSocketAddress isaSpeaker = (InetSocketAddress) socket.getRemoteSocketAddress();
        	 System.out.println("A Speaker("+isaSpeaker.getAddress().getHostAddress()+") is connected. (Port: " +isaSpeaker.getPort() + ")");
        	 player.speaker_add(socket, isaSpeaker.getAddress().getHostAddress());
         }          
      
      } catch (UnknownHostException e) {
		  // TODO Auto-generated catch block
	  		e.printStackTrace();
      } catch (IOException e) {
	     // TODO Auto-generated catch block
	        e.printStackTrace();
	  }
   	}
//	public void all_restart(){
//		for(int i=0;i<10;i++){
//			if(speaker[i]!=null)
//				speaker[i].restart();
//		}
//	}
//	public void all_pause(){
//		for(int i=0;i<10;i++){
//			if(speaker[i]!=null)
//				speaker[i].pause();
//		}
//	}
	   
	   

}