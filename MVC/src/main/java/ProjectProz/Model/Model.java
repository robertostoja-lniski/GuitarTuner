package ProjectProz.Model;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
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

import ProjectProz.View.AutoTuningPanelView;

/**
 * @author Robert
 * Model - stored tuning data, reads and generates frequency
 */
public class Model  {
	
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
	int scaledFreq;
	double unscaledFreq;
	/**
	 * Constructor with number of lines given
	 * @param lines
	 */
	public Model(int lines) {
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
	public Model() {
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
	 public void initializeTab() {
		for(int i =0; i<10; i++) {
			noteUsed[i] = new note();
			noteUsed[i].name = "#";
		}
	}
	/**
	 * 
	 * @return returns number of lines
	 */
	 
	public int getLines() {
		return lines;
	}
	/**
	 * gives access to the thread
	 * @return
	 */
	public FreqReader getRunnable() {
		return (FreqReader) runnable;
	}
	/**
	 * 
	 * @return return the length of note used tab
	 */
	public int getTabSize() {
		return noteUsed.length;
	}
	/**
	 * 
	 * @param lines
	 * changes lines field to parameter given
	 */
	public void setLines(int lines) {
		this.lines = lines;
	}
	/**
	 * Saves tuning to a file
	 * @param name - name of file to which data should be written
	 * @throws File not found and Unsupported encoding exception
	 */
	public void saveTuningToNewFile(String name) {
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
	public int getNoteBaseSize() {
		return noteBase.size();
	}
	/**
	 * Clears note base - deletes data stored in
	 * the vector
	 */
	public void clearNoteBase() {
				
		int n = getNoteBaseSize();

		for(int i=n-1; i>=0 ; i--) {
			noteBase.remove(i);
		}
	}
	/**
	 * Clears notes used - does not clear
	 * any other data structure
	 */
	public void clearNotesUsed() {
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
	public void readTuningFromFile(String fileName) {
		
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
    public String getNameOfNoteAt(int i) {
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
    public double getFreqOfNoteAt(int i) {
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
     public void setTuning(String command) {
    	 
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
     public int AddTuningToDataBase(Vector<String> textToProcess,  int numberOfLines) {

    	Vector<note> noteLine = new Vector<note>();
    	note setupNote = new note();
    	setupNote.freq = numberOfLines;
    	setupNote.name = textToProcess.get(1);
    	noteLine.addElement(setupNote);
    	
	        	 for(int i = 1; i <= 8 ; i++) {
	 	        		
	 	        		if(!textToProcess.get(2*i).isEmpty() && !textToProcess.get(2*i+1).isEmpty()) {	 	        			
	 	        			note nextNote = new note();
	 	        			nextNote.freq = Double.parseDouble(textToProcess.get(2*i));
	 	        			nextNote.name = textToProcess.get(2*i+1);
	 	        			noteLine.addElement(nextNote);
	 	        		}
	 	        }
	     noteBase.addElement(noteLine);
	     setLines(lines+1);
	     
	    return 1;

    }

    /**
     * Gets tuning name at i position from note base vector
     * @param i - index
     * @return name of tuning at index i
     * @throws IndexOutOfBoundsException and NullPointerException
     */
	public String getTuningName(int i) {
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
	 * Unused method for displaying notes used
	 */
    public void checkNotesUsed() {
    	
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
    public void setFreqAt(int i, double val) {
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
    public int getNumberOfButtons() {
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
     * reads frequency and compares it to
     * expected frequency of note at i position
     * @param i - index
     */
    public void tune( int i ) {
    	
    	try {
					
				((FreqReader) runnable).setIndex(i);
				Thread thread = new Thread(runnable);
				thread.start();
		
		} catch (NullPointerException e) {
			System.out.println("In model.tune null pointer exception");
		}
       	
    }

    public void setTuningPanel(AutoTuningPanelView tuningPanel) {
    	this.tuningPanel = tuningPanel;
    }
    /**
     * returns random integer
     * @return
     */
    int getRandomNum() {
    	return (int) (Math.random()*300+80);
    }
    /**
     * Return y value of a point based on it's x value.
     * Points are at circle with a 150 range
     * @param x - first point value
     * @return y value of a point
     */
    public int getPointY(int x) {
    	try {
    		return 150-(int)Math.sqrt(14000-(x-150)*(x-150));
    	} catch(Exception e) {
    		System.out.println("Exception caught");
    		return 0;
    	}
    	
    }
    /**
     * 
     * @return size of noteBase
     */
    public int getSize() {
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
     * plays a sound at frequency of a given note
     * @param i - index of noteUsed vector
     */
    public void sound(int i) {
    	try {
    		//System.out.println(noteUsed[i].name);
    		
    		Generator g = new Generator(noteUsed[i].freq);
    		g.play();
    		
    	} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("In model.sound(int) index out of bounds.");
		} catch(NullPointerException e) {
			System.out.println("In model.sound(int) null pointer exception");
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
    /*
     * sets scaled frequency
     */
    public void setScaledFreq(int scaled) {
    		scaledFreq = scaled;
    }
    /*
     * sets unscaled frequency
     */
    public void setUnscaledFreq(double unscaled) {
    		 unscaledFreq = unscaled;
    }
    /**
     *
     * @return returns scaled frequency
     */
    public int getScaledFreq() {
    	return scaledFreq;
    }
    /**
     * returns unscaled frequency
     * @return
     */
    public double getUnscaledFreq() {
    	return unscaledFreq;
    }
  
   
}