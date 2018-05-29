package ProjectProz.MVC;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ProjectProz.Controller.AddNewController;
import ProjectProz.Controller.MenuController;
import ProjectProz.Controller.ModeController;
import ProjectProz.Controller.UppMenuController;
import ProjectProz.Model.Model;
import ProjectProz.View.AddNewView;
import ProjectProz.View.AutoTuningPanelView;
import ProjectProz.View.MenuView;
import ProjectProz.View.ModeBaseView;
import ProjectProz.View.UppMenuView;

public class Tests {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void restoreStreams() {
	    System.setOut(System.out);
	    System.setErr(System.err);
	}
	
	@Test
	public void creatingModelTest() {	
		Model model = new Model();
		assertEquals("Microphone initialization\n", outContent.toString());	
	}
	@Test
	public void initializationNameTest() {	
		Model model = new Model();
		model.initializeTab();
		for(int i = 0 ; i< 10; i++) {
			assertTrue(model.getNameOfNoteAt(i).equals("#"));
		}	
	}
	@Test
	public void initializationFreqTest() {	
		Model model = new Model();
		model.initializeTab();
		for(int i = 0 ; i< 10; i++) {
			assertTrue(model.getFreqOfNoteAt(i)==0);
		}	
	}
	
	@Test 
	public void setFreqOfNote() {
	   
	   Model model = new Model();
	   model.initializeTab();
  	   model.readTuningFromFile("NewFile.txt");
  	   model.setTuning("Guitar_Standard_E");
  	   
  	   double expected = 10.0;
  	   model.setFreqAt(0, expected);
  	   double actual = model.getFreqOfNoteAt(0);
  	   double delta = 0;
  	   assertEquals(expected, actual, delta);
	}
	

	@Test //(expected=ArrayIndexOutOfBoundsException.class)
	public void outOfBounds1() {
		Model model = new Model();
		model.initializeTab();
		model.getNameOfNoteAt(15);
	}
	@Test //(expected=ArrayIndexOutOfBoundsException.class)
	public void outOfBounds2() {
		Model model = new Model();
		model.initializeTab();
		model.getFreqOfNoteAt(15);
	}
	@Test
	public void messageCheckNameAt(){
		Model model = new Model();
		model.initializeTab();
		model.getNameOfNoteAt(15);
		String expected = "Index out of bounds in get Name at!";
		String input = outContent.toString();		
		String actual = input.substring(input.length() - expected.length()-2, input.length()-2);
		assertEquals(expected , actual);
	}
	@Test
	public void messageCheckFreqAt(){
		Model model = new Model();
		model.initializeTab();
		model.getFreqOfNoteAt(15);
		String expected = "Index out of bounds in get Freq at!";
		String input = outContent.toString();		
		String actual = input.substring(input.length() - expected.length()-2, input.length()-2);
		assertEquals(expected , actual);	
	}
	@Test
	public void UninitTabSizeTest() {
		Model model = new Model();
		assertEquals(10, model.getTabSize());
	}
	@Test
	public void initTabSizeTest() {
		Model model = new Model();
		model.initializeTab();
		assertEquals(10, model.getTabSize());
	}
	@Test 
	public void UninitBaseNoteSaveTuning() {
		Model model = new Model();
		model.saveTuningToNewFile("TestFile.txt");
	}
	@Test 
	public void linesNumTestDefaultConstructor() {
		Model model = new Model();
		assertEquals(6 , model.getLines());
	}
	@Test 
	public void linesNumTestParamConstructor() {
		Model model = new Model(10);
		assertEquals(10 , model.getLines());
	}
	@Test
	public void noteBaseClear() {
		Model model = new Model();
		model.initializeTab();
	 	model.readTuningFromFile("NewFile.txt");	 	
	 	model.clearNoteBase();
	 	assertEquals(0, model.getNoteBaseSize());		
	}
	@Test
	public void tryingToSaveUnaccessibleData() {
		Model model = new Model();
		model.initializeTab();
		model.setLines(1000);
		model.saveTuningToNewFile("TestFile.txt");
	}
	@Test
	public void clearNotes() {
		Model model = new Model();
		model.initializeTab();
		model.readTuningFromFile("NewFile.txt");
		model.clearNotesUsed();
		int positiveCounter=0;
		for(int i=0; i<10 ; i++){
			if(model.getNameOfNoteAt(i).equals("#"))
				positiveCounter++;
		}
		assertEquals(10, positiveCounter);
	}
	@Test
	public void clearNotesBeforeInitialisation() {
		Model model = new Model();
		model.clearNotesUsed();
		model.initializeTab();		
	}
	@Test
	public void readingFromUnexistingFile() {
		Model model = new Model();
		model.initializeTab();
		model.readTuningFromFile("NotExistingFile.txt");
	}
	@Test
	public void settingNotExistingTuning() {
		Model model = new Model();
		model.initializeTab();
		model.readTuningFromFile("NewFile.txt");
		model.setTuning("NotExistingTuning");
	}
	@Test
	public void settingNotExistingTuningMessage() {
		Model model = new Model();
		model.initializeTab();
		model.readTuningFromFile("NewFile.txt");
		model.setTuning("NotExistingTuning");
		
		String expected = "Tuning not found";
		String input = outContent.toString();		
		String actual = input.substring(input.length() - expected.length()-2, input.length()-2);
		
		assertEquals(expected , actual);
	}
	@Test
	public void notesUsedCheckBeforeInitialisation() {
		Model model = new Model();
		model.checkNotesUsed();
	}
	@Test
	public void notesUsedCheckBeforeInitialisationMessage() {
		Model model = new Model();
		model.checkNotesUsed();
		
		String expected = "In model.checkNotesUsed() null pointer exception";
		String input = outContent.toString();		
		String actual = input.substring(input.length() - expected.length()-2, input.length()-2);
		
		assertEquals(expected , actual);
	}
	@Test
	public void getNumberOfButtonsUninitialised() {
		Model model = new Model();
		model.getNumberOfButtons();
		
		String expected = "In model.getNumberOfButtons() null pointer exception";
		String input = outContent.toString();		
		String actual = input.substring(input.length() - expected.length()-2, input.length()-2);
		
		assertEquals(expected , actual);
		
	}
	@Test
	public void getNumberOfButtons() {
		Model model = new Model();
		model.initializeTab();
		assertEquals(0, model.getNumberOfButtons());	
	}
	@Test 
	public void tuningTest() {
		Model model = new Model();
		model.initializeTab();
		model.tune(100000);
	}
	@Test 
	public void tuningTest1() {
		Model model = new Model();
		model.initializeTab();
		model.tune(-10000);
	}
	@Test
	public void settingFreq() {
		Model model = new Model();
		model.setScaledFreq(888);
		double actual = model.getScaledFreq();
		assertEquals(888 , actual, 0);		
	}
	@Test
	public void getSizeUninitialised() {
		Model model = new Model();		
		assertEquals(0 , model.getSize());		
	}
	@Test
	public void getSizeInitialised() {
		Model model = new Model();	
		model.initializeTab();
		assertEquals(0 , model.getSize());		
	}
	@Test
	public void soundOutOfBounds() {
		Model model = new Model();	
		model.sound(1900);	
		
		String expected = "In model.sound(int) index out of bounds.";
		String input = outContent.toString();		
		String actual = input.substring(input.length() - expected.length()-2, input.length()-2);
		
		assertEquals(expected , actual);
	}
	@Test
	public void soundNullPointer() {
		Model model = new Model();	
		model.sound(4);	
		
		String expected = "In model.sound(int) null pointer exception";
		String input = outContent.toString();		
		String actual = input.substring(input.length() - expected.length()-2, input.length()-2);
		
		assertEquals(expected , actual);
	}
	
