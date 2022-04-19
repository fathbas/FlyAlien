package com.fatihb.sur;

import static com.badlogic.gdx.Gdx.graphics;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {

	SpriteBatch spriteBatch;
	Texture back;
	Texture bird;
	Texture enemy1;
	Texture enemy2;
	Texture enemy3;
	Random random;
	Circle birdCircle;
	BitmapFont font;
	BitmapFont font2;
	BitmapFont font3;
	//Preferences prefs = Gdx.app.getPreferences("My Preferences");

	//ShapeRenderer shapeRenderer;

	int score=0;
	int bestScore = 0;
	int scoredEnemy=0;
	float birdX;
	float birdY;
	int numberOfEnemies = 4;
	float[] enemyX = new float[numberOfEnemies];
	float[] enemyOffset1 = new float[numberOfEnemies];
	float[] enemyOffset2 = new float[numberOfEnemies];
	float[] enemyOffset3 = new float[numberOfEnemies];
	Circle[] enemyCircle1;
	Circle[] enemyCircle2;
	Circle[] enemyCircle3;

	float distance;
	int gameState = 0;
	float velocity=0;
	float enemyVelocity=7;
	float grata = 0.8f;

	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		back = new Texture("background.png");
		bird = new Texture("bird.png");
		enemy1 = new Texture("enemy.png");
		enemy2 = new Texture("enemy.png");
		enemy3 = new Texture("enemy.png");

		distance = graphics.getWidth() >> 1;
		random = new Random();

		birdX= (float) (graphics.getWidth()/7.0);
		birdY= graphics.getHeight() >> 1;

		birdCircle = new Circle();
		enemyCircle1=new Circle[numberOfEnemies];
		enemyCircle2=new Circle[numberOfEnemies];
		enemyCircle3=new Circle[numberOfEnemies];

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(5);
		font2 = new BitmapFont();
		font2.setColor(Color.BLACK);
		font2.getData().setScale(7);
		font3 = new BitmapFont();
		font3.setColor(Color.BLACK);
		font3.getData().setScale(7);



		//shapeRenderer = new ShapeRenderer();

		for (int i=0; i<numberOfEnemies; i++){
			enemyOffset1[i]=(random.nextFloat()-0.5f)*(graphics.getHeight()-200);
			enemyOffset2[i]=(random.nextFloat()-0.5f)*(graphics.getHeight()-200);
			enemyOffset3[i]=(random.nextFloat()-0.5f)*(graphics.getHeight()-200);

			enemyX[i]= graphics.getWidth()- (enemy1.getWidth() >> 1) +i*distance;

			enemyCircle1[i]= new Circle();
			enemyCircle2[i]= new Circle();
			enemyCircle3[i]= new Circle();
		}
	}



	@Override
	public void render () {
		spriteBatch.begin();
		spriteBatch.draw(back,0,0, graphics.getWidth(), graphics.getHeight());
		//bestScore = prefs.getInteger("best_score",0);


		if (gameState==1){
			if (enemyX[scoredEnemy]<birdX){
				score+=1;
				enemyVelocity+=0.2000000000000;
				if (scoredEnemy<numberOfEnemies-1){
					scoredEnemy+=1;
				}else {
					scoredEnemy=0;
				}
			}

			if (Gdx.input.justTouched()){
				velocity=-15;
			}

			for (int i=0; i<numberOfEnemies; i++){

				if (enemyX[i]<0){
					enemyX[i]=enemyX[i]+numberOfEnemies*distance;

					enemyOffset1[i]=(random.nextFloat()-0.5f)*(graphics.getHeight()-200);
					enemyOffset2[i]=(random.nextFloat()-0.5f)*(graphics.getHeight()-200);
					enemyOffset3[i]=(random.nextFloat()-0.5f)*(graphics.getHeight()-200);
				}else {
					enemyX[i]=enemyX[i]-enemyVelocity;
				}
				enemyX[i]=enemyX[i]-enemyVelocity;
				spriteBatch.draw(enemy1,enemyX[i], (graphics.getHeight() >> 1) -enemyOffset1[i], (float) (graphics.getWidth()/14.0), (float) (graphics.getHeight()/10.0));
				spriteBatch.draw(enemy2,enemyX[i], (graphics.getHeight() >> 1) -enemyOffset2[i], (float) (graphics.getWidth()/14.0), (float) (graphics.getHeight()/10.0));
				spriteBatch.draw(enemy3,enemyX[i], (graphics.getHeight() >> 1) -enemyOffset3[i], (float) (graphics.getWidth()/14.0), (float) (graphics.getHeight()/10.0));
				enemyCircle1[i] = new Circle((float) (enemyX[i]+ graphics.getWidth()/28.0), (float) ((graphics.getHeight() >> 1) -enemyOffset1[i]+ graphics.getHeight()/20.0), (float) (graphics.getWidth()/35.0));
				enemyCircle2[i] = new Circle((float) (enemyX[i]+ graphics.getWidth()/28.0), (float) ((graphics.getHeight() >> 1) -enemyOffset2[i]+ graphics.getHeight()/20.0), (float) (graphics.getWidth()/35.0));

				int dist1 = (int) ((int)Math.pow(enemyCircle1[i].x - enemyCircle2[i].x,2) + Math.pow(enemyCircle1[i].y - enemyCircle2[i].y,2));
				int radSum1 = (int) Math.pow(enemyCircle1[i].radius + enemyCircle2[i].radius,2);
				if (radSum1>=dist1){
					enemyOffset2[i]=(random.nextFloat()-0.5f)*(graphics.getHeight()-200);
					enemyCircle2[i] = new Circle((float) (enemyX[i]+ graphics.getWidth()/28.0), (float) ((graphics.getHeight() >> 1) -enemyOffset2[i]+ graphics.getHeight()/20.0), (float) (graphics.getWidth()/35.0));
				}
				enemyCircle3[i] = new Circle((float) (enemyX[i]+ graphics.getWidth()/28.0), (float) (graphics.getHeight()/2-enemyOffset3[i]+ graphics.getHeight()/20.0), (float) (graphics.getWidth()/35.0));

				int dist2 = (int) ((int)Math.pow(enemyCircle1[i].x - enemyCircle3[i].x,2) + Math.pow(enemyCircle1[i].y - enemyCircle3[i].y,2));
				int radSum2 = (int) Math.pow(enemyCircle1[i].radius + enemyCircle3[i].radius,2);
				int dist3 = (int) ((int)Math.pow(enemyCircle3[i].x - enemyCircle2[i].x,2) + Math.pow(enemyCircle3[i].y - enemyCircle2[i].y,2));
				int radSum3 = (int) Math.pow(enemyCircle3[i].radius + enemyCircle2[i].radius,2);
				if (radSum2>=dist2||radSum3>=dist3){
					enemyOffset3[i]=(random.nextFloat()-0.5f)*(graphics.getHeight()-200);
					enemyCircle3[i] = new Circle((float) (enemyX[i]+ graphics.getWidth()/28.0), (float) ((graphics.getHeight() >> 1) -enemyOffset3[i]+ graphics.getHeight()/20.0), (float) (graphics.getWidth()/28.0));
				}
			}
			if (birdY>0 && birdY< graphics.getHeight()- graphics.getHeight()/20.0) {
				velocity = velocity+ grata;
				birdY = birdY - velocity;
			}else {
				gameState=2;
			}
		}else if(gameState==0) {
			if (Gdx.input.justTouched()){
				gameState = 1;
			}
		}else if (gameState==2){
			font2.draw(spriteBatch,"GAME OVER!!", (float) (graphics.getWidth()/3.0), (float) (graphics.getHeight()/1.7));
			font3.draw(spriteBatch,String.valueOf(score), (float) (graphics.getWidth()/2.1), (float) (graphics.getHeight()/2.2));
			if (Gdx.input.justTouched()){
				gameState = 1;
				birdY= graphics.getHeight() >> 1;

				for (int i=0; i<numberOfEnemies; i++){
					enemyOffset1[i]=(random.nextFloat()-0.5f)*(graphics.getHeight()-200);
					enemyOffset2[i]=(random.nextFloat()-0.5f)*(graphics.getHeight()-200);
					enemyOffset3[i]=(random.nextFloat()-0.5f)*(graphics.getHeight()-200);

					enemyX[i]= graphics.getWidth()- (enemy1.getWidth() >> 1) +i*distance;

					enemyCircle1[i]= new Circle();
					enemyCircle2[i]= new Circle();
					enemyCircle3[i]= new Circle();
				}
				velocity=0;
				scoredEnemy=0;
				if (score>bestScore){
					bestScore=score;
					//prefs.putInteger("best_score", bestScore);
				}
				score=0;
				enemyVelocity=7;
			}
		}
		spriteBatch.draw(bird,birdX,birdY, (float) (graphics.getWidth()/22.0), (float) (graphics.getHeight()/18.0));
		font.draw(spriteBatch,String.valueOf(score), (float) (graphics.getWidth()/20.0), (float) (graphics.getHeight()/8.95));
		font.draw(spriteBatch,String.valueOf(bestScore), (float) (graphics.getWidth()/20.0), (float) (graphics.getHeight()/1.05));
		spriteBatch.end();
		birdCircle.set((float) (birdX+ graphics.getWidth()/45.0), (float) (birdY+ graphics.getHeight()/40.0), (float) (graphics.getWidth()/55.0));
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);

		for (int i=0; i<numberOfEnemies; i++){
			//shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth()/28,Gdx.graphics.getHeight()/2-enemyOffset1[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/28);
			//shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth()/28,Gdx.graphics.getHeight()/2-enemyOffset2[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/28);
			//shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth()/28,Gdx.graphics.getHeight()/2-enemyOffset3[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/28);

			if (Intersector.overlaps(birdCircle,enemyCircle1[i])||Intersector.overlaps(birdCircle,enemyCircle2[i])||Intersector.overlaps(birdCircle,enemyCircle3[i])){
				gameState=2;
			}
		}
		//shapeRenderer.end();
	}
}
