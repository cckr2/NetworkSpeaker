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
 
 
public class DrawGraph extends Canvas implements Runnable{
	final long serialVersionUID = 1L;
	final int height = 100;
	final int margin = 8;
	
    Thread thread;
    
    int[] baseDate = new int[SoundPlayer.length];
    int[] temp = new int[SoundPlayer.length];
    boolean[] standardData = new boolean[SoundPlayer.length];
    
    BufferStrategy buffer_strategy;
    Graphics2D graphics;
    GradientPaint gradient_paint;
    MixSelector mixSelector;
 
    public DrawGraph(MixSelector mixSelector){
    	this.mixSelector = mixSelector; 
        gradient_paint = new GradientPaint(10,0,Color.red,325,100,Color.blue,true);
        thread = new Thread(this);
        for (int i = 0; i < SoundPlayer.length; i++) {
            temp[i] += (20+i);
            baseDate[i] = 50;
            standardData[i] = false;
        }        
    }
    
    public void starter(){      
        thread.start();
    }
    
    @Override
    public void run(){
        new Timer().schedule(new TimerTask() {
            public void run() {
                for (int i = 0; i < SoundPlayer.length; i++) {
                    if (!standardData[i]) {
                        if (baseDate[i] > MixSelector.amp[i]) {
                        	baseDate[i]--;
                        } else {
                            baseDate[i]++;
                        }
                    }
                    if (baseDate[i] == MixSelector.amp[i])
                        standardData[i] = true;
                }
            }
        }, 0, 3);
        
        new Timer().schedule(new TimerTask() {
            public void run() {
                for (int i = 0; i < SoundPlayer.length; i++) {
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
        
        while(true){
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
        graphics.clearRect(0,0, SoundPlayer.width-4, height);
        graphics.setColor(new Color(0f, 0f, 0f, 0f));
        graphics.setPaint(gradient_paint);
        graphics.drawPolyline(temp, baseDate, SoundPlayer.length);
       
        this.setBounds(margin,margin,  SoundPlayer.width-38, height);
        if(PlayerController.runner) this.setBackground(new Color(234,242,250));
        else this.setBackground(new Color(255,204,204));
        buffer_strategy.show();
    }
 
}