	@Test
	public void closeMode() {
		Model model = new Model();	
		model.initializeTab();
		model.getPointY(0);
		
		String expected = "";
		String input = outContent.toString();		
		String actual = input.substring(input.length() - expected.length()-2, input.length()-2);
		
		assertEquals(expected , actual);
	}
	
	
	// view tests
	@Test 
	public void AddNewViewTest() {
		Model model = new Model();
		AddNewController controller = new AddNewController(model , 0);
		AddNewView newView = new AddNewView(model, 0);
		
		String actual = newView.getTextField(0);
		String expected = "";
		
		assertEquals(expected, actual);
		
	}
	@Test 
	public void AutoTuningPanelUnknownPhoto() {
		Model model = new Model();
		AutoTuningPanelView testView = new AutoTuningPanelView(model);
		
		testView.setFoto("ABC.png");
		
		String expected = "there is no such photo";
		String input = outContent.toString();		
		String actual = input.substring(input.length() - expected.length()-2, input.length()-2);
		
		assertEquals(expected , actual);			
	}
	@Test 
	public void AutoTuningPanelNegativeXGiven() {
		Model model = new Model();
		AutoTuningPanelView testView = new AutoTuningPanelView(model);
		
		testView.setXY(-100);
		
		String expected = "x changed to 0 - cannot be negative";
		String input = outContent.toString();		
		String actual = input.substring(input.length() - expected.length()-2, input.length()-2);
		
		assertEquals(expected , actual);			
	}
	@Test 
	public void MenuViewUnknownPhoto() {
		Model model = new Model();
		MenuController controller = new MenuController(model, null);
		UppMenuController uppMenuController = new UppMenuController(model);
		UppMenuView uppMenuView = new UppMenuView(model);

		MenuView testView = new MenuView(model, uppMenuView);
		
		testView.initLabels("NONEPHOTO.png");
		
		String expected = "There is no such photo";
		String input = outContent.toString();		
		String actual = input.substring(input.length() - expected.length()-2, input.length()-2);
		
		assertEquals(expected , actual);			
	}
	@Test 
	public void ModeBaseViewNullDivision() {
		Model model = new Model();
		model.initializeTab();
 	    model.readTuningFromFile("NewFile.txt");
 	    model.setTuning("Guitar_Standard_E");
 	    
		ModeController modeController = new ModeController(model);
		UppMenuController uppMenuController = new UppMenuController(model);
		UppMenuView uppMenuView = new UppMenuView(model);
		
		model.setFreqAt(0, 0);
		ModeBaseView testView = new ModeBaseView(model, uppMenuView);
			
		String expected = "In ModeView.initButtons() button num = 0";
		String input = outContent.toString();		
		String actual = input.substring(input.length() - expected.length()-2, input.length()-2);
		
		assertEquals(expected , actual);			
	}
	@Test 
	public void ModelSetAndGetFreqAt() {
		Model model = new Model();
		model.initializeTab();
 	    model.readTuningFromFile("NewFile.txt");
 	    model.setTuning("Guitar_Standard_E");
 	    

 	    double expected = 0;
        model.setFreqAt(0, 0);
		double actual = model.getFreqOfNoteAt(0);
		double delta = 0;
		
		assertEquals(expected , actual,delta);			
	}
}
