package ProjectProz.Controller;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Vector;

import ProjectProz.Model.Model;
import ProjectProz.View.AddNewView;
import ProjectProz.View.AutoModeView;
import ProjectProz.View.ManualModeView;
import ProjectProz.View.MenuView;
import ProjectProz.View.ModeBaseView;
import ProjectProz.View.UppMenuView;


/**
 * @author Robert
 * @param whereToReturn 0 for manual, 1 for auto and 2 for menu
 * Controller of adding new tuning.
 * It implements Action Listener interface
 */
public class AddNewController implements ActionListener{
	
   private Model model;
   private AddNewView addNewView;
   private MenuView menuView;
   private ModeBaseView modeView;
   
   int whereToReturn; // 0 manual 1 auto 2 menu 
   /**
    * basic contructor
    * @param model
    * @param whereToReturn
    */
   public AddNewController(Model model, int whereToReturn){
      this.model = model;
      this.whereToReturn = whereToReturn;
   }
   /**
    * sets modeView
    */
   public void setModeView(ModeBaseView modeView) {
	   this.modeView = modeView;
   }
   /**
    * Adds view reference
    */
   public void setAddNewView(AddNewView addNewView) {
	   this.addNewView = addNewView;
   }
/**
 * Add new tuning to tuning base
 * and automatically saves it to the new file
 * based on whereToReturnParam
 */
public void actionPerformed(ActionEvent e) {
	if(e.getActionCommand().equals("Back")) {
		addNewView.closeAddOption();
	}
	if(e.getActionCommand().equals("Apply")) {
		// counts number of properly entered lines
		int numOfLines = checkBeforeAdd();
		if(numOfLines!=0) {
			Vector<String> textToProcess = getFullText();
			//check(textToProcess);
			model.AddTuningToDataBase(textToProcess, numOfLines);
			model.saveTuningToNewFile("D:\\Eclipse Projects\\MVC\\src\\main\\java\\ProjectProz\\MVC\\NewFile.txt");
			addNewView.closeAddOption();
			
			UppMenuController controller1 = new UppMenuController(model);
			UppMenuView uppmenuview = new UppMenuView(model);
			
			if(whereToReturn == 0) {
				
				modeView.close();
				ModeController controller = new ModeController(model);				
				ManualModeView view = new ManualModeView(model, uppmenuview);
				uppmenuview.setController(controller1);
				view.setController(controller);
				controller1.setView(view, false);
				
			} else { 
				if(whereToReturn == 1) {
					
					modeView.close();
					ModeController controller = new ModeController(model);
					AutoModeView view = new AutoModeView(model, uppmenuview);	
					uppmenuview.setController(controller1);
					view.setController(controller);
					controller1.setView(view, true);
									} else {
					if(whereToReturn == 2) {
						closeMenu();
				    	uppmenuview.setController(controller1);
				    	   
				    	 MenuView view = new MenuView(model, uppmenuview);	    	   
				    	 MenuController controller = new MenuController(model,view);	    	   
				    	 view.setController(controller);
				    	 controller1.setView(view);
																	
					}
					
				}
		}
					
		}
	}
	
}

private Vector<String> getFullText() {

	Vector<String> textEntered = new Vector<String>();
	textEntered.add("#Tuning");
	String name = addNewView.getTextField(1).replaceAll("\\s", "_");
	textEntered.add(name);
	
        	 for(int i = 2; i <= 16 ; i++) {
 	        //	System.out.println(setupview.getTextField(i));
        		 if(!addNewView.getTextField(i).equals("")) {
 	        		name = addNewView.getTextField(i).replaceAll("\\s", "_");     		
 	        	} else {
 	        		name = addNewView.getTextField(i);   
 	        	}
        	 	textEntered.addElement(name);
        	 }
 	 return textEntered;       
 }

/**
 * Checks if tuning can be added
 * if data given by user is of a proper type
 * @return 0 if unsuccessful, 1 if succeed
 */

int checkBeforeAdd() {
    
	String name, fstr;
	int num = 0;
	try {   		
		if(addNewView.getTextField(1).equals("")) {   			
    		addNewView.returnErrorMsg();
    		return 0;
    	}    		
    	for(int i = 1; i<=8; i++) {
    	     
    		fstr = addNewView.getTextField(2*i);
    		name = addNewView.getTextField(2*i+1);
    		
			if(!fstr.equals(""))
    			Double.parseDouble(addNewView.getTextField(2*i));

    		if((!fstr.equals("") && name.equals("")) || (fstr.equals("") && !name.equals(""))) {
    			addNewView.returnErrorMsg();
    			return 0;
    		} else {
    			if(!fstr.equals("") && !name.equals(""))
    			num++;
    		}    		
    	}
    	for(int i = 1; i <=8; i++) {
    		name = addNewView.getTextField(2*i+1);
    	//	System.out.println(name);
    		for(int j=i+1; j<=8; j++) {
    			fstr = addNewView.getTextField(2*i);
    			if(!fstr.equals("") && !name.equals("")) {
    				if(addNewView.getTextField(2*j+1).equals(name)) {
        				System.out.println(i);
        				addNewView.returnTheSameNameError();
        				return 0;
        			} 
    			}  			       		
    		}
    	}
	} catch (NumberFormatException e ) {  		
		addNewView.returnErrorMsg();
		return 0;
	}
	return num;
	
}
/**
 * sets view to controller
 */
public void setView(AddNewView setupview) {
	this.addNewView = setupview;
}
/**
 * closes menu
 */
public void closeMenu() {
	try {
		WindowEvent winClosingEvent = new WindowEvent(menuView,WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
	} catch (Exception e) {	
		System.out.println("In model.closeMenu() null pointer exception");
	}
}
}