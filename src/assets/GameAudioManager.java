package assets;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class GameAudioManager {

	public static GameAudioManager instance = new GameAudioManager();
	private Clip clipForMusic;
	private Clip clipForGunshot;
	private AudioInputStream gameMusic;
	private AudioInputStream gunshot;
	private GameAudioManager()
	{
		
	}
	
	public static void init()
	{
		try 
		{
			instance.gameMusic = AudioSystem.getAudioInputStream(new File("assets/music/might.wav"));
			instance.gunshot = AudioSystem.getAudioInputStream(new File("assets/music/gunshot.wav"));
			
			instance.clipForGunshot = AudioSystem.getClip();
			instance.clipForGunshot.open(instance.gunshot);
			
			instance.clipForMusic = AudioSystem.getClip();
			instance.clipForMusic.open(instance.gameMusic);
		} 
		catch(UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playGameMusic()
	{
		FloatControl gainControl = (FloatControl) clipForMusic.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(-10.0f);
		clipForMusic.loop(Clip.LOOP_CONTINUOUSLY); 
		 
	}
	
	public void playGunshot()
	{
		clipForGunshot.setMicrosecondPosition(0);
		clipForGunshot.start(); 
	}
}
