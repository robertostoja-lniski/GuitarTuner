package ProjectProz.MVC;

import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author Robert
 * Model - stored tuning data, reads and generates frequency
 */
public class Model  {
	
    ManualModeView manualmode;
    AutoModeView automode;
    MenuView menuview;
    AddNewView setupview;
    UppMenuView uppmenuview;
    AutoTuningPanelView tuningPanel;
    class note {
		double freq;
		String name;
		
		note(double freq, String name) {
			this.freq = freq;
			this.name = name;
		}
		note(){}
	};
    int lines;
    int chosenLine;
    boolean tuningTurnedOn = false;
	Vector<note> notsUsed;
	note[] noteUsed;
	Vector < Vector<note> > noteBase; 
	Runnable runnable;
	/**
	 * Constructor with number of lines given
	 * @param lines
	 */
	Model(int lines) {
		//fqReader = new FreqReader();  
		Runnable runnable = new FreqReader(this);
		this.runnable = runnable;
		noteBase = new Vector < Vector<note> >();
		noteUsed = new note[10];
		this.lines = lines;
	}
	/**
	 * Default constructor
	 */
	Model() {
		//fqReader = new FreqReader();  
		Runnable runnable = new FreqReader(this);
		this.runnable = runnable;
		noteBase = new Vector < Vector<note> >();
		noteUsed = new note[10];
		lines = 6;		
	}
	/**
	 * Initializes tab of used notes
	 */
	 void initializeTab() {
		for(int i =0; i<10; i++) {
			noteUsed[i] = new note();
			noteUsed[i].name = "#";
		}
	}
	/**
	 * 
	 * @return returns number of lines
	 */
	 
