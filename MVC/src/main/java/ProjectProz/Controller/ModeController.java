package ProjectProz.Controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import ProjectProz.Model.Model;
import ProjectProz.View.AutoModeView;
import ProjectProz.View.ManualModeView;
import ProjectProz.View.MenuView;
import ProjectProz.View.UppMenuView;



/**
 * @author Robert
 * Controls every action performed in mode frames
 */
public class ModeController implements ActionListener {
	
	private Model model;
	private AutoModeView autoMode;
	private ManualModeView manualMode;
	// false for manual, true for auto
	private boolean modeType;
	public ModeController(Model model) {
		this.model = model;
	}
	ModeController(Model model, boolean modeType) {
		this.model = model;
		this.modeType = modeType;
	}
	public void setView(AutoModeView view) {
		autoMode = view;
	}
	public void setView(ManualModeView view) {
		manualMode = view;
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
			
		       if(modeType) autoMode.close();
		       if(!modeType) manualMode.close();
			   UppMenuController controller1 = new UppMenuController(model);
	    	   UppMenuView uppmenuview = new UppMenuView(model);
	    	   uppmenuview.setController(controller1);
	    	   
	    	   MenuView view = new MenuView(model, uppmenuview);	    	   
	    	   MenuController controller = new MenuController(model,view);	    	   
	    	   view.setController(controller);	
	    	   controller1.setView(view);
		}
		for(int i =0; i<model.getTabSize(); i++) {
			
			if(e.getActionCommand().equals(model.getNameOfNoteAt(i))) {
				if(modeType) {
					
					autoMode.setTuningMessage(model.getNameOfNoteAt(i));
					
					checkFrequency(i);
				    
				} else {
					String noteName = model.getNameOfNoteAt(i);
					manualMode.setSoundView(i, noteName);
					model.sound(i);
				}
				
			}		
		}
		
		if(e.getActionCommand().equals("stop")) {
				manualMode.setDefaultButtonsLayout();					 
		}		
	
  }
   	
	/**
     * sets tuning message
     */
    void setTuningMessage() {
    	autoMode.signSwap();
    }
    /**
     * Sets default auto view layout
     */
    void setAutoDefaultLayout() {   	    	
    	try {
    		autoMode.getPanel().setDefaultXY();
        	autoMode.update(autoMode.getGraphics());
        	autoMode.setDefaultButtonsLayout();
    	} catch(NullPointerException e) {
    		System.out.println("In model.setAutoDefaultLayout null pointer exception");
    	}
    }
    
   	/**
     * reads frequency and compares it to
     * expected frequency of note at i position
     * @param i - index
   	 * @throws InterruptedException 
     */
   	
    void checkFrequency( int i ) {
    	
    	try {
				(model.getRunnable()).setIndex(i);

				Thread thread = new Thread(model.getRunnable());
				thread.start();
				TimeUnit.MILLISECONDS.sleep(700);
				autoMode.getPanel().setFreqInfo(model.getUnscaledFreq());
				autoMode.getPanel().setXY(model.getScaledFreq());
				
				autoMode.getPanel().repaint();
				
		} catch (NullPointerException e) {
			System.out.println("In model.tune null pointer exception");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       	
    }  	
}              
