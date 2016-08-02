import java.awt.Canvas;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.Timer;
import java.util.TimerTask;
 
 
public class Draw_graph extends Canvas implements Runnable{
    
    private static final long serialVersionUID = 1L;
 
    boolean _runner = false;
    Thread thread;
    
    int[] data2 = new int[Grapher.MAX_LENGTH];
    int[] x = new int[Grapher.MAX_LENGTH];
    boolean[] data3 = new boolean[Grapher.MAX_LENGTH];
    
    BufferStrategy bs;
    Graphics2D g2;
    
    GradientPaint gp;
    
    public Draw_graph(){
        
        gp = new GradientPaint(10,0,Color.red,325,100,Color.blue,true);
        thread = new Thread(this);
        
        for (int i = 0; i < Grapher.MAX_LENGTH; i++) {
            x[i] += (20+i);
            data2[i] = 50;
            data3[i] = false;
        }
        
    }
    
    public void starter(){
        
        _runner = true;
        thread.start();
        
    }
    
    @Override
    public void run(){
 
        new Timer().schedule(new TimerTask() {
            public void run() {
                for (int i = 0; i < Grapher.Length; i++) {
 
                    if (!data3[i]) {
                        if (data2[i] > Get_Sound_Info.amp[i]) {
                            data2[i]--;
                        } else {
                            data2[i]++;
                        }
                    }
                    if (data2[i] == Get_Sound_Info.amp[i])
                        data3[i] = true;
 
                }
                
 
            }
        }, 0, 3);
        new Timer().schedule(new TimerTask() {
            public void run() {
                for (int i = 0; i < Grapher.Length; i++) {
 
                    if (data3[i]) {
                        if (data2[i] > 50) {
                            data2[i]--;
                        } else {
                            data2[i]++;
                        }
                    }
                    if (data2[i] == 50)
                        data3[i] = false;
                    
                }
 
            }
        }, 0, 3);
 
        while (_runner) {
            render();
        }
    }
    
    public void render(){
        if (this.getBufferStrategy() == null) {
 
            this.createBufferStrategy(3);
            return;
 
        }
 
        bs = this.getBufferStrategy();
        
        g2 = (Graphics2D)bs.getDrawGraphics();
        
        
        g2.setColor(new Color(0f, 0f, 0f, 0f));
        g2.clearRect(0, 0, Grapher.Width, Grapher.Height);    
        g2.setPaint(gp);
        g2.drawPolyline(x, data2, Grapher.Length);
        
        bs.show();
    }
 
}