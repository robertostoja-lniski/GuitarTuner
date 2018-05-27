package ProjectProz.MVC;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * @author Robert
 * Controls main menu frame 
 * implements ActionListener
 */
public class MenuController implements ActionListener{
   private Model model;

   /**
    * Constructor
    */
   public MenuController(Model model){
      this.model = model;
   }
/**
 * Based on action event command user may 
 * go to Auto or Manual mode or exit program
 */
public void actionPerformed(ActionEvent e) {
	
	
	if(e.getActionCommand().equals("Auto")) {
		
		UppMenuController controller1 = new UppMenuController(model, 1);
		UppMenuView uppmenuview = new UppMenuView(controller1, model,1);
		ModeController controller = new ModeController(model);
		AutoModeView view = new AutoModeView(controller,model, uppmenuview);
		view.removeWarning();
		model.closeMenu();
	} 
	if(e.getActionCommand().equals("Manual")) {
		ModeController controller = new ModeController(model);
		UppMenuController controller1 = new UppMenuController(model, 0);
		UppMenuView uppmenuview = new UppMenuView(controller1, model,0);
		ManualModeView view = new ManualModeView(controller,model, uppmenuview);
		view.removeWarning();
		model.closeMenu();
	} 
	if(e.getActionCommand().equals("click to quit")) {
		 System.exit(0);
	} 
	
	}
		
	
}