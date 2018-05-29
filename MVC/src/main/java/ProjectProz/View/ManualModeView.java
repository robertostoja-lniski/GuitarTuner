package ProjectProz.View;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import ProjectProz.Controller.ModeController;
import ProjectProz.Model.Model;

/**
 * @author Robert
 * Manual mode view (ModeBaseView inheritance)
 */
public class ManualModeView extends ModeBaseView {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param controller - mode controller
     * @param uppmenuview - upper menu (the same for every frame)
	 */
    public ManualModeView(Model model, UppMenuView uppmenuview) {
    	    super(model, uppmenuview); 	    
    	    initLabels();
    	    setModeName("Manual Mode");
    	    
    }
    public void setSoundView(int i, String noteName) {
    	signSwap();
    	setSigns(noteName);
    	setStop(true);
    }
    public void setController(	ModeController controller) {
    	super.setController(controller);
    	controller.setModeType(false);
    }
    /**
     * Initialises labels
     */
    private void initLabels() {
   
        jLabelChosenString = new JLabel("");        
        jLabelChosenString.setFont(new Font("Dialog", 0, 80));
        jLabelChosenString.setBounds(130, 330, 200, 80);
        jLabelChosenString.setVerticalAlignment(SwingConstants.CENTER);
        jLabelChosenString.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(jLabelChosenString);
		
	}
   
}
    
    

