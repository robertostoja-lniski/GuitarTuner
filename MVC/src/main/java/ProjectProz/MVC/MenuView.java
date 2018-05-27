package ProjectProz.MVC;



import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 * @author Robert
 * Main Menu View
 */
public class MenuView extends JFrame {
	
	MenuController controller;
	Model model;
	UppMenuView uppmenuview;
	private static final long serialVersionUID = 1L;
	private JButton jButtonAuto, jButtonManual , jButtonQuit;
    private JLabel foto;
    
    /**
     *  Constructor
     */
    public MenuView(MenuController controller, Model model, UppMenuView uppmenuview) {
    	this.controller = controller;
    	this.model = model;
    	this.uppmenuview = uppmenuview;
    	model.setView(this);
        initComponents();
        setVisible(true);
        setSize(460,580);
        setResizable(false);
    }
    /**
     *  Initializes photo, buttons and labels
     */
    private void initComponents() {
    	
    	initLabels("fotoBeta.jpg");
    	initButtons();
    	initUppMenu();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        pack();
    }
   /**
    * Initializes upper menu
    */
    private void initUppMenu() {
    	setJMenuBar(uppmenuview);	
	}
    /**
     * Initializes Labels
     * @param fotoPath - path to photo displayed on the screen
     */
	public void initLabels(String fotoPath) {

	    foto = new JLabel();
	    try {
	    	foto.setIcon(new ImageIcon(getClass().getResource(fotoPath)));
	        foto.setBounds(80,40,300,200);
	        getContentPane().add(foto);
	    } catch(NullPointerException e) {
	    	System.out.println("There is no such photo");
	    }
        
    }
    /**
     * Initializes buttons 
     */
    public void initButtons() {
    	  
    	  jButtonAuto = new JButton("Auto");
          jButtonManual = new JButton("Manual");
          jButtonQuit = new JButton("click to quit");
     
          jButtonAuto.addActionListener(controller);
          getContentPane().add(jButtonAuto);
          jButtonAuto.setBounds(50, 280, 155, 85);

          jButtonManual.addActionListener(controller);
          getContentPane().add(jButtonManual);
          jButtonManual.setBounds(250, 280, 153, 85);

          jButtonQuit.addActionListener(controller);
          getContentPane().add(jButtonQuit);
          jButtonQuit.setBounds(170, 450, 120, 50);
    }
    
    public void removeWarning() {}
}