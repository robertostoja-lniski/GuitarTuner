package ProjectProz.MVC;

import java.awt.EventQueue;

import ProjectProz.Controller.MenuController;
import ProjectProz.Controller.UppMenuController;
import ProjectProz.Model.Model;
import ProjectProz.View.MenuView;
import ProjectProz.View.UppMenuView;

public class MVCTuner {
   public static void main(String[] args) {
	   
	   EventQueue.invokeLater(new Runnable() {
	       public void run() {
	    	   Model model  = new Model();
	    	   
	    	   model.initializeTab();
	    	   model.readTuningFromFile("D:\\Eclipse Projects\\MVC\\src\\main\\java\\ProjectProz\\MVC\\NewFile.txt");
	    
	    	  // model.saveTuningToNewFile("NewFile.txt");
	    	   model.setTuning("Guitar_Standard_E");
	    	   
	    	   UppMenuController controller1 = new UppMenuController(model);
	    	   
	    	   UppMenuView uppmenuview = new UppMenuView(model);
	    	   uppmenuview.setController(controller1);
	    	   
	    	   MenuView view = new MenuView(model, uppmenuview);	    	   
	    	   MenuController controller = new MenuController(model,view);	    	   
	    	   view.setController(controller);
	    	   controller1.setView(view);
	       }
	   });
   }
 
}