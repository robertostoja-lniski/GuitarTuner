package ProjectProz.MVC;

import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

/**
 * @author Robert
 * View of upper menu
 */

public class UppMenuView extends JMenuBar{
	
	private static final long serialVersionUID = 1L;
	
	UppMenuController controller;
	Model model;
	// 0 menu, 1 manual, 2 auto
	int whereToReturn;
	
	UppMenuView(UppMenuController controller, Model model, int whereToReturn) {
		this.model = model;
		this.whereToReturn = whereToReturn;
		this.controller = controller;
        JMenu menuEdit = new JMenu("Edit");
        menuEdit.setMnemonic(KeyEvent.VK_F);
        add(menuEdit);
        
        JMenu menuOption = new JMenu("Set tuning");
        menuOption.setMnemonic(KeyEvent.VK_P);
        menuEdit.add(menuOption);
        
        JMenuItem menuHelp = new JMenuItem("Help");
        add(menuHelp);
        
        JMenuItem menuItemNew = new JMenuItem("Add new tuning", KeyEvent.VK_N);
        menuEdit.add(menuItemNew);
        
        JMenuItem defaultTuning = new JMenuItem("Set defaults");
        menuEdit.add(defaultTuning);
        
        ButtonGroup group = new ButtonGroup();
        
        String optionName;
        for(int i=0; i<model.getSize(); i++) {
        	
        	optionName = model.getTuningName(i); 	
        	JRadioButtonMenuItem newOption = new JRadioButtonMenuItem(optionName);
        	newOption.setActionCommand("#"+optionName);
        	newOption.addActionListener(controller);
            menuOption.add(newOption);
            group.add(newOption);
        }      
        defaultTuning.addActionListener(controller);
        menuItemNew.addActionListener(controller);
        menuHelp.addActionListener(controller);
	}
	/**
	 * 
	 * @return either 0 for manual, 1 for manual or 2 for auto
	 */
	public int getReturnCommand() {
		return whereToReturn;
	}
}
