package ProjectProz.MVC;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * @author Robert
 * View of tuning panel
 */
public class AutoTuningPanelView extends JPanel{

	private static final long serialVersionUID = 1L;
	
	int x, y;
	Model model;
	JLabel foto, freqInfo;
	
	AutoTuningPanelView(Model model) {
		    this.model = model;
		    model.setTuningPanel(this);
		    
    		setXY();    		
    		initPanel();
    		initLabel();
    		setFoto("tunerAuto.jpg"); 		
    }
	/**
	 * Initializes frequency info at the bottom
	 */
	private void initLabel() {
		
		freqInfo = new JLabel("Your frequency: ");
		freqInfo.setBounds(80,450,100,300);
		freqInfo.setHorizontalAlignment(SwingConstants.CENTER);
        add(freqInfo);
	}
	public void setFreqInfo(double f) {
		String freq = String.valueOf(f);
		freq = String.format("%.2f", f);
		freqInfo.setText(freq+ " Hz");
	}
	/**
	 *  sets default values of x and y for drawing a line
	 */
    public void setXY() {   	
    	x = 150;
    	y = 175;   	
    }
    /**
     * 
     * @param photoPath - path to photo, which is backgroud
     */
    public void setFoto(String photoPath) {
    	foto = new JLabel();
    	try {
    		
    		foto.setIcon(new ImageIcon(getClass().getResource(photoPath)));
            foto.setBounds(80,300,300,150);
            add(foto);
    	} catch(NullPointerException e) {
    		System.out.println("In auto tuning panel there is no such photo");
    	}
        
    }
    /*
     * sets y based on circle equation and x param 
     */
    public void setXY(int x) {
    	
    	if(x < 0 ) {
    		x = 0;
    		System.out.println("x changed to 0 - cannot be negative");
    	}
    	this.x = x;
        y = model.getPointY(x);
    }
    public void paint(Graphics g) {  
        super.paint(g);  
        //System.out.println(x+ " " + y);
        Graphics2D g2 = (Graphics2D) g;       
        Line2D lin = new Line2D.Float(150, 175, x, y);
        g2.draw(lin);
    }
    /**
     * Initializes panel;
     */
    public void initPanel() {
    	setBounds(80,300,300,250);    	
    	//setBackground(Color.red);
    }
}
