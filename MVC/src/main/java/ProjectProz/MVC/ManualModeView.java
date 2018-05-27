package ProjectProz.MVC;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * @author Robert
 * Manual mode view (ModeBaseView inheritance)
 */
public class ManualModeView extends ModeBaseView {
	
	ModeController controller;
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param controller - mode controller
     * @param uppmenuview - upper menu (the same for every frame)
	 */
    public ManualModeView(ModeController controller, Model model, UppMenuView uppmenuview) {
    	    super(controller, model, uppmenuview);
    	    model.setView(this);
    	    controller.setModeType(false);
    	    initLabels();
    	    setModeName("Manual Mode");
    	    
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
    
    

