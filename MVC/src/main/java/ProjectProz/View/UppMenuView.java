package ProjectProz.View;

import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import ProjectProz.Controller.UppMenuController;
import ProjectProz.Model.Model;

/**
 * @author Robert
 * View of upper menu
 */

public class UppMenuView extends JMenuBar{
	
	private static final long serialVersionUID = 1L;
	
	JMenuItem defaultTuning;
	JMenuItem menuItemNew;
	JMenuItem menuHelp;
	JMenu menuOption;
	Vector<JRadioButtonMenuItem> tuningsToChoose;
	Model model;
	/**
	 * adding controller
	 * @param controller
	 */
	public void setController(UppMenuController controller) {
		defaultTuning.addActionListener(controller);
        menuItemNew.addActionListener(controller);
        menuHelp.addActionListener(controller);
        addControllerToMenu(controller);
	}
	/**
	 * 
	 * @param controller
	 * @param model
	 * @param whereToReturn
	 */
	public UppMenuView(Model model) {
		this.model = model;
        JMenu menuEdit = new JMenu("Edit");
        menuEdit.setMnemonic(KeyEvent.VK_F);
        add(menuEdit);
        
        menuOption = new JMenu("Set tuning");
        menuOption.setMnemonic(KeyEvent.VK_P);
        menuEdit.add(menuOption);
        
        menuHelp = new JMenuItem("Help");
        add(menuHelp);
        
        menuItemNew = new JMenuItem("Add new tuning", KeyEvent.VK_N);
        menuEdit.add(menuItemNew);
        
        defaultTuning = new JMenuItem("Set defaults");
        menuEdit.add(defaultTuning);
        
        ButtonGroup group = new ButtonGroup();
        tuningsToChoose = new Vector<JRadioButtonMenuItem>();
        String optionName;
        for(int i=0; i<model.getSize(); i++) {
        	
        	optionName = model.getTuningName(i); 	
        	JRadioButtonMenuItem newOption = new JRadioButtonMenuItem(optionName);
        	newOption.setActionCommand("#"+optionName);
        	
            menuOption.add(newOption);
            tuningsToChoose.add(newOption);
            group.add(newOption);
        } 
             
        
	}
	
	private void addControllerToMenu(UppMenuController controller) {
	
        for(int i=0; i<tuningsToChoose.size(); i++) {
        	    
            tuningsToChoose.get(i).addActionListener(controller);
        } 
	}
}
