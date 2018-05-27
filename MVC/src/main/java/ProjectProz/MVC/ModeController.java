package ProjectProz.MVC;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



/**
 * @author Robert
 * Controls every action performed in mode frames
 */
public class ModeController implements ActionListener {
	
	private Model model;
	// false for manual, true for auto
	private boolean modeType;
	ModeController(Model model) {
		this.model = model;
	}
	/*
	 * Sets mode type ( false for manual and true for auto)
	 */
	public void setModeType(boolean modeType) {
		this.modeType = modeType;
	}
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
   	public void actionPerformed(ActionEvent e) {
   		
		if(e.getActionCommand().equals("back")) {
			
			model.closeMode(modeType);
			MenuController controller = new MenuController(model);
			UppMenuController controller1 = new UppMenuController(model, 0);
			UppMenuView uppmenuview = new UppMenuView(controller1, model,0);
			MenuView view = new MenuView(controller,model, uppmenuview);
			view.removeWarning();
			
		}
		for(int i =0; i<model.getTabSize(); i++) {
			
			if(e.getActionCommand().equals(model.getNameOfNoteAt(i))) {
				if(modeType) {
					
					model.setTuningMessage(model.getNameOfNoteAt(i));
					//model.repaintAuto();
					model.tune(i);
				} else {
					model.sound(i);
				}
				
			}		
		}
		
		if(e.getActionCommand().equals("stop")) {
			if(modeType) {
				model.setTuningOn(false);
				model.setAutoDefaultLayout();
				
			} else {
				//model.setManualDefaultLayout();
					 
		}	
	}	
  }    
}              
