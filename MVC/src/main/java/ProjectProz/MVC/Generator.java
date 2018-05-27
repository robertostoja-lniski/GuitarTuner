package ProjectProz.MVC;

import javax.sound.sampled.*;

/**
 * 
 * Generates sound and frequency given
 */
public class Generator  {
	
	static double freq;
	float sampleRate = 44100; 
    double a = 0.2;
    
    Generator(double f) {
    	freq = f;
    }
    /**
     * At first amplitude is created
     * then to every field in tab certain value
     * of sin function is set. Then double tab is 
     * changed to bit tab, which is writted to output line
     */
    void play() {
    	 double twoPiF = 2*Math.PI*freq;
    	 double[] buffer = new double[44100]; 
    	    for (int sample = 0; sample < buffer.length; sample++) {
    	      double time = sample / sampleRate;
    	      buffer[sample] = a * Math.sin(twoPiF*time);
    	    }
    	    
    	    final byte[] byteBuffer = new byte[buffer.length];
    	    int idx = 0;
    	    for (int i = 0; i < byteBuffer.length; ) {
    	      int x = (int) (buffer[idx++]*127);
    	      byteBuffer[i++] = (byte) x;
    	    }
    	      
    	    boolean bigEndian = false;
    	    boolean signed = true;
    	    int bits = 8;
    	    int channels = 1;
    	    AudioFormat format = new AudioFormat(sampleRate,bits,channels,signed,bigEndian);
    	     
    	    try {
    	    	DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        	    SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
        	    line.open(format);
        	    line.start();
        	    line.write(byteBuffer, 0, byteBuffer.length);
        	    line.close();
        	  
    	    } catch(LineUnavailableException e) {
    	    	System.out.println("error");
    	    }
    	    
    }
   
  
}