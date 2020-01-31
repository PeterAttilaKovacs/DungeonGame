/**
 * Music: "Dark Ambience Loop by Iwan Gabovitch qubodup.net"
 */

package sound;

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

	//Variables - Music
	private String musicFile;
	
	//Variables - Sound Effects
	//player
	private String soundFile_boltgun;
	private String soundFile_plasmabolt;
	private String soundFile_plasmacharg;
	private String soundFile_outofammo;
	//private String soundFile_playerdeath; //pedding
	private String soundFile_playeridle;
	//private String soundFile_lowammo; //pedding
	
	//enemy
	private String soundFile_enemydeath;
	private String soundFile_enemybolt;
	//private String soundFile_enemymove; //pedding
	
	//level
	private String soundFile_exitlevel;
	
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
		if (handler.volUP()) 	{ 
			volume += 1;
			if (volume >= 100) { volume = 100; }
			System.out.println("volup?: " + volume);
		}
		
		//Volume DOWN
		if (handler.volDOWN()) 	{ 
			volume -= 1; 
			if (volume <= 0) { volume = 0; }
			System.out.println("voldown?: " + volume);
		}

		//int to float
		float dB = (float)(volume/100.0); 
		playMusic.setVolume(dB);
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
		musicFile = "/ambient.mp3";
		soundFile_boltgun = "/boltgun.wav";
		soundFile_plasmabolt = "/plasmabolt.wav";
		soundFile_plasmacharg = "/plasmacharg.wav";
		soundFile_outofammo = "/outofammo.wav";
		soundFile_enemydeath = "/enemydeath1.wav";
		soundFile_enemybolt = "/enemypistol.wav";
		soundFile_exitlevel = "/exitdoor.wav";
		soundFile_playeridle = "/playeridel.wav";
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
			
			//set volume
			soundEffect_PlayerBolt.setVolume(0.2);
			soundEffect_EnemyDeath.setVolume(0.3);
			soundEffect_EnemyShoot.setVolume(0.2); 
			soundEffect_ExitLevel.setVolume(1.0);
		}
		catch (MediaException mEx) {
			System.err.print("Media player / Audio file did not load: " + mEx);
		}
	}
	
	//Volume getter
	public static int getVolume() {
		return volume;
	}
}
