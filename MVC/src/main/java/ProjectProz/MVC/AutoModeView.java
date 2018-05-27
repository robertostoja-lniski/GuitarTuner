package ProjectProz.MVC;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Robert
 * Auto mode view (ModeBaseView inheritance)
 */
public class AutoModeView extends ModeBaseView {
	
	ModeController controller;
	private static final long serialVersionUID = 1L;
	JLabel foto;
	AutoTuningPanelView tuningPanel;
	
	/**
	 * Constructor
	 * @param controller - auto tuning controller
	 * @param uppmenuview - upper menu (the same for every frame)
	 */
    public AutoModeView(ModeController controller, Model model, UppMenuView uppmenuview) {
    		super(controller, model, uppmenuview);
    		model.setView(this);
    		controller.setModeType(true);   		
    		initPanel();
    	//	setFoto();
    		setModeName("Auto Mode");    		
    }
    /**
     * @return returns reference to it's panel 
     */
    public AutoTuningPanelView getPanel() {
    	return tuningPanel;
    }
    /**
     * initialises panel on which tuning accuracy is shown
     */
    public void initPanel() {
        tuningPanel = new AutoTuningPanelView(model);
        tuningPanel.setXY();
    	getContentPane().add(tuningPanel);
    }
    
}
