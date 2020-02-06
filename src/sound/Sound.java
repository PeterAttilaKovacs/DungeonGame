/**
 * Music: "Dark Ambience Loop by Iwan Gabovitch qubodup.net"
 */

package sound;

import javax.sound.sampled.FloatControl;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import main.GameHandler;

public class Sound {

	//Variables - Handler and volume
	private GameHandler handler;
	private static int volume = 0;
	private static int effect = 10;

	//Variables - Music
	private String musicFile;
	
	//Variables - Sound Effects
	//player
	private String soundFile_boltgun;
	private String soundFile_plasmabolt;
	private String soundFile_plasmacharg;
	private String soundFile_outofammo;
	//private String soundFile_playerdeath; //TODO
	private String soundFile_playeridle;
	
	//enemy
	private String soundFile_enemydeath;
	private String soundFile_enemybolt;
	//private String soundFile_enemymove; //TODO
	
	//level
	private String soundFile_exitlevel;
	
	//volume control
	private float dBVolume, dBeffect;
	
	/**
	 * Sound constructor
	 */
	public Sound(GameHandler handler) {
		this.handler = handler;
		
		loadSoundFiles();
		init();

		playMusic.setOnEndOfMedia(()-> {
			playMusic.seek(Duration.ZERO);
			playMusic.play();
		});
	}

	//Tick for Volume Control
	public void tick() {

		//Volume UP
		if (handler.getVolUP()) 	{ 
			volume += 1;
			if (volume >= 100) { volume = 100; }
			//System.out.println("volup?: " + volume); //test-debug
		}
		
		//Volume DOWN
		if (handler.getVolDOWN()) 	{ 
			volume -= 1; 
			if (volume <= 0) { volume = 0; }
			//System.out.println("voldown?: " + volume); //test-debug
		}

		//Effect UP
		if (handler.getEffUP()) 	{ 
			effect += 1;
			if (effect>= 100) { effect = 100; }
			//System.out.println("effup?: " + effect); //test-debug
		}
				
		//Effect DOWN
		if (handler.getEffDOWN()) 	{ 
			effect -= 1; 
			if (effect <= 0) { effect = 0; }
			//System.out.println("effdown?: " + effect); //test-debug
		}
		
		//int to float for Music
		dBVolume = (float)(volume/100.0); 
		playMusic.setVolume(dBVolume);
		
		//TODO ths aint working well, finde another solution
		//int to float for Effect
		dBeffect = (float)(effect/100.0);
		if (handler.getEffUP()) {
			soundEffect_PlayerBolt.setVolume(dBeffect);
			soundEffect_EnemyDeath.setVolume(dBeffect);
			soundEffect_EnemyShoot.setVolume(dBeffect); 
			soundEffect_ExitLevel.setVolume(dBeffect);
		}
		else if (handler.getEffDOWN()) {
			soundEffect_PlayerBolt.setVolume(dBeffect);
			soundEffect_EnemyDeath.setVolume(dBeffect);
			soundEffect_EnemyShoot.setVolume(dBeffect); 
			soundEffect_ExitLevel.setVolume(dBeffect);
		}
	}
	
	//Variables - Music / Audio Player
	public Media music;
	public MediaPlayer playMusic;
	public AudioClip soundEffect_PlayerBolt;
	public AudioClip soundEffect_PlayerIdel;
	public AudioClip soundEffect_PlayerOutOfAmmo;
	public AudioClip soundEffect_EnemyDeath;
	public AudioClip soundEffect_EnemyShoot;
	public AudioClip soundEffect_ExitLevel;
	public AudioClip soundEffect_PlayerPlasma;
	public AudioClip soundEffect_PlayerPlasmacharg;
	
	//Loading sound files
	public void loadSoundFiles() {
		musicFile = "/sounds/ambient.mp3";
		soundFile_boltgun = "/sounds/boltgun.wav";
		soundFile_plasmabolt = "/sounds/plasmabolt.wav";
		soundFile_plasmacharg = "/sounds/plasmacharg.wav";
		soundFile_outofammo = "/sounds/outofammo.wav";
		soundFile_enemydeath = "/sounds/enemydeath1.wav";
		soundFile_enemybolt = "/sounds/enemypistol.wav";
		soundFile_exitlevel = "/sounds/exitdoor.wav";
		soundFile_playeridle = "/sounds/playeridel.wav";
	}
	
	//Play - inicialization method
	//TODO creat array of audioclips and call by reference...enum id maybe?...
	public void init() {
		try {
			music = new Media(getClass().getResource(musicFile).toExternalForm());
			playMusic = new MediaPlayer(music);
			
			soundEffect_PlayerBolt = new AudioClip(getClass().getResource(soundFile_boltgun).toString());
			soundEffect_PlayerOutOfAmmo = new AudioClip(getClass().getResource(soundFile_outofammo).toString());
			soundEffect_PlayerIdel = new AudioClip(getClass().getResource(soundFile_playeridle).toString());
			soundEffect_EnemyDeath = new AudioClip(getClass().getResource(soundFile_enemydeath).toString());
			soundEffect_EnemyShoot = new AudioClip(getClass().getResource(soundFile_enemybolt).toString());
			soundEffect_ExitLevel = new AudioClip(getClass().getResource(soundFile_exitlevel).toString());
			soundEffect_PlayerPlasma = new AudioClip(getClass().getResource(soundFile_plasmabolt).toString());
			soundEffect_PlayerPlasmacharg = new AudioClip(getClass().getResource(soundFile_plasmacharg).toString());
			
			//set Effect volume
//			soundEffect_PlayerBolt.setVolume(dBeffect);
//			soundEffect_EnemyDeath.setVolume(dBeffect);
//			soundEffect_EnemyShoot.setVolume(dBeffect); 
//			soundEffect_ExitLevel.setVolume(dBeffect);
		}
		catch (MediaException mEx) {
			System.err.print("Media player / Audio file did not load: " + mEx);
		}
	}
	
	//Volume getter
	public static int getVolume() {
		return volume;
	}
	
	//Effect getter
	public static int getEffect() {
		return effect;
	}
}