	int getLines() {
		return lines;
	}
	/**
	 * 
	 * @return return the length of note used tab
	 */
	int getTabSize() {
		return noteUsed.length;
	}
	/**
	 * 
	 * @param lines
	 * changes lines field to parameter given
	 */
	void setLines(int lines) {
		this.lines = lines;
	}
	/**
	 * Saves tuning to a file
	 * @param name - name of file to which data should be written
	 * @throws File not found and Unsupported encoding exception
	 */
	void saveTuningToNewFile(String name) {
		PrintWriter writer;

		try {
			writer = new PrintWriter(name, "UTF-8");
			writer.println(lines);
			try {
				for(int i=0; i<lines; i++) {

					for(int j=0; j<=(int)noteBase.get(i).get(0).freq; j++) {
						
						writer.printf("%.2f", noteBase.get(i).get(j).freq);
						writer.print(" " + noteBase.get(i).get(j).name +"\t");
					}
					writer.println();					
				}
				writer.close();
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Index out of bounds in print save tuning to file!");
			} 
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}	
	}	
	/**
	 * @return return the size of the whole note base
	 */
	int getNoteBaseSize() {
		return noteBase.size();
	}
	/**
	 * Clears note base - deletes data stored in
	 * the vector
	 */
	void clearNoteBase() {
				
		int n = getNoteBaseSize();

		for(int i=n-1; i>=0 ; i--) {
			noteBase.remove(i);
		}
	}
	/**
	 * Clears notes used - does not clear
	 * any other data structure
	 */
	void clearNotesUsed() {
		try {
			for(int i =0 ; i<10 ; i++) {
				noteUsed[i].name = "#";
			}
		} catch (NullPointerException e) {
			System.out.println("In clearNotesUsed tried to access null pointer");
		}
		
	}
	/**
	 * Reads tuning from the file given
	 * @param fileName - path to the file
	 * @throws File not found exception and wrong data format exception
	 */
	void readTuningFromFile(String fileName) {
		
		clearNoteBase();
		Scanner scan;
	    File file = new File(fileName);
	    try {
	        scan = new Scanner(file);
	        int it = scan.nextInt();
	        lines = it;
	        try {
	        	 for(int i =0; i < it ; i++) {
	 	        	Vector<note> noteLine = new Vector<note>();
	 	        	note lineSetup = new note();
	 	        	double len = scan.nextDouble();
	 	        	lineSetup.freq = len;
	 	        	lineSetup.name = scan.next();

	 	        	noteLine.addElement(lineSetup);
	 	        	for(int j =0; j <len; j++) {

	 	        		note nextNote = new note();	 	        		
	 	        		nextNote.freq = scan.nextDouble();
	 	        		nextNote.name = scan.next();
	 	        		noteLine.addElement(nextNote);

	 	        	}

	 	        	noteBase.addElement(noteLine);
	 	        	
	 	        }
	        } catch(InputMismatchException e2) {
	    		System.out.println("Wrong data in the file");
	    }	       
	    } catch (FileNotFoundException e1) {
	            e1.printStackTrace();
	    } 
	}
	/**
	 * 
	 * @param i - index
	 * @return string type name of note and i index
	 * @throws array out of bounds exception
	 */
    String getNameOfNoteAt(int i) {
    	try {
    		return noteUsed[i].name;
    	} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Index out of bounds in get Name at!");
			return "";
		}    	
    }
    /**
	 * 
	 * @param i - index
	 * @return double type freq of note and i index
	 * @throws array out of bounds exception
	 */
    double getFreqOfNoteAt(int i) {
    	try {
    		return noteUsed[i].freq;
    	} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Index out of bounds in get Freq at!");
			return 0;
		}   
    	
    }
    /**
     * 
     * chooses tuning from note based and sets it
     * to note used tab
     * @param command - command distinguishing tuning
     */
     void setTuning(String command) {
    	 
    	for(int i =0; i<lines; i++) {
    		
    		if(noteBase.get(i).get(0).name.equals(command)) {
    		
    			for(int j=0; j<=noteBase.get(i).get(0).freq; j++) {
    		
    				noteUsed[j].freq = noteBase.get(i).get(j).freq;
    				noteUsed[j].name = noteBase.get(i).get(j).name;
    				//System.out.println(noteUsed[j].freq + " " + noteUsed[j].name);
    				
    			}
    			for(int j=(int)noteBase.get(i).get(0).freq+1; j<10; j++) {
    				noteUsed[j].name = "#";
    			}
    			return;
    		}
    	}
    	System.out.println("Tuning not found");
    	
    }
     /**
      * Adds new tuning to note base vector
      * @return 0 if tuning is not added or 1 if adding is successful
      */
     int AddTuning() {
    	int num = checkBeforeAdd();
    	if(num==0) {
    		return 0;
    	}	
    	Vector<note> noteLine = new Vector<note>();
    	note setupNote = new note();
    	setupNote.freq = num;
    	setupNote.name = setupview.getTextField(1).replaceAll("\\s", "_");
    	noteLine.addElement(setupNote);
    	
	        	 for(int i = 1; i <= 8 ; i++) {
	 	        		 	        	
	 	        		if(!setupview.getTextField(2*i).equals("")) {	 	        			
	 	        			note nextNote = new note();
	 	        			nextNote.freq = Double.parseDouble(setupview.getTextField(2*i));
	 	        			nextNote.name = setupview.getTextField(2*i+1).replaceAll("\\s", "_");
	 	        			noteLine.addElement(nextNote);
	 	        		}
	 	        }
	     noteBase.addElement(noteLine);
	     setLines(lines+1);
	     
	    return 1; 
    }
    public UppMenuView getUppMenuView() {
    	return uppmenuview;
    }
    /**
     * Gets tuning name at i position from note base vector
     * @param i - index
     * @return name of tuning at index i
     * @throws IndexOutOfBoundsException and NullPointerException
     */
	String getTuningName(int i) {
		try {
			return noteBase.get(i).get(0).name;
		}catch (IndexOutOfBoundsException e) {
			System.out.println("in model :: get Tuning Name index out of bounds");
			
		}catch(NullPointerException e) {
			System.out.println("in model :: get Tuning Name null pointer");
		}
		return "";
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
    		if(setupview.getTextField(1).equals("")) {   			
        		returnErrorMsg();
        		return 0;
        	}    		
        	for(int i = 1; i<=8; i++) {
        	     
        		fstr = setupview.getTextField(2*i);
        		name = setupview.getTextField(2*i+1);

        		
				if(!fstr.equals(""))
        			Double.parseDouble(setupview.getTextField(2*i));

        		if((!fstr.equals("") && name.equals("")) || (fstr.equals("") && !name.equals(""))) {
        			returnErrorMsg();
        			return 0;
        		} else {
        			if(!fstr.equals("") && !name.equals(""))
        			num++;
        		}
        		
        	}

        	for(int i = 1; i <=8; i++) {
        		name = setupview.getTextField(2*i+1);
        	//	System.out.println(name);
        		for(int j=i+1; j<=8; j++) {
        			fstr = setupview.getTextField(2*i);
        			if(!fstr.equals("") && !name.equals("")) {
        				if(setupview.getTextField(2*j+1).equals(name)) {
            				System.out.println(i);
            				returnTheSameNameError();
            				return 0;
            			} 
        			}
        			       		
        		}
        	}
    	} catch (NumberFormatException e ) {  		
    		returnErrorMsg();
    		return 0;
    	}
    	return num;
    	
    }
	/**
	 * features dialog error window that data format is of a wrong type
	 */
	private void returnErrorMsg() {
    	JOptionPane.showMessageDialog(setupview, "Wrong data format");
    }
	/**
	 * features dialog error window that few string can't have the same name
	 */
	private void returnTheSameNameError() {
    	JOptionPane.showMessageDialog(setupview, "Few strings can't have the same name");
    }
	/**
	 * Unused method for displaying notes used
	 */
    void checkNotesUsed() {
    	
    	System.out.println();
    	System.out.println("Checking notes used");
    	try {
    		for(int i =0; i<10; i++) {
        		//if(noteUsed[i].name!="#")
        		System.out.print( noteUsed[i].freq + " " + noteUsed[i].name + "  ");
        	}
    	} catch(NullPointerException e) {
    		System.out.println("In model.checkNotesUsed() null pointer exception");
    	}
    	
    }
    
    /**
     * Method setting frequency at index i
     * @param i - index
     * @param val - value to be set
     */
    void setFreqAt(int i, double val) {
    	try {
    	    noteUsed[i].freq= val;
    	} catch (Exception e) {
			System.out.println("Either null pointer, or array out of bounds :)");			
		} 
    	return ;
    }
    /**
     * 
     * @return number of buttons to be displayed
     * 
     */
    int getNumberOfButtons() {
    	int result=0;
    	try {
    		for(int i =0; i<10; i++) {
        		if(noteUsed[i].name!="#") {
        			result++;
        		}
        	}
    	} catch(NullPointerException e) {
    		System.out.println("In model.getNumberOfButtons() null pointer exception");
    	}
    	
    	return result;
    }
    /**
     * 
     * @param on - if false new tuning cannot be added
     */
    void setTuningOn(boolean on) {
    	tuningTurnedOn = on;
    }
    /**
     * reads frequency and compares it to
     * expected frequency of note at i position
     * @param i - index
     */
    void tune( int i ) {
    	
    	try {
					
				((FreqReader) runnable).setIndex(i);
				Thread thread = new Thread(runnable);
				thread.start();
		
		} catch (NullPointerException e) {
			System.out.println("In model.tune null pointer exception");
		}
       	
    }
    /**
     * draw a line from default point to point (x,y)
     * y is then calculated
     * @param x - first value of point
     */
    void drawLine(int x) { 
    	 try {
    		 tuningPanel.setXY(x);
             tuningPanel.update(tuningPanel.getGraphics());
    	 } catch(NullPointerException e) {
    		 System.out.println("In model.drawLine() null pointer exception");
    	 }
    	 

    }
    void repaintAuto() {
    	automode.repaint();
    }
    void setTuningPanel(AutoTuningPanelView tuningPanel) {
    	this.tuningPanel = tuningPanel;
    }
    int getRandomNum() {
    	return (int) (Math.random()*300+80);
    }
    /**
     * Return y value of a point based on it's x value.
     * Points are at circle with a 150 range
     * @param x - first point value
     * @return y value of a point
     */
    int getPointY(int x) {
    	return 150-(int)Math.sqrt(14000-(x-150)*(x-150));
    }
    void setView(AutoModeView automode) {
    	this.automode = automode;
    }
    void setView(ManualModeView manualmode) {
    	this.manualmode = manualmode;
    }
    void setView(MenuView menuview) {
    	this.menuview = menuview;
    }
    void setView(AddNewView setupview) {
    	this.setupview = setupview;
    }
    /**
     * 
     * @return size of noteBase
     */
    int getSize() {
    	try {
    		return noteBase.size();
    	} catch (NullPointerException e) {
    		System.out.println("In model.getSize() null pointer exception");
    		return 0;
    	}
    	
    }
    int getChosenLine() {
    	return chosenLine;
    }
    /**
     * sets tuning message
     */
    void setTuningMessage() {
    	automode.signSwap();
    }
    /**
     * sets tuning message
     * @param name - displays name of a string that is being tuned
     */
    void setTuningMessage(String name) {
    	//System.out.println("hejo");
    	String msg = "You are tuning " + name;
    	//System.out.println(msg);
    	automode.setMessage(msg);
    }
    /**
     * plays a sound at frequency of a given note
     * @param i - index of noteUsed vector
     */
    void sound(int i) {
    	try {
    		//System.out.println(noteUsed[i].name);
    		manualmode.signSwap();
    		manualmode.setSigns(noteUsed[i].name);
    		manualmode.setStop(true);
    		Generator g = new Generator(noteUsed[i].freq);
    		g.play();
    		
    	} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("In model.sound(int) index out of bounds.");
		} catch(NullPointerException e) {
			System.out.println("In model.sound(int) null pointer exception");
		}
    }
    /**
     * Sets default manual view layout
     */
    void setManualDefaultLayout() {
    	try {
    		manualmode.setDefaultButtonsLayout();
    	} catch(NullPointerException e) {
    		System.out.println("In model.setManualDefaultLayout null pointer exception");
    	}
    	
    }
    void setFreqInfo(double f) {
    	tuningPanel.setFreqInfo(f);
    }
    /**
     * Sets default auto view layout
     */
    void setAutoDefaultLayout() {
    	    	
    	try {
    		automode.getPanel().setXY();
        	automode.update(automode.getGraphics());
        	automode.setDefaultButtonsLayout();
    	} catch(NullPointerException e) {
    		System.out.println("In model.setAutoDefaultLayout null pointer exception");
    	}
    }
    /**
     * Send a user to link given
     */
    public void helpChosen() {
    	String URL = "https://en.wikipedia.org/wiki/Electronic_tuner";
		try {
			Desktop.getDesktop().browse(URI.create(URL));
		} catch (IOException e1) {
			System.out.println("URL string error");
			e1.printStackTrace();
		}
    }
    /**
     * closes a mode
     * @param modeType - if false automode is closed, if true manual is closed
     */
    public void closeMode(boolean modeType){
    	
    	WindowEvent winClosingEvent;
    	
    	try {
			if(modeType) {
			    winClosingEvent = new WindowEvent(automode,WindowEvent.WINDOW_CLOSING);
			} else {
				winClosingEvent = new WindowEvent(manualmode,WindowEvent.WINDOW_CLOSING);
			}
			Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
		} catch (Exception e) {
			System.out.println("In model.closeMode(boolean) null pointer exception");
		}
         
        
   }
    /**
     * closes menu
     */
    public void closeMenu() {
    	try {
			WindowEvent winClosingEvent = new WindowEvent(menuview,WindowEvent.WINDOW_CLOSING);
			Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
		} catch (Exception e) {	
			System.out.println("In model.closeMenu() null pointer exception");
		}
    }
    /**
     * closes adding new tuning option
     */
    public void closeAddOption() {
    	try {
			WindowEvent winClosingEvent = new WindowEvent(setupview,WindowEvent.WINDOW_CLOSING);
			Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
		} catch (Exception e) {
			System.out.println("In model.closeAddOption() null pointer exception");
		}
    }
   
}