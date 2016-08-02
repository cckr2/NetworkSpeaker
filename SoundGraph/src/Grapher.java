import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
 
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
 
 
public class Grapher extends JFrame{
 
    private static final long serialVersionUID = 1L;
 
    public static int Width = 552;
    public static int Height = 150;
    public static int Length = 512;
    public static int MAX_LENGTH = 512;
    
    Get_Sound_Info gsi;
    Draw_graph graph;
    
    JSlider slider;
    
    public Grapher(){
        
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((d.width-Width)/2, (d.height-Height)/2, Width, Height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        gsi = new Get_Sound_Info();
        graph = new Draw_graph();
        
        this.setLayout(new BorderLayout());
        add(BorderLayout.CENTER,graph);
        
        slider = new JSlider();
        slider.setMinimum(512);
        slider.setMaximum(MAX_LENGTH);
        
        slider.setValue(512);
        
        slider.addChangeListener(new ChangeListener() {
            
            @Override
            public void stateChanged(ChangeEvent e) {
                // TODO Auto-generated method stub
                Grapher.this.setBounds( (MAX_LENGTH-(slider.getValue()+20))/2, Grapher.this.getY(), slider.getValue()+20, Grapher.this.getHeight());
                
                Length = slider.getValue()-20;
                Grapher.Width = slider.getValue()+20;
            }
        });
        
        add(BorderLayout.SOUTH,slider);
        
        this.setVisible(true);
        graph.starter();
        
        
    }
    
    public static void main(String[] args){
        new Grapher();
    }

}
