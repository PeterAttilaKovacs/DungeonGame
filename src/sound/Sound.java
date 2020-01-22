/**
 * Music: "Dark Ambience Loop by Iwan Gabovitch qubodup.net"
 */

package sound;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Sound {

	//Variables - Music
	private String musicFile;
	
	//Variables - Sound Effects
	//player
	private String soundFile_boltgun;
	private String soundFile_outofammo;
	//private String soundFile_playerdeath; //pedding
	private String soundFile_playeridle; //pedding
	//private String soundFile_lowammo; //pedding
	
	//enemy
	private String soundFile_enemydeath;
	private String soundFile_enemybolt;
	//private String soundFile_enemymove; //pedding
	
	//level
	private String soundFile_exitlevel;
	
	//Variables - Music / Audio Player
	public Media music;
	public MediaPlayer playMusic;
	public AudioClip soundEffect_PlayerShoot;
	public AudioClip soundEffect_PlayerIdel;
	public AudioClip soundEffect_PlayerOutOfAmmo;
	public AudioClip soundEffect_EnemyDeath;
	public AudioClip soundEffect_EnemyShoot;
	public AudioClip soundEffect_ExitLevel;
	
	/**
	 * Sound constructor
	 */
	public Sound() {
		musicFile = "/ambient.mp3";
		soundFile_boltgun = "/boltgun.wav";
		soundFile_outofammo = "/outofammo.wav";
		soundFile_enemydeath = "/enemydeath1.wav";
		soundFile_enemybolt = "/enemypistol.wav";
		soundFile_exitlevel = "/exitdoor.wav";
		soundFile_playeridle = "/playeridel.wav";
		
		init();
		
		//looping same mp3 file and setting volume
		playMusic.setVolume(0.1);
		playMusic.setOnEndOfMedia(()-> {
			playMusic.seek(Duration.ZERO);
			playMusic.play();
		});
	}
	
	//Play - inicialization method
	public void init() {
		try {
			music = new Media(getClass().getResource(musicFile).toExternalForm());
			playMusic = new MediaPlayer(music);
			
			soundEffect_PlayerShoot = new AudioClip(getClass().getResource(soundFile_boltgun).toString());
			soundEffect_PlayerOutOfAmmo = new AudioClip(getClass().getResource(soundFile_outofammo).toString());
			soundEffect_PlayerIdel = new AudioClip(getClass().getResource(soundFile_playeridle).toString());
			soundEffect_EnemyDeath = new AudioClip(getClass().getResource(soundFile_enemydeath).toString());
			soundEffect_EnemyShoot = new AudioClip(getClass().getResource(soundFile_enemybolt).toString());
			soundEffect_ExitLevel = new AudioClip(getClass().getResource(soundFile_exitlevel).toString());
			
			//set volume
			soundEffect_PlayerShoot.setVolume(0.2);
			soundEffect_EnemyDeath.setVolume(0.3);
			soundEffect_EnemyShoot.setVolume(0.2); 
			soundEffect_ExitLevel.setVolume(1.0);
		}
		catch (MediaException mex) {
			System.err.print("Media player / Audio file did not load: " + mex);
		}
	}
}
