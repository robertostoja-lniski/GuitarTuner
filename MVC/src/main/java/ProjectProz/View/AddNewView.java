package ProjectProz.View;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ProjectProz.Controller.AddNewController;
import ProjectProz.Model.Model;

/**
 * @author Robert
 * View of adding new tuning
 * @param JLabels are names displayed on screen
 * @param setupTab stores data typed by user
 */
public class AddNewView extends JFrame {

	Model model;
	int whereToReturn;
	private static final long serialVersionUID = 1L;
	JLabel jLabelName, jLabelChoose, jLabelChosen, jLabelChosenString, jLabelFreq, jLabelNote;
	JTextField[] setupTab;
	JButton applyButton,backButton;
	/**
	 * 
	 * @param controller - add new controller
	 * @param whereToReturn 0 for manual, 1 for auto and 2 for menu
	 */
	public AddNewView(Model model, int whereToReturn) {
		
		this.whereToReturn = whereToReturn;
		setupTab = new JTextField[20];
	
		setSize(300,500);
		setVisible(true);
		initLabes();
		initButtons();
		getContentPane().setLayout(null);
	}
	/**
	 * button initialisation
	 */
	public void setController(AddNewController controller) {
		applyButton.addActionListener(controller);
		backButton.addActionListener(controller);
		for (int i=1; i<8; i++) {
			setupTab[2*i].addActionListener(controller);
	    	setupTab[2*i+1].addActionListener(controller);
		}
		
    	 
	}
	private void initButtons() {
				
		applyButton = new JButton("Apply");
		backButton = new JButton("Back");
		
		applyButton.setBounds(35, 380, 100, 50);
		backButton.setBounds(155, 380, 100, 50);
		
		getContentPane().add(applyButton);
		getContentPane().add(backButton);
		
		
	}
	/**
	 * 
	 * @param i - index of setupTab
	 * @return string type data stored in setup Tab, or "" if exception caught
	 */
	public String getTextField(int i ) {
		try {
			return setupTab[i].getText();
					
		} catch (NullPointerException e) {
			System.out.println("in getTextField tried to get null pointer");
			return "";
		}
	}
	/**
	 * Labels initialisation
	 */
	private void initLabes() {
         jLabelChoose = new JLabel("Enter tuning name");
         jLabelChoose.setBounds(80,0,220,80);
         jLabelChoose.setFont(new Font("Dialog", 0, 14));
         
         setupTab[0] = new JTextField("");
         setupTab[1] = new JTextField("");
         setupTab[1].setActionCommand("name");
         setupTab[1].setBounds(90, 60, 100, 25);
         
         getContentPane().add(setupTab[1]);
                 
         jLabelFreq = new JLabel("frequency");
         jLabelFreq.setBounds(45, 90, 80, 20);       
         jLabelFreq.setFont(new Font("Dialog", 0, 12));
         
         jLabelNote = new JLabel("note name");
         jLabelNote.setBounds(185, 90, 80, 20);
         jLabelNote.setFont(new Font("Dialog", 0, 12));
         
         String f,n;
         for(int i = 1; i<=8; i++) {
        	 f = "freq"; 
        	 n ="name";
        	 f+=i;	
        	 n+=i;
        	// System.out.println("Tutej");
        	// JTextField customFieldf = new JTextField();
        	// JTextField customFieldn = new JTextField();
        	 setupTab[2*i] = new JTextField("");
        	 setupTab[2*i+1] = new JTextField("");
        	 
        	 setupTab[2*i].setBounds(35,90+i*30, 80, 25);
        	 setupTab[2*i+1].setBounds(175, 90+i*30, 80, 25);
        	 
        	 setupTab[2*i].setActionCommand(f);
        	 setupTab[2*i+1].setActionCommand(n);
        	         	         	 
        	
        	// System.out.println(setupTab[2*i].getText().equals(""));   
        	 
        	 getContentPane().add(setupTab[2*i]);
        	 getContentPane().add(setupTab[2*i+1]);
         }
        
         getContentPane().add(jLabelChoose);
         getContentPane().add(jLabelNote);
         getContentPane().add(jLabelFreq);
       
	}
	/**
	 * features dialog error window that data format is of a wrong type
	 */
	public void returnErrorMsg() {
    	JOptionPane.showMessageDialog(this, "Wrong data format");
    }
	/**
	 * features dialog error window that few string can't have the same name
	 */
	public void returnTheSameNameError() {
    	JOptionPane.showMessageDialog(this, "Few strings can't have the same name");
    }
	/**
	 * closes the view
	 */
	public void closeAddOption() {
		try {
			WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
			Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
		} catch (Exception e) {
			System.out.println("In model.closeAddOption() null pointer exception");
		}
	}
}
