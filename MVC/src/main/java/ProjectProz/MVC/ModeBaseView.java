package ProjectProz.MVC;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;


/**
 * @author Robert
 * 
 */
public class ModeBaseView extends JFrame {
	
	ModeController controller;
	UppMenuView uppmenuview;
	protected Model model;
	private static final long serialVersionUID = 1L;
	private JButton jButtonQuit,jButtonStop,  jButtonTest;
	private JLabel jLabelName;
	protected JLabel jLabelChosenString;
	private JLabel jLabelMessage;
	
	/**
	 * Constructor
	 * @param controller - controller for both auto and mode view
	 * @param uppmenuview - the same for every frame
	 */
    public ModeBaseView(ModeController controller, Model model, UppMenuView uppmenuview) {
    	this.controller = controller;
    	this.model = model;
    	this.uppmenuview = uppmenuview;

    	initButtons();
    	initLabels();
    	initUppMenu();                       
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        pack();
        
        setVisible(true);
        setSize(460,580);
        setResizable(false);    
    }
    /**
     * Initialises Upper Menu
     */
    private void initUppMenu() {
    	setJMenuBar(uppmenuview);	
	}
    /**
     * Initialises Labels
     */
	private void initLabels() {
    	 jLabelName = new JLabel("");
         jLabelMessage = new JLabel("Choose the string to tune");
         jLabelChosenString = new JLabel("");
         
         jLabelName.setFont(new Font("Dialog", 0, 30));
         jLabelName.setBounds(110,60,250,150);
         jLabelName.setHorizontalAlignment(SwingConstants.CENTER);
         getContentPane().add(jLabelName);
         
         jLabelMessage.setFont(new Font("Dialog", 0, 18));
         jLabelMessage.setBounds(0,230,460,80);
         jLabelMessage.setHorizontalAlignment(SwingConstants.CENTER);
         
         getContentPane().add(jLabelMessage);
         		
	}
    /*
     * Initialises Buttons
     * At first number of buttons is read from 
     * data stored in the model. Then buttons with 
     * distinctive names and action commands are
     * added to view and to controller.
     */
	private void initButtons() {
		jButtonQuit = new JButton("back");
        jButtonStop = new JButton("stop");
     
        jButtonStop.addActionListener(controller);
        jButtonStop.setBounds(190,480,80,40);     
        getContentPane().add(jButtonStop);
        jButtonStop.setVisible(false); 
        
        jButtonQuit.setBounds(360,10,80,40);
        jButtonQuit.addActionListener(controller);
        getContentPane().add(jButtonQuit);
        
        try {

        	int numberOfButtons = (int)model.getFreqOfNoteAt(0);
        	if(numberOfButtons < 0) {
        		numberOfButtons = 0;
        		System.out.println("Number of buttons is negative - changed to zero");
        	}
        	int buttonSpace=0;
        	
        //	if(numberOfButtons>1)
        	buttonSpace = (460 - (numberOfButtons+1)*10)/(numberOfButtons);
        	int tabIt = 1;

            for(int i = 1; i <=model.getFreqOfNoteAt(0); i++) {
            	
            	if(!model.getNameOfNoteAt(i).equals("#")) {            		
            		jButtonTest = new JButton();
                	jButtonTest.setText(model.getNameOfNoteAt(tabIt));
                	jButtonTest.setBounds(8+(buttonSpace+10)*(tabIt-1),180,buttonSpace,50);
                	jButtonTest.addActionListener(controller);
                	getContentPane().add(jButtonTest);
                	tabIt++;
            	}          	
            } 
        } catch(ArithmeticException e) {
        	System.out.println("In ModeView.initButtons() button num = 0");
        }   			
	}
    /*
     * Set name of sign in the top
     */
	public void setSigns(String name) {
    	jLabelChosenString.setText(name);
    }
	/*
	 * makes stop button either visible or invisible
	 */
	public void setStop(boolean val) {
		jButtonStop.setVisible(val);
	}
    public void removeWarning() {}
    /*
     * Swaps the name of sign to "You are tuning"
     */
    public void signSwap() {	
    	jLabelMessage.setText("You are tuning");
    }
    /**
     * Sets message to the user
     * @param msg - string given to be displayed
     */
    public void setMessage(String msg) {
    	jLabelMessage.setText(msg);
    }
    /*
     * Sets default layout
     */
    public void setDefaultButtonsLayout() {
    	jLabelMessage.setText("Choose the string to tune");
    	jLabelChosenString.setText("");
    	jButtonStop.setVisible(false);
    }
    /*
     * Sets name of the mode - either auto or manual
     */
    public void setModeName(String name) {
    	jLabelName.setText(name);
    }
    /*
     * Enables the user to close the frame by clicking a cross in the 
     * top right corner
     */
    public void close(){
        WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
         Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
   }
}
    
    