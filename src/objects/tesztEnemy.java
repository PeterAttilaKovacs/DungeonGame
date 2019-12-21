package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import animation.SpriteCuter;
import enums.ID;
import main.Game;
import main.Handler;

public class tesztEnemy extends BaseObject{


	private Handler handler;
	private Game game;
	private Hud hud;
	
	public tesztEnemy(float x, float y, ID id, SpriteCuter imageCut, Handler handler, Game game, Hud hud) {
		super(x, y, id, imageCut);
		this.handler = handler;
		this.game = game;
		this.hud = hud;
	}

	@Override
	public void tick() {
		//TODO AI
		for (int i = 0; i < handler.object.size(); i++){
			BaseObject tempTESZT = handler.object.get(i);
			
			if (tempTESZT.getId() == ID.BolterRound){
				if (getBounds().intersects(tempTESZT.getBounds())){
					handler.removeObject(tempTESZT); //lovedek torlese
					handler.removeObject(this); //tesztEnemy torlese
					hud.MarineScore += 10;
					
				}
			}
		}
	}

	private int width = 32;
	private int height = 32;
	Shape circle = new Ellipse2D.Double(x-85, y-85, width*6, height*6); //teszt
	
	@Override
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g; //teszt
		
		g.setColor(Color.blue);
		g.fillRect((int)x, (int)y, width, height);
		g2.draw(circle);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}
	
	//teszteleshez
	public Ellipse2D getAttack() {
		return new Ellipse2D.Double(x-85, y-85, width*6, height*6);
	}
}
