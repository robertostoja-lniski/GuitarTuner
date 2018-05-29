package ProjectProz.View;

import javax.swing.JLabel;

import ProjectProz.Controller.ModeController;
import ProjectProz.Model.Model;

/**
 * @author Robert
 * Auto mode view (ModeBaseView inheritance)
 */
public class AutoModeView extends ModeBaseView {
	
	
	private static final long serialVersionUID = 1L;
	JLabel foto;
	AutoTuningPanelView tuningPanel;
	
	/**
	 * Constructor
	 * @param controller - auto tuning controller
	 * @param uppmenuview - upper menu (the same for every frame)
	 */
    public AutoModeView( Model model, UppMenuView uppmenuview) {
    		super(model, uppmenuview);	
    		initPanel();
    	//	setFoto();
    		setModeName("Auto Mode");    		
    }
    public void setController(ModeController controller) {
    		super.setController(controller);
    		controller.setModeType(true);
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
        tuningPanel.setDefaultXY();
    	getContentPane().add(tuningPanel);
    }
    /**
     * sets tuning message
     * @param name - displays name of a string that is being tuned
     */
    public void setTuningMessage(String name) {
    	//System.out.println("hejo");
    	String msg = "You are tuning " + name;
    	//System.out.println(msg);
    	setMessage(msg);
    }
    
}
