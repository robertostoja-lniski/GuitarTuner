package ProjectProz.MVC;

import java.awt.EventQueue;
import java.io.FileNotFoundException;

public class MVCTuner {
   public static void main(String[] args) {
	   
	   EventQueue.invokeLater(new Runnable() {
	       public void run() {
	    	   Model model  = new Model();
	    	   
	    	   model.initializeTab();
	    	   model.readTuningFromFile("D:\\Eclipse Projects\\MVC\\src\\main\\java\\ProjectProz\\MVC\\NewFile.txt");
	    
	    	  // model.saveTuningToNewFile("NewFile.txt");
	    	   model.setTuning("Guitar_Standard_E");
	    	   
	    	   UppMenuController controller1 = new UppMenuController(model, 2);
	    	   UppMenuView uppmenuview = new UppMenuView(controller1, model, 2);
	    	   MenuController controller = new MenuController(model);
	    	   MenuView view = new MenuView(controller,model, uppmenuview);
	    	   view.removeWarning();
	    	   
	       }
	   });
   }
   
}