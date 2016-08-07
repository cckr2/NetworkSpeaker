import java.awt.Canvas;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
 
 
public class Draw_graph extends Canvas implements Runnable{
	public static int Width = 550;
    public static int Height = 100;
    public static int Length = 512;
    public static int MAX_LENGTH = 512;
    
    private static final long serialVersionUID = 1L;
 
    boolean rend = false;
    Thread thread;
    
    int[] baseDate = new int[MAX_LENGTH];
    int[] temp = new int[MAX_LENGTH];
    boolean[] standardData = new boolean[MAX_LENGTH];
    
    BufferStrategy buffer_strategy;
    Graphics2D graphics;
    GradientPaint gradient_paint;
    
    public Draw_graph(){      
        gradient_paint = new GradientPaint(10,0,Color.red,325,100,Color.blue,true);
        thread = new Thread(this);
        for (int i = 0; i < MAX_LENGTH; i++) {
            temp[i] += (20+i);
            baseDate[i] = 50;
            standardData[i] = false;
        }        
    }
    
    public void starter(){      
        rend = true;
        thread.start();
    }
    
    @Override
    public void run(){
        new Timer().schedule(new TimerTask() {
            public void run() {
                for (int i = 0; i < Length; i++) {
                    if (standardData[i]) {
                        if (baseDate[i] > 50) {
                            baseDate[i]--;
                        } else {
                            baseDate[i]++;
                        }
                    }
                    if (baseDate[i] == 50)
                        standardData[i] = false;                  
                }
            }
        }, 0, 3);
        
        while (rend) {
            render();
        }
    }
    
    public void render(){
        if (this.getBufferStrategy() == null) {
 
            this.createBufferStrategy(3);
            return;
 
        }
 
        buffer_strategy = this.getBufferStrategy();
        
        graphics = (Graphics2D)buffer_strategy.getDrawGraphics();
        graphics.setColor(new Color(0f, 0f, 0f, 0f));
        graphics.clearRect(0, 0, Width, Height);    
        graphics.setPaint(gradient_paint);
        graphics.drawPolyline(temp, baseDate, Length);
        this.setBounds(8,8, Width, Height);
        //this.setBackground(new Color(225,241,251));
        this.setBackground(new Color(234,242,250));
       
        buffer_strategy.show();
    }
 
}