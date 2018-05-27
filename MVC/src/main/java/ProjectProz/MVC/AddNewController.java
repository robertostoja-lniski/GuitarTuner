package ProjectProz.MVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * @author Robert
 * @param whereToReturn 0 for manual, 1 for auto and 2 for menu
 * Controller of adding new tuning.
 * It implements Action Listener interface
 */
public class AddNewController implements ActionListener{
   private Model model;
   int whereToReturn; // 0 manual 1 auto 2 menu 
   public AddNewController(Model model, int whereToReturn){
      this.model = model;
      this.whereToReturn = whereToReturn;
   }
/**
 * Add new tuning to tuning base
 * and automatically saves it to the new file
 * based on whereToReturnParam
 */
public void actionPerformed(ActionEvent e) {
	if(e.getActionCommand().equals("Back")) {
		model.closeAddOption();
	}
	if(e.getActionCommand().equals("Apply")) {
		if(model.checkBeforeAdd()!=0) {
			model.AddTuning();
			model.saveTuningToNewFile("D:\\Eclipse Projects\\MVC\\src\\main\\java\\ProjectProz\\MVC\\NewFile.txt");
			model.closeAddOption();
			
			if(whereToReturn == 0) {
				
				model.closeMode(false);
				ModeController controller = new ModeController(model);
				UppMenuController controller1 = new UppMenuController(model, whereToReturn);
				UppMenuView uppmenuview = new UppMenuView(controller1, model, whereToReturn);
				ManualModeView view = new ManualModeView(controller,model, uppmenuview);
				
			} else { 
				if(whereToReturn == 1) {
					
					model.closeMode(true);
					ModeController controller = new ModeController(model);
					UppMenuController controller1 = new UppMenuController(model, whereToReturn);
					UppMenuView uppmenuview = new UppMenuView(controller1, model, whereToReturn);
					AutoModeView view = new AutoModeView(controller,model, uppmenuview);	
					
				} else {
					if(whereToReturn == 2) {
						model.closeMenu();
						MenuController controller = new MenuController(model);
						UppMenuController controller1 = new UppMenuController(model, whereToReturn);
						UppMenuView uppmenuview = new UppMenuView(controller1, model, whereToReturn);
						MenuView view = new MenuView(controller,model, uppmenuview);
																	
					}
					
				}
		}
					
		}
	}
	
}	
}