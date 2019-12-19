package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable{

	/**
	 * Szeria verzio szam
	 */
	private static final long serialVersionUID = 5307833864394498312L;
	
	//Jatek-ablak valtozoi
	public static int width = 1000;
	public static int height = 600;
	private String title;
	
	public Game(){
		new Window(width, height, title, this);
		init();
	}
	
	//Handler referencia
	private Handler handler;
			
	//Camera referencia
	private Camera camera;
	
	public void init(){
		title = "Dungeon Game pA 0.1";
		handler = new Handler(); 
		camera = new Camera(0, 0, handler);
	}

	/**
	 * Szal valtozoi
	 */
	private Thread myThread = null; //uj futasi szal letrehozasa
	private final Object threadLock = new Object(); //zarolasi referencia objektum, nem modosithato
	private boolean canRun = false;
	
	//uj futtatasi szal inditasa
	public void start(){
		synchronized (threadLock){ //olyan objektum szinkronizalasa ami biztos, hogy letezik!
            if (myThread instanceof Thread){ //van-e szal?
                if(!myThread.isAlive()) stop(); //ha a szal nem el, akkor stop
            }
        
            if (myThread==null){ //ha nincs szal, akkor uj szal inditasa
            
                //uj szal letrehozasa es takariktasa (ez a profi megoldas!)
                myThread = new Thread(()->{ //lambda kifejezeses fuggveny
                    try{
                        canRun = true;
                        while (canRun){
                            this.run();
                            myThread.sleep(100);
                        }
                    }
                    
                    catch (InterruptedException ie){ 
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
	public void stop(){
		synchronized(threadLock){ //olyan objektum szinkronizalasa ami biztos, hogy letezik!
            if (myThread instanceof Thread){ //van-e szal?
                if(myThread.isAlive()){
                    canRun = false;
                    try {
                        myThread.join(2000); //2 masodperces varas
                    } 
                    
                    catch (InterruptedException ex) {
                    	System.err.println("Hiba a szal leallitasaban: " + ex);
                    }
                
                    //vizsgalat, hogy el-e meg a szal
                    if(myThread.isAlive()){
                        myThread.interrupt(); //ha 3 mp utan nem all meg, akkor jelzes a szalnak a megallasra
                    }
                }    
            }
        } 
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Grafika renderelese
	 */
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) { //ez csak egyszer fog meghivodni a jatek elejen
			this.createBufferStrategy(3); //alapbeallitas: 3x elotoltes - tulnovelese pl. 5-8-15x-re lassitja a jatekot
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics g2D = (Graphics2D) g;
		
		//alap piros talaj
		g.setColor(Color.red);
		g.fillRect(0, 0, width, height);
		
		///kamera szerinti nezet renderelese -START-
		g2D.translate(-camera.getX(), -camera.getY());
		
		handler.render(g);
		
		/// -STOP-
		bs.show();
		g.dispose();
	}
	
	public static void main(String[] args){
		new Game();
	}
}
