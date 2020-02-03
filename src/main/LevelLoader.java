package main;

import java.awt.image.BufferedImage;

import enums.ID;
import objectplayer.PlayerHUD;
import objectplayer.SpaceMarine;
import objectsenemy.EnemyHeretic;
import objectsenemy.EnemyMutant;
import objectslevel.AmmoCrate;
import objectslevel.Flag;
import objectslevel.MediPack;
import objectslevel.WallBlock;
import sound.Sound;
import view.BufferedImageLoader;
import view.SpriteCuter;

public class LevelLoader {

	private GameHandler handler;
	public PlayerHUD hud;
	public SpriteCuter imageCut_level;
	public SpriteCuter imageCut_enemy;
	public SpriteCuter imageCut_player;
	public Camera camera;
	public BufferedImage level_1 = null, level_2 = null, level_3 = null;
	private Game game;
	private Sound effect_Player;
	private Sound effect_Enemy;
		
	/**
	 * LevelLoader constructor
	 * @param handler - Handler class
	 * @param game - Game class
	 * @param camera - Camera class
	 * @param hud - PlayerHUD class
	 * @param imageCut - SpriteCuter class, imageCut for player & enemy rendering
	 * @param imageCut_level - SpriteCuter class, imageCut for level rendering
	 * @param Sound effect_Player & effect_Enemy - Sound class, for sound effects
	 */
	//public LevelLoader(Handler handler, SpriteCuter cut, Game game, Camera camera){
	public LevelLoader(GameHandler handler, Game game, Camera camera, PlayerHUD hud, 
						SpriteCuter imageCut_level, SpriteCuter imageCut_player, SpriteCuter imageCut_enemy, 
						Sound effect_Player, Sound effect_Enemy) {
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		this.camera = camera;
		this.imageCut_level = imageCut_level;
		this.imageCut_player = imageCut_player;
		this.imageCut_enemy = imageCut_enemy;
		this.effect_Player = effect_Player;
		this.effect_Enemy = effect_Enemy;
		
		BufferedImageLoader loader = new BufferedImageLoader();
			level_1 = loader.loadImage("/maps/underhivelvl1.png"); 	//level 1
			level_2 = loader.loadImage("/maps/underhivelvl2.png"); 	//level 2
			level_3 = loader.loadImage("/maps/underhivelvl3.png"); 	//level 3
			//level_4 = loader.loadImage("/...png"); 	//lvl 4
			//level_5 = loader.loadImage("/...png"); 	//lvl 5
			//etc...	
			
		nextLevel();
	}
		
	/**
	 * Level rendering
	 * @param levelMap - loaded image for rendering the level
	 */
	public void loadMap(BufferedImage levelMap) {
		int width = levelMap.getWidth();
		int height = levelMap.getHeight();
				
		for (int xx = 0; xx < width; xx++) {
			for (int yy = 0; yy < height; yy++) {
				int pixel = levelMap.getRGB(xx, yy);
				int red	= (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				//Walls (red)
				if (red == 255 && green == 0 && blue == 0) {
					handler.addObject(new WallBlock(xx*32, yy*32, ID.WallBlock, imageCut_level));
				}	
						
				//SpaceMarine (blue - player)
				if (red == 0 && green == 0 && blue == 255) {
					handler.addObject(new SpaceMarine(xx*32, yy*32, ID.SpaceMarine, imageCut_player, handler, camera, game, hud, effect_Player, this));
				}
					
				//EnemyHeretic (green - enemy1)
				if (red == 0 && green == 255 && blue == 0) {
					handler.addObject(new EnemyHeretic(xx*32, yy*32, ID.EnemyHeretic, imageCut_enemy, handler, game, hud, effect_Enemy));
				}
				
				//EnemyMutant (gray - enemy2)
				if (red == 192 && green == 192 && blue == 192) {
					handler.addObject(new EnemyMutant(xx*32, yy*32, ID.EnemyMutant, imageCut_enemy, handler, game, hud, effect_Enemy));
				}
				
					
				//AmmoCrate (cyan)
				if (red == 0 && green == 255 && blue == 255) { //cyen color: red: 0, green: 255, blue: 255
					handler.addObject(new AmmoCrate(xx*32, yy*32, ID.AmmoCrate, imageCut_level, handler));
				}
					
				//Exitpoint (yellow)
				if (red == 255 && green == 255 && blue == 0) { //yellow color: red: 255, green: 255, blue: 0 
					handler.addObject(new Flag(xx*32, yy*32, ID.Flag, imageCut_level)); 
				}
				
				//MediPack (white)
				if (red == 255 && green == 255 && blue == 255) { //white color: red: 255, green: 255, blue: 255 
					handler.addObject(new MediPack(xx*32, yy*32, ID.MediPack, imageCut_level, handler)); 
				}
			}
		}
	}
		
	//Loading next level
	public void nextLevel() {
		handler.clearLevel();
		
		switch(Game.Level) {
			
		//Lvl 1
		case 1:
			loadMap(level_1);
			camera.findPlayer();
			break;
			
		//Lvl 2
		case 2: 
			loadMap(level_2);
			camera.findPlayer();
			break;
			
		//Lvl 3
		case 3: 
			loadMap(level_3);
			camera.findPlayer();
			break;
		}
			
		/**
		 * Counter for level, after level 3, counter is set to null,
		 * level loading starts from level 1.
		 */
		if (Game.Level == 3) {
			Game.Level = 1;
		}
		else {
			Game.Level++;
		}
	}
}
