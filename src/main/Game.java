/**
 * 
 * Space Marine on Demon Worlds
 * 
 * author: Galaktika
 * 
 * TODO: animalt grafika - spritok, zene, hangok, menu, menu/jatek allapot
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

public class Game extends Canvas implements Runnable{

	/**
	 * Szeria verzioszam
	 */
	private static final long serialVersionUID = 5307833864394498312L;
	
	//Jatek allapot valtozoja
	public static STATES GameStatus = STATES.Menu;
	
	//Jatek-ablak valtozoi
	public static int width = 1000;
	public static int height = 600;
	public static String title;
	public static int Level = 1;
	
	//Game konstruktora
	public Game() {
		init();
		new Window(width, height, title, this); //uj megjelenitesi ablak hivasa
		new LevelLoader(handler, this, camera, hud, imageCut); //uj palya betoltese
		start();
	}
	
	//Handler es Camera referencia
	private Handler handler;
	private Camera camera;
	private PlayerHUD hud;
	
	//Menupontok referencia
	private MainMenu menu;
	private HelpMenu subHelpMenu;
	
	//Eger referencia
	public MouseInput mouse;
	
	//Animacio es kepvagas referencia
	private SpriteCuter imageCut;
	private BufferedImageLoader imageloader;
	private BufferedImage trySprite;
	
	/**
	 * Inicializalas (valtozok inicializalasa)
	 */
	public void init() {
		title = "SpaceMarine Game pA 0.1";
		handler = new Handler(); 
		camera = new Camera(0, 0, handler);
		this.addKeyListener(new KeyInput(handler));
		
		mouse = new MouseInput();
		
		menu = new MainMenu();
		subHelpMenu = new HelpMenu();
		
			//eger figyeles felvetele menure
			this.addMouseMotionListener(mouse);
			this.addMouseListener(mouse);
		
		hud = new PlayerHUD(25, 25, null, imageCut);
		
		imageloader = new BufferedImageLoader();
			
			trySprite = imageloader.loadImage("/spm.png");
			imageCut = new SpriteCuter(trySprite);
	}

	/**
	 * Szal valtozoi
	 */
	private Thread myThread = null; //uj futasi szal letrehozasa
	private final Object threadLock = new Object(); //zarolasi referencia objektum, nem modosithato
	private boolean canRun = false;
	
	//uj futtatasi szal inditasa
	public void start() {
		synchronized (threadLock) { //olyan objektum szinkronizalasa ami biztos, hogy letezik!
            if (myThread instanceof Thread){ //van-e szal?
                if(!myThread.isAlive()) stop(); //ha a szal nem el, akkor stop
            }
        
            if (myThread==null){ //ha nincs szal, akkor uj szal inditasa
            
                //uj szal letrehozasa es takariktasa (ez a profi megoldas!)
                myThread = new Thread(()->{ //lambda kifejezeses fuggveny
                    try{
                        canRun = true;
                        while (canRun) {
                            this.run();
                            myThread.sleep(100);
                        }
                    }
                    
                    catch (InterruptedException ie) { 
                    	System.err.println("Hiba a szal futatasaban: " + ie); //nagyon alap hibaelfogas
                    }
                    
                    finally {
                        myThread = null;
                    }    
                });
                myThread.start();
            }
        }    
	}
	
	//futasban levo szal leallitasa
	public void stop() {
		synchronized(threadLock) { //olyan objektum szinkronizalasa ami biztos, hogy letezik!
            if (myThread instanceof Thread){ //van-e szal?
                if(myThread.isAlive()) {
                    canRun = false;
                    try {
                        myThread.join(2000); //2 masodperces varas
                    } 
                    
                    catch (InterruptedException ex) {
                    	System.err.println("Hiba a szal leallitasaban: " + ex);
                    }
                
                    //vizsgalat, hogy el-e meg a szal
                    if(myThread.isAlive()) {
                        myThread.interrupt(); //ha 3 mp utan nem all meg, akkor jelzes a szalnak a megallasra
                    }
                }    
            }
        } 
	}
	
	//futatatasban levo szal
	@Override
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		while(canRun){ //amig a szal fut
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1){
				tick(); //tick hivasa
				delta--;
			}
			
			render(); //rendereles hivasa
			
			if (System.currentTimeMillis() - timer > 1000){
				timer += 1000;
			}
		}
		stop();
	}
	
	/**
	 * Frissites metodus
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
		
		
		//jatek tick
		if (GameStatus == STATES.Play) {
			handler.tick();
			camera.tick();
		}	
	}
	
	/**
	 * Grafika renderelese
	 */
	private void render() {
		
		/**
		 * Elotoltesi strategia
		 */
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) { //ez csak egyszer fog meghivodni a jatek elejen
			this.createBufferStrategy(3); //alapbeallitas: 3x elotoltes - tulnovelese pl. 5-8-15x-re lassitja a jatekot
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics g2D = (Graphics2D) g;
			
			//alap piros talaj
			g.setColor(Color.magenta);
			g.fillRect(0, 0, width, height);
		
		/**
		 * Jatek indul, ha a jatekstatusza: jatek
		 */	
		if (GameStatus == STATES.Play) {
			
			//eger figyles levetele a menurol
			this.removeMouseMotionListener(mouse);
			this.removeMouseListener(mouse);
			
			///kamera szerinti nezet renderelese -START-
			g2D.translate(-camera.getX(), -camera.getY());
		
				handler.render(g);
			
			g2D.translate(camera.getX(), camera.getY());
			///kamera szerinti nezet renderelese -STOP-
		
			/**
			 * Jatekos HUD renderelese
			 * 
			 * g2D alatt, mivel ez statikusan kell, hogy egy pozicioba legyen mindig
			 */
			hud.render(g);
		}
		
		/**
		 * Menu funkcio inditasa uj jatek inditasakor
		 */
		if (GameStatus == STATES.Menu) {
			menu.render(g);
		}
		else if (GameStatus == STATES.Help) {
			subHelpMenu.render(g);
		}
		
		bs.show();
		g.dispose();
	}
	
	/**
	 * Main metodus, uj jatek hivasa
	 */
	public static void main(String[] args) {
		new Game();
	}
}