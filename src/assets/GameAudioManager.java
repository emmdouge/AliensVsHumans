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
	private Clip clipForDemonAttack;
	private Clip clipForRifleshot;
	
	private AudioInputStream gameMusic;
	private AudioInputStream gunshot;
	private AudioInputStream rifleshot;
	private AudioInputStream demonAttack;
	
	private GameAudioManager()
	{
		
	}
	
	public static void init()
	{
		try 
		{
			instance.gameMusic = AudioSystem.getAudioInputStream(new File("assets/music/might.wav"));
			instance.gunshot = AudioSystem.getAudioInputStream(new File("assets/music/gunshot.wav"));
			instance.rifleshot = AudioSystem.getAudioInputStream(new File("assets/music/rifleshot.wav"));
			instance.demonAttack = AudioSystem.getAudioInputStream(new File("assets/music/demonAttack.wav"));
			
			instance.clipForGunshot = AudioSystem.getClip();
			instance.clipForGunshot.open(instance.gunshot);
			
			instance.clipForMusic = AudioSystem.getClip();
			instance.clipForMusic.open(instance.gameMusic);
			
			instance.clipForRifleshot = AudioSystem.getClip();
			instance.clipForRifleshot.open(instance.rifleshot);
			
			instance.clipForDemonAttack = AudioSystem.getClip();
			instance.clipForDemonAttack.open(instance.demonAttack);
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
	
	public void playRifleshot()
	{
		if(!clipForRifleshot.isRunning())
		{
			clipForRifleshot.setMicrosecondPosition(0);
			clipForRifleshot.start();
		}
	}
	
	public void playDemonAttack()
	{
		clipForDemonAttack.setMicrosecondPosition(0);
		clipForDemonAttack.start(); 
	}
}
