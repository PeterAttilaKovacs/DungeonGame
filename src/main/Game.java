/**
 * 
 * Space Marine on Demon Worlds
 * 
 * author: Galaktika
 * 
 * TODO: animated sprites, music, sound
 * 
 */

package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import enums.STATES;
import gui.KeyInput;
import gui.MouseInput;
import menu.HelpMenu;
import menu.MainMenu;
import objects.PlayerHUD;
import view.BufferedImageLoader;
import view.SpriteCuter;

public class Game extends Canvas implements Runnable {

	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = 5307833864394498312L;
	
	//Game state on start
	public static STATES GameStatus = STATES.Menu;
	
	//Game - Window variables
	public static int width = 1000;
	public static int height = 600;
	public static String title;
	public static int Level = 1;
	
	//Game constructor
	public Game() {
		init();
		new Window(width, height, title, this); //creating new window
		new LevelLoader(handler, this, camera, hud, imageCut); //loading first level
		start();
	}
	
	//Handler and Camera references
	private Handler handler;
	private Camera camera;
	private PlayerHUD hud;
	
	//Menu options references
	private MainMenu menu;
	private HelpMenu subHelpMenu;
	
	//Mouse references
	public MouseInput mouse;
	
	//Animation and image-handler references
	private SpriteCuter imageCut;
	private BufferedImageLoader imageloader;
	private BufferedImage trySprite;
	
	/**
	 * Inicialization
	 */
	public void init() {
		title = "SpaceMarine Game pA 0.1";
		handler = new Handler(); 
		camera = new Camera(0, 0, handler);
		this.addKeyListener(new KeyInput(handler));
		
		mouse = new MouseInput();
		
		menu = new MainMenu();
		subHelpMenu = new HelpMenu();
		
			//adding mouse listeners for menu
			this.addMouseMotionListener(mouse);
			this.addMouseListener(mouse);
		
		hud = new PlayerHUD(25, 25, null, imageCut);
		
		imageloader = new BufferedImageLoader();
			
			trySprite = imageloader.loadImage("/spm.png");
			imageCut = new SpriteCuter(trySprite);
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
            if (myThread instanceof Thread){ 
                if(!myThread.isAlive()) stop();
            }
        
            if (myThread==null){ //if Thread is null, then start new Thread
            
                //creating new thread with lambda expression
                myThread = new Thread(()->{
                    try{
                        canRun = true;
                        while (canRun) {
                            this.run();
                            myThread.sleep(100);
                        }
                    }
                    
                    catch (InterruptedException ie) { 
                    	System.err.println("Hiba a szal futatasaban: " + ie); //basic exception catch
                    }
                    
                    finally {
                        myThread = null;
                    }    
                });
                myThread.start();
            }
        }    
	}
	
	//Stoping already running Trehad
	public void stop() {
		synchronized(threadLock) {
            if (myThread instanceof Thread){
                if(myThread.isAlive()) {
                    canRun = false;
                    try {
                        myThread.join(2000); //waiting 2 seconds for thread to stop
                    } 
                    
                    catch (InterruptedException ex) {
                    	System.err.println("Hiba a szal leallitasaban: " + ex); //basic exception catch
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
			
			while(delta >= 1){
				tick(); //calling main tick 
				delta--;
			}
			
			render(); //calling main render
			
			if (System.currentTimeMillis() - timer > 1000){
				timer += 1000;
			}
		}
		stop();
	}
	
	/**
	 * Refres (tick) method
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
	 * Graphics rendering
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
			
			//red floor
			graphics.setColor(Color.magenta);
			graphics.fillRect(0, 0, width, height);
		
		/**
		 * Game starts, if Game STATES is set to: Play
		 */	
		if (GameStatus == STATES.Play) {
			
			//removing mouselisteners from menu
			this.removeMouseMotionListener(mouse);
			this.removeMouseListener(mouse);
			
			///camera-view rendering -START-
			graphics2D.translate(-camera.getX(), -camera.getY());
		
				handler.render(graphics);
			
			graphics2D.translate(camera.getX(), camera.getY());
			///camera-view rendering -STOP-
		
			/**
			 * Player HUD rendering
			 * 
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