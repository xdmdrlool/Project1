package clemnico;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

	public static Mixer mixer;
	public  Clip clip;
	

	@SuppressWarnings("deprecation")
	public Sound(String path) {
		
		Mixer.Info[] mixInfos=AudioSystem.getMixerInfo();
//		for(Mixer.Info infos : mixInfos) {
//			System.out.println(infos.getName()+" "+infos.getDescription());}
		
		
		
		mixer=AudioSystem.getMixer(mixInfos[1]);
		DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
		
		try {clip =(Clip)mixer.getLine(dataInfo);} 
		catch (LineUnavailableException e) {e.printStackTrace();}
		
		
		File f=new File("resources/"+path);
		
		URL soundURL=null;
		try {soundURL = f.toURL();} catch (MalformedURLException e1) {e1.printStackTrace();}
	
		
		try {AudioInputStream audioStream= AudioSystem.getAudioInputStream(soundURL);
		try {clip.open(audioStream);} catch (LineUnavailableException e) {e.printStackTrace();}} 
		catch (UnsupportedAudioFileException | IOException e) {System.out.println("Son Introuvable : "+path);}
		
		
	}
	
	public void play() {
	      
		if (clip.isRunning()) {clip.stop();}   
		clip.setFramePosition(0); 
		clip.start();    
		
	      }
	
	 public void stop() {
		clip.stop();
	  }

}
