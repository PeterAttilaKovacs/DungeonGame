/**
 * 
 * Space Marine on Heretic Worlds
 * 
 * author: Galaktika
 * 
 * TODO: menu Options, sources packageing
 * TODO: Set Volume option
 */

package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import com.sun.javafx.application.PlatformImpl;

import enums.STATES;
import gui.KeyInput;
import gui.MouseInput;
import menu.HelpMenu;
import menu.MainMenu;
import objectplayer.PlayerHUD;
import sound.Sound;
import view.BufferedImageLoader;
import view.SpriteCuter;

public class Game extends Canvas implements Runnable {

	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = 5307833864394498312L;
	
	//Game state on start
	public static STATES GameStatus = STATES.Menu;
	
	//Menu variables
	public static int width = 1000;
	public static int height = 600;
	public static String title;
	
	//LevelLoader variables
	protected static int Level = 1;
	
	/**
	 * Main Game constructor
	 */
	public Game() {
		init();
		new Window(this);
		//loading first level
		new LevelLoader(handler, this, camera, hud, 
						imageCut_level, imageCut_player, imageCut_enemy, 
						audioPlayer, audioPlayer);
		start();
	}
	
	/**
	 * Init variables
	 */
	//Handler and Camera references
	private Handler handler;
	private Camera camera;
	private PlayerHUD hud;
	
	//Menu options references
	private MainMenu menu;
	private HelpMenu subHelpMenu;
	
	//Mouse references
	protected MouseInput mouse;
	
	//Animation and image-handler references
	private SpriteCuter imageCut_player;
	private SpriteCuter imageCut_level;
	private SpriteCuter imageCut_enemy;
	private BufferedImage playerSprite = null;
	private BufferedImage enemySprite = null;
	private BufferedImage level_layout = null;
	private BufferedImage level_ground = null;
	private BufferedImageLoader imageloader;
	
	//Sound references
	private static Sound musicPlayer;
	private static Sound audioPlayer;
	
	/**
	 * Inicialization
	 */
	public void init() {
		//Step 1
		PlatformImpl.startup(() -> {}); //for music player, javafx platform initialization!!
		//Step 2
		musicPlayer = new Sound();
		audioPlayer = new Sound();
		
		title = "SpaceMarine Game pA 1.0";
		handler = new Handler(); 
		camera = new Camera(0, 0, handler);
		this.addKeyListener(new KeyInput(handler));
		
		mouse = new MouseInput();
		
		menu = new MainMenu();
		subHelpMenu = new HelpMenu();
		
			//adding mouse listeners for menu
			this.addMouseMotionListener(mouse);
			this.addMouseListener(mouse);
		
		hud = new PlayerHUD(25, 25, null, imageCut_player);
		
		imageloader = new BufferedImageLoader();
			
			//Step 1
			level_layout = imageloader.loadImage("/spacecity2.png");
			playerSprite = imageloader.loadImage("/spm.png");
			enemySprite  = imageloader.loadImage("/enmheretic.png");
			//Step 2
			imageCut_level = new SpriteCuter(level_layout);
			imageCut_player = new SpriteCuter(playerSprite);
			imageCut_enemy = new SpriteCuter(enemySprite);
			level_ground = imageCut_level.grabImage(2, 2, 32, 32); //loading level ground for rendering
	}

	/**
	 * Thread variables
	 */
	private Thread myThread = null; //creating new Thread
	private final Object threadLock = new Object(); //lock referency
	private boolean canRun = false;
	
	//Staring new Thread
	public void start() {
		synchronized (threadLock) { 
            if (myThread instanceof Thread) { 
                if(!myThread.isAlive()) stop();
            }
        
            if (myThread == null) { //if Thread is null, then start new Thread
            
                //creating new thread with lambda expression
                myThread = new Thread(()-> {
                    try {
                        canRun = true;
                        while (canRun) {
                            this.run();
                            Thread.sleep(100);
                        }
                    }
                    
                    catch (InterruptedException ie) { 
                    	System.err.println("Thread start failed: " + ie); //basic exception catch
                    }
                    
                    finally {
                        myThread = null;
                    }    
                });
                myThread.start();
                //System.out.println(myThread); //Test-Debug only
            }
        }    
	}
	
	//Stoping already running Trehad
	public void stop() {
		synchronized(threadLock) {
            if (myThread instanceof Thread) {
                if(myThread.isAlive()) {
                    canRun = false;
                    try {
                        myThread.join(2000); //waiting 2 seconds for thread to stop
                    } 
                    
                    catch (InterruptedException ex) {
                    	System.err.println("Thread exception: " + ex); //basic exception catch
                    }
                
                    //is thread still alive?
                    if(myThread.isAlive()) {
                        myThread.interrupt(); //if thread dose not stop, then interrupt thread
                    }
                }    
            }
        } 
	}
	
	//Running Thread
	@Override
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		while(canRun){ //while thread canRun
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1) {
				tick(); //calling main tick 
				delta--;
			}
			
			render(); //calling main render
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
			}
		}
		stop(); //stoping thread in canRun
	}
	
	/**
	 * Main Tick method
	 */
	private void tick() {
		//main - menu tick
		if (GameStatus == STATES.Menu) {
			menu.tick();
		}	
		//menu - help tick
		else if (GameStatus == STATES.Help) {
			subHelpMenu.tick();
		}
		
		//game tick
		if (GameStatus == STATES.Play) {
			handler.tick();
			camera.tick();
		}	
	}
	
	/**
	 * Main Rendering method
	 */
	private void render() {
		
		/**
		 * Buffer Strategy
		 */
		BufferStrategy bufferStrategy = this.getBufferStrategy();
		if (bufferStrategy == null) { //singleton
			this.createBufferStrategy(3); //base settings: 3x buffering
			return;
		}
		
		Graphics graphics = bufferStrategy.getDrawGraphics();
		Graphics graphics2D = (Graphics2D) graphics;
			
			//Menu screen background rendering
			graphics.setColor(Color.orange);
			graphics.fillRect(0, 0, width, height);
		
		/**
		 * Game starts, if Game STATES is set to: Play
		 */	
		if (GameStatus == STATES.Play) {
			
			//starting music
			musicPlayer.playMusic.play();
			
			//removing mouselisteners from menu
			this.removeMouseMotionListener(mouse);
			this.removeMouseListener(mouse);
			
			/// Camera-view rendering -START- ///
			graphics2D.translate(-camera.getX(), -camera.getY());
		
				//rendering ground
				for (int xx = 0; xx < 30*72; xx += 32) {			
					for (int yy = 0; yy < 30*72; yy += 32) {		
						graphics.drawImage(level_ground, xx, yy, null);
					}
				}
			
			
				handler.render(graphics);
			
			graphics2D.translate(camera.getX(), camera.getY());
			/// Camera-view rendering -STOP- ///
		
			/**
			 * Player HUD rendering
			 * must be under g2D, to stay in static position
			 */
			hud.render(graphics);
		}
		
		/**
		 * Menu function is called at starting a new Game
		 */
		if (GameStatus == STATES.Menu) {
			menu.render(graphics);
		}
		else if (GameStatus == STATES.Help) {
			subHelpMenu.render(graphics);
		}
		
		bufferStrategy.show();
		graphics.dispose();
	}
	
	/**
	 * Main method, starting new Game
	 */
	public static void main(String[] args) {
		new Game();
	}
}