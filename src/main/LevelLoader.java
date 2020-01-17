package main;

import java.awt.image.BufferedImage;

import enums.ID;
import objects.AmmoCrate;
import objects.Flag;
import objects.PlayerHUD;
import objects.SpaceMarine;
import objects.WallBlock;
import objects.EnemyKhornet;
import view.BufferedImageLoader;
import view.SpriteCuter;

public class LevelLoader {

	private Handler handler;
	public PlayerHUD hud;
	public SpriteCuter imageCut;
	public Camera camera;
	public BufferedImage level_1 = null, level_2 = null, level_3 = null;
	private Game game;
		
	/**
	 * LevelLoader constructor
	 * @param handler - Handler class
	 * @param game - Game class
	 * @param camera - Camera class
	 * @param hud - PlayerHUD class
	 * @param imageCut - SpriteCuter class
	 */
	//public LevelLoader(Handler handler, SpriteCuter cut, Game game, Camera camera){
	public LevelLoader(Handler handler, Game game, Camera camera, PlayerHUD hud, SpriteCuter imageCut){
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		this.camera = camera;
		this.imageCut = imageCut;
		
		BufferedImageLoader loader = new BufferedImageLoader();
			level_1 = loader.loadImage("/kronosworld.png"); 	//lvl 1
			level_2 = loader.loadImage("/davinworld.png"); 		//lvl 2
			level_3 = loader.loadImage("/cadiaworld.png"); 		//lvl 3
			//level_4 = loader.loadImage("/...png"); 	//lvl 4
			//level_5 = loader.loadImage("/...png"); 	//lvl 5
			//etc...
				
		nextLevel();
	}
		
	/**
	 * Level rendering
	 * @param levelMap - loaded image for rendering the level
	 */
	public void loadMap(BufferedImage levelMap){
		int width = levelMap.getWidth();
		int height = levelMap.getHeight();
				
		for (int xx = 0; xx < width; xx++){
			for (int yy = 0; yy < height; yy++){
				int pixel = levelMap.getRGB(xx, yy);
				int red	= (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
						
				//Walls (red)
				if (red == 255 && green == 0 && blue == 0){
					handler.addObject(new WallBlock(xx*32, yy*32, ID.WallBlock, imageCut));
				}	
						
				//SpaceMarine (blue)
				if (red == 0 && green == 0 && blue == 255){
					handler.addObject(new SpaceMarine(xx*32, yy*32, ID.SpaceMarine, imageCut, handler, camera, game, hud, this));
				}
					
				//Khornet (green - enemy)
				if (red == 0 && green == 255 && blue == 0){
					handler.addObject(new EnemyKhornet(xx*32, yy*32, ID.Khornet, imageCut, handler, game, hud));
				}
					
				//AmmoCrate (cyan)
				if (red == 0 && green == 255 && blue == 255){ //cyen color: red: 0, green: 255, blue: 255
					handler.addObject(new AmmoCrate(xx*32, yy*32, ID.AmmoCrate, imageCut, handler));
				}
					
				//Exitpoint (yellow)
				if (red == 255 && green == 255 && blue == 0){ //yellow color: red: 255, green: 255, blue: 0 
					handler.addObject(new Flag(xx*32, yy*32, ID.Flag, imageCut)); 
				}
			}
		}
	}
		
	//Loading next level
	public void nextLevel(){
		handler.clearLevel();
			
		switch(Game.Level){
			
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
		if (Game.Level == 3){
			Game.Level = 1;
		}
		else {
			Game.Level++;
		}
	}
}
