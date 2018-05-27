package ProjectProz.MVC;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * @author Robert
 * View of adding new tuning
 * @param JLabels are names displayed on screen
 * @param setupTab stores data typed by user
 */
public class AddNewView extends JFrame {

	AddNewController controller;
	Model model;
	int whereToReturn;
	private static final long serialVersionUID = 1L;
	JLabel jLabelName, jLabelChoose, jLabelChosen, jLabelChosenString, jLabelFreq, jLabelNote;
	JTextField[] setupTab;
	
	/**
	 * 
	 * @param controller - add new controller
	 * @param whereToReturn 0 for manual, 1 for auto and 2 for menu
	 */
	AddNewView(AddNewController controller, Model model, int whereToReturn) {
		
		this.controller = controller;
		this.whereToReturn = whereToReturn;
		model.setView(this);
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
	private void initButtons() {
				
		JButton applyButton = new JButton("Apply");
		JButton backButton = new JButton("Back");
		
		applyButton.setBounds(35, 380, 100, 50);
		backButton.setBounds(155, 380, 100, 50);
		
		getContentPane().add(applyButton);
		getContentPane().add(backButton);
		
		applyButton.addActionListener(controller);
		backButton.addActionListener(controller);
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
        	         	         	 
        	 setupTab[2*i].addActionListener(controller);
        	 setupTab[2*i+1].addActionListener(controller);
        	 
        	// System.out.println(setupTab[2*i].getText().equals(""));   
        	 
        	 getContentPane().add(setupTab[2*i]);
        	 getContentPane().add(setupTab[2*i+1]);
         }
        
         getContentPane().add(jLabelChoose);
         getContentPane().add(jLabelNote);
         getContentPane().add(jLabelFreq);
       
	}
}
