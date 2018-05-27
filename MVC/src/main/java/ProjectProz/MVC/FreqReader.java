package ProjectProz.MVC;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import org.apache.commons.math3.complex.Complex;  
import org.apache.commons.math3.transform.DftNormalization;  
import org.apache.commons.math3.transform.FastFourierTransformer;  
import org.apache.commons.math3.transform.TransformType;  

/**
 * Frequency reader based on FFT
 * and complex Fourier series
 * 
 */
public class FreqReader implements Runnable{  

    TargetDataLine  microphone;  

    final int       audioFrames= 8192;   

    final float     sampleRate= 8000.0f;  
    final int       bitsPerRecord= 16;  
    final int       channels= 1;  
    final boolean   bigEndian = true;  
    final boolean   signed= true;  

    byte            byteData[];     
    double          doubleData[];     
    AudioFormat     format;  
    FastFourierTransformer transformer;  
    Model model;
    int index;
    double f;
    /*
     * creates byte tab for reading microphone signal
     * and created doubleData tab for storing frequency
     * Initializes microphone
     */
    public void setIndex(int i) {
    	index = i;
    }
    public FreqReader (Model model) {  

    	this.model = model;
        byteData= new byte[audioFrames * 2];  //two bytes per audio frame, 16 bits  

        //doubleData= new double[audioFrames * 2];  // real & imaginary  
        doubleData= new double[audioFrames];  // only real for apache  

        transformer = new FastFourierTransformer(DftNormalization.STANDARD);  

        System.out.print("Microphone initialization\n");  
        format = new AudioFormat(sampleRate, bitsPerRecord, channels, signed, bigEndian);  
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format); // format is an AudioFormat object  

        if (!AudioSystem.isLineSupported(info)) {  
            System.err.print("isLineSupported failed");  
            //System.exit(1);  
        }  

        try {  
             microphone = (TargetDataLine) AudioSystem.getLine(info);  
             microphone.open(format);  
          //   System.out.print("Microphone opened with format: "+format.toString()+"\n");  
             microphone.start();  
        }catch(Exception ex){  
           // System.out.println("Microphone failed: "+ex.getMessage());  
           // System.exit(1);  
        }  
    }  
    /*
     *  Reads data from microphne to byteData
     */
    public int readPcm(){  
        int numBytesRead=   
                microphone.read(byteData, 0, byteData.length);  
        if(numBytesRead!=byteData.length){  
          //  System.out.println("Warning: read less bytes than buffer size");  
           // System.exit(1);  
        }  
        return numBytesRead;  
    }  
    
    /**
     * Moves data stored in byteData to double data
     * counting it's double value
     */

    public void byteToDouble(){  
        ByteBuffer buf= ByteBuffer.wrap(byteData);  
        buf.order(ByteOrder.BIG_ENDIAN);  
        int i=0;   

        while(buf.remaining()>2){  
            short s = buf.getShort();  
            doubleData[ i ] = (new Short(s)).doubleValue();  
            ++i;  
        }  
        //System.out.println("Parsed "+i+" doubles from "+byteData.length+" bytes");  
    }  

    /**
     * 
     * At first creates complex tab and two double values
     * one for real, and the second one for imaginary part
     * This implementation of Fourier transform is based
     * on summing peaks and then diving this by length tab
     * in order to calculate frequency
     * @return 
     * @return calculated frequency
     */
    public double findFrequency(){  
    	
  
        double frequency;  
        Complex[] cmplx= transformer.transform(doubleData, TransformType.FORWARD);  
        double real;  
        double im;  
        double mag[] = new double[cmplx.length];  

        for(int i = 0; i < cmplx.length; i++){  
            real = cmplx[i].getReal();  
            im = cmplx[i].getImaginary();  
            mag[i] = Math.sqrt((real * real) + (im*im));  
        }  

        double peak = -1.0;  
        int index=-1;  
      //  System.out.println(cmplx.length);
        for(int i = 0; i < cmplx.length; i++){  
            if(peak < mag[i]){ 
            	//System.out.println(index);
                index=i;  
                peak= mag[i];  
            }  
        }  
        frequency = (sampleRate * index) / audioFrames;  
       
       // microphone.close();
    //    System.out.print("Index: "+index+", Frequency: "+frequency+"\n");  
        f = frequency;
        return frequency;
   }
    /**
     * shows frequency on JPanel
     */
    public void showFrequency() {
    		
    	double diff, x;
		double chosenFreq;
    	 // System.out.println(model.getFreqOfNoteAt(index));
		    chosenFreq = model.getFreqOfNoteAt(index);
		    diff = chosenFreq - f;
		    //System.out.println("it is f in show freq method : " + f);		
		    //System.out.println("diff is" + diff);
		    if(Math.abs(diff) <= 150) {
		    	x = diff;
		    } else {
		    	if(diff < 0) {
		    		x = -150;
		    	} else {
		    		x = 150;
		    	}
		    }
		    model.drawLine(150-(int)x);
		    model.setFreqInfo(f);
    }
	public void run() {

		//System.out.println("Running");
		readPcm();
		//System.out.println("Data Read");
		byteToDouble();
		//System.out.println("Byte is changed to double");
		//System.out.println(findFrequency());
		findFrequency();
		showFrequency();
		
	}
}