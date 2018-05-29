package ProjectProz.Controller;


import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import ProjectProz.Model.Model;
import ProjectProz.View.AutoModeView;
import ProjectProz.View.ManualModeView;
import ProjectProz.View.MenuView;
import ProjectProz.View.UppMenuView;


/**
 * @author Robert
 * Controls main menu frame 
 * implements ActionListener
 */
public class MenuController implements ActionListener{
   private Model model;
   private MenuView menuView;
   /**
    * Constructor
    */
   public MenuController(Model model, MenuView menuView){
      this.model = model;
      this.menuView = menuView;
   }
/**
 * Based on action event command user may 
 * go to Auto or Manual mode or exit program
 */
public void actionPerformed(ActionEvent e) {

	if(e.getActionCommand().equals("Auto")) {
		
		UppMenuController controller1 = new UppMenuController(model);
		UppMenuView uppmenuview = new UppMenuView(model);
		ModeController controller = new ModeController(model, true);
		AutoModeView view = new AutoModeView(model, uppmenuview);
		
		uppmenuview.setController(controller1);
		view.setController(controller);
		controller1.setView(view, true);
		controller.setView(view);
		menuView.close();
	} 
	if(e.getActionCommand().equals("Manual")) {
		
		ModeController controller = new ModeController(model, false);
		UppMenuController controller1 = new UppMenuController(model);
		UppMenuView uppmenuview = new UppMenuView(model);
		ManualModeView view = new ManualModeView(model, uppmenuview);
		uppmenuview.setController(controller1);
		view.setController(controller);
		controller1.setView(view, false);
		controller.setView(view);
		menuView.close();
	} 
	if(e.getActionCommand().equals("click to quit")) {
		 System.exit(0);
	} 
	
	}
		
}