package ProjectProz.Controller;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import ProjectProz.Model.Model;
import ProjectProz.View.AddNewView;
import ProjectProz.View.AutoModeView;
import ProjectProz.View.ManualModeView;
import ProjectProz.View.MenuView;
import ProjectProz.View.ModeBaseView;
import ProjectProz.View.UppMenuView;

/**
 * @author Robert
 * Upper menu controller
 * return to manual mode if whereToReturn is 0
 * to auto mode if whereToReturn is 1 and 
 * to menu if whereToReturn is 2
 */
public class UppMenuController implements ActionListener {
	
	Model model;
	MenuView menuView;
	ModeBaseView modeView;

	int whereToReturn;
	public UppMenuController(Model model) {
		this.model = model;
	}
	public void setView(MenuView menuView) {
		this.menuView = menuView;
		whereToReturn = 2;
	}
	// 0 manual, 1 auto
	public void setView(ModeBaseView modeView, boolean modeType) {
		this.modeView = modeView;
		if(!modeType) {
			whereToReturn = 0;
		} else {
			whereToReturn = 1;
		}
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
			AddNewView stView = new AddNewView(model, whereToReturn);
			stView.setController(controller);
			controller.setAddNewView(stView);
			controller.setModeView(modeView);	
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
		
		ModeController controller = new ModeController(model, false);
		UppMenuController controller1 = new UppMenuController(model);
		UppMenuView uppmenuview = new UppMenuView(model);
		ManualModeView view = new ManualModeView(model, uppmenuview);
		
		uppmenuview.setController(controller1);
		view.setController(controller);
		controller1.setView(view, false);
		controller.setView(view);
		modeView.close();
		
	}
	/**
	 * closes auto mode and then opens it one more time
	 */
	private void returnToAuto() {
		
		UppMenuController controller1 = new UppMenuController(model);
		UppMenuView uppmenuview = new UppMenuView(model);
		ModeController controller = new ModeController(model, true);
		AutoModeView view = new AutoModeView(model, uppmenuview);
		
		uppmenuview.setController(controller1);
		view.setController(controller);
		controller1.setView(view, true);
		controller.setView(view);
		modeView.close();
	}
	/**
	 * closes menu and then opens it one more time
	 */
	private void returnToMenu() {
	
	   UppMenuController controller1 = new UppMenuController(model);
  	   UppMenuView uppmenuview = new UppMenuView(model);
  	   uppmenuview.setController(controller1);
  	   
  	   MenuView view = new MenuView(model, uppmenuview);	    	   
  	   MenuController controller = new MenuController(model,view);	    	   
  	   view.setController(controller);	
  	   controller1.setView(view);
  	   menuView.close();
	}
		
}
