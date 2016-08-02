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
   public static final int DEFAULT_BUFFER_SIZE = 1000;
   byte Buffer[] = new byte[DEFAULT_BUFFER_SIZE];
   
   private TargetDataLine dataLine;
   private Socket socket ;
   private OutputStream os;
   public SoundServer(TargetDataLine line){
      dataLine = line;
   }

   public void startCapture(){
       dataLine.start();
       super.start();
   }

   public void stopCapture(){
       dataLine.stop();
       dataLine.close();
       try {
         os.close();
         socket.close();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   public void run(){
      try {
         ServerSocket server = new ServerSocket(port); 
          System.out.println("This server is listening... (Port: " + port  + ")");
          Socket socket = server.accept();  
          InetSocketAddress isaClient = (InetSocketAddress) socket.getRemoteSocketAddress();
          
          System.out.println("A client("+isaClient.getAddress().getHostAddress()+
                ") is connected. (Port: " +isaClient.getPort() + ")");
          
            os = socket.getOutputStream();
            int readBytes;
 
          while (true) {
             readBytes = dataLine.read(Buffer, 0, DEFAULT_BUFFER_SIZE);
             os.write(Buffer, 0, readBytes);
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
      TargetDataLine targetDataLine = null;
      
       
       Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
       System.out.println("Available mixers:");
       
       for(int i = 0; i < mixerInfo.length; i++){
          System.out.println(i+" : "+ mixerInfo[i].getName());
       }
       
       AudioFormat audioFormat = getAudioFormat();
       
       DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class,audioFormat);
       Mixer mixer = AudioSystem.getMixer(mixerInfo[4]);//here

       
       try {
          targetDataLine = (TargetDataLine)mixer.getLine(dataLineInfo);
          targetDataLine.open(audioFormat);
           targetDataLine.start();
      } catch (LineUnavailableException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }
        
       
        System.out.println("AudioFormat settings: " + audioFormat.toString());

        //Data read and send;
        //SoundServer is Tread;
        SoundServer recorder = new SoundServer(targetDataLine);
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

   private static AudioFormat getAudioFormat(){
       float sampleRate = 44100.0F;       //8000,11025,16000,22050,44100
       int sampleSizeInBits = 16;       //8,16
       int channels = 2;       //1,2
       boolean signed = true;       //true,false
       boolean bigEndian = false;       //true,false
       return new AudioFormat(sampleRate,sampleSizeInBits,channels,signed,bigEndian);
   }
}