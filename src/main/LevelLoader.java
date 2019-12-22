package main;

import java.awt.image.BufferedImage;

import animation.BufferedImageLoader;
import animation.SpriteCuter;
import enums.ID;
import objects.AmmoCrate;
import objects.Flag;
import objects.PlayerHUD;
import objects.SpaceMarine;
import objects.WallBlock;
import objects.tesztEnemy;

public class LevelLoader {

	//LevelLoader variables
	private Handler handler;
	public PlayerHUD hud;
	public SpriteCuter cut;
	//public SpriteCuter cut2;
	public Camera camera;
	public BufferedImage level_1 = null, level_2 = null, level_3 = null;
	private Game game;
		
	//LevelLoader constructor
	//public LevelLoader(Handler handler, SpriteCuter cut, Game game, Camera camera){
	public LevelLoader(Handler handler, Game game, Camera camera, PlayerHUD hud){
		this.handler = handler;
		//this.cut = cut;
		this.hud = hud;
		this.game = game;
		this.camera = camera;
		
		BufferedImageLoader loader = new BufferedImageLoader();
			level_1 = loader.loadImage("/kronosworld.png"); 	//lvl 1
			level_2 = loader.loadImage("/davinworld.png"); 		//lvl 2
			level_3 = loader.loadImage("/cadiaworld.png"); 		//lvl 3
			//level_4 = loader.loadImage("/...png"); 	//lvl 4
			//level_5 = loader.loadImage("/...png"); 	//lvl 5
			//stb
				
		nextLevel();
	}
		
	//Level rendering
	public void loadMap(BufferedImage levelMap){
		int w = levelMap.getWidth();
		int h = levelMap.getHeight();
				
		for (int xx = 0; xx < w; xx++){
			for (int yy = 0; yy < h; yy++){
				int pixel = levelMap.getRGB(xx, yy);
				int red	= (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
						
				//Fal (piros)
				if (red == 255 && green == 0 && blue == 0){
					handler.addObject(new WallBlock(xx*32, yy*32, ID.WallBlock, cut));
				}	
						
				//SpaceMarine (kek)
				if (red == 0 && green == 0 && blue == 255){
					handler.addObject(new SpaceMarine(xx*32, yy*32, ID.SpaceMarine, cut, handler, camera, game, hud, this));
				}
					
				//Khornet (zold)
				if (red == 0 && green == 255 && blue == 0){
					handler.addObject(new tesztEnemy(xx*32, yy*32, ID.Khornet, cut, handler, game, hud));
				}
					
				//Loszereslada (cian)
				if (red == 0 && green == 255 && blue == 255){ //cyen color: red: 0, green: 255, blue: 255
					handler.addObject(new AmmoCrate(xx*32, yy*32, ID.AmmoCrate, cut, handler));
				}
					
				//Kilepesi pont (sarga)
				if (red == 255 && green == 255 && blue == 0){ //yellow color: red: 255, green: 255, blue: 0 
					handler.addObject(new Flag(xx*32, yy*32, ID.Flag, cut)); 
				}
			}
		}
	}
		
	//Palya tovabbtoltes
	public void nextLevel(){
		handler.clearLevel();
			
		switch(Game.Level){
			
		//Lvl 1
		case 1:
			loadMap(level_1);
			camera.fndPlayer();
			break;
			
		//Lvl 2
		case 2: 
			loadMap(level_2);
			camera.fndPlayer();
			break;
			
		//Lvl 3
		case 3: 
			loadMap(level_3);
			camera.fndPlayer();
			break;
		}
			
		/**
		 * Szamlalo palyahoz, harmadik palya utan visszatoltja a szamlalot,
		 * jatek palyatoltes ujraindul Lvl 1 - tol.
		 */
		if (Game.Level == 3){
			Game.Level = 1;
		}
		else {
			Game.Level++;
		}
	}
}
