package ProjectProz.MVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Robert
 * Upper menu controller
 * return to manual mode if whereToReturn is 0
 * to auto mode if whereToReturn is 1 and 
 * to menu if whereToReturn is 2
 */
public class UppMenuController implements ActionListener {
	
	Model model;
	int whereToReturn;
	UppMenuController(Model model, int whereToReturn) {
		this.model = model;
		this.whereToReturn = whereToReturn;
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Help")) {
			model.helpChosen();
		} 
		 if(e.getActionCommand().equals("Set defaults")) {
			model.readTuningFromFile("D:\\Eclipse Projects\\MVC\\src\\main\\java\\ProjectProz\\MVC\\toRead.txt");
			model.setTuning("Guitar_Standard_E");
			model.saveTuningToNewFile("D:\\Eclipse Projects\\MVC\\src\\main\\java\\ProjectProz\\MVC\\NewFile.txt");
	
			switch(whereToReturn) {
			case 0: returnToManual(); break;
			case 1: returnToAuto(); break;
			case 2: returnToMenu(); break;
			}
		} 
		if(e.getActionCommand().equals("Add new tuning")) {
			AddNewController controller = new AddNewController(model, whereToReturn);
			AddNewView stView = new AddNewView(controller, model, whereToReturn);
			
		}
		if(e.getActionCommand().charAt(0)=='#') {
			String tuningName;
			tuningName = e.getActionCommand().substring(1, e.getActionCommand().length());
			model.setTuning(tuningName);
			
			switch(whereToReturn) {
			case 0: returnToManual(); break;
			case 1: returnToAuto(); break;
			case 2: returnToMenu(); break;
			}
		}	
					
	  
	}
	/**
	 * closes manual mode and then opens it one more time
	 */
	private void returnToManual() {
		
		model.closeMode(false);
		model.closeMenu();
		ModeController controller = new ModeController(model);
		UppMenuController controller1 = new UppMenuController(model, whereToReturn);
		UppMenuView uppmenuview = new UppMenuView(controller1, model, whereToReturn);
		ManualModeView view = new ManualModeView(controller,model, uppmenuview);
	}
	/**
	 * closes auto mode and then opens it one more time
	 */
	private void returnToAuto() {
		
		model.closeMode(true);
		model.closeMenu();
		ModeController controller = new ModeController(model);
		UppMenuController controller1 = new UppMenuController(model, whereToReturn);
		UppMenuView uppmenuview = new UppMenuView(controller1, model, whereToReturn);
		AutoModeView view = new AutoModeView(controller,model, uppmenuview);
	}
	/**
	 * closes menu and then opens it one more time
	 */
	private void returnToMenu() {
		
		model.closeMenu();
		MenuController controller = new MenuController(model);
		UppMenuController controller1 = new UppMenuController(model, whereToReturn);
		UppMenuView uppmenuview = new UppMenuView(controller1, model, whereToReturn);
		MenuView view = new MenuView(controller,model, uppmenuview);
	}
}
