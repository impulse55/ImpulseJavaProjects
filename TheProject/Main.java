import java.net.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.*;
import javafx.scene.paint.*;
import javafx.geometry.*;
import javafx.scene.image.*;

import java.io.*;

import java.util.*;
import java.text.*;
import java.io.*;
import java.lang.*;
import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.animation.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import java.net.*;
import javafx.geometry.*;

public class Main extends Application
{
   //Initializes all of the necessary objects and variables to be used throughout the game
   FlowPane fp;
   Player thePlayer = new Player(300f,300f);
   ArrayList<Mine> mines = new ArrayList<Mine>();
   Canvas theCanvas = new Canvas(600,600);
   Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 12);
   Random rand = new Random();
   AnimationHandler ta = new AnimationHandler();
   Scanner fscan;
   FileOutputStream fos;
   PrintWriter pw;
   int highScore = 0;
   int score = 0;
   double rate = 3;

   public void start(Stage stage)
   {
      //Scans the High Score file and sets the high score variable to what is inside
      //of the file
      try
      {
         fscan = new Scanner(new File("HighScore.txt"));
         highScore = fscan.nextInt();
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
      
      fp = new FlowPane();
      
      //Adds the game canvas to the root FlowPane
      fp.getChildren().add(theCanvas);
      gc = theCanvas.getGraphicsContext2D();
      //Draws the initial background and player
      drawBackground(300,300,gc);
      thePlayer.draw(300,300,gc,true);
      
      //Creates the scene and stage
      Scene scene = new Scene(fp, 600, 600);
      stage.setScene(scene);
      stage.setTitle("Trent's Awesome Project :)");
      stage.show();
      
      //Binds the KeyListenerDown and KeyListenerUp classes to the FlowPane
      fp.setOnKeyPressed(new KeyListenerDown());
      fp.setOnKeyReleased(new KeyListenerUp());
      
      //Starts the AnimationHandler
      ta.start();
      
      fp.requestFocus();
   }
   
   GraphicsContext gc;
   
   Image background = new Image("stars.png");
   Image overlay = new Image("starsoverlay.png");
   Random backgroundRand = new Random();
   //this piece of code doesn't need to be modified
   public void drawBackground(float playerx, float playery, GraphicsContext gc)
   {
	  //re-scale player position to make the background move slower. 
      playerx*=.1;
      playery*=.1;
   
	//figuring out the tile's position.
      float x = (playerx) / 400;
      float y = (playery) / 400;
      
      int xi = (int) x;
      int yi = (int) y;
      
	  //draw a certain amount of the tiled images
      for(int i=xi-3;i<xi+3;i++)
      {
         for(int j=yi-3;j<yi+3;j++)
         {
            gc.drawImage(background,-playerx+i*400,-playery+j*400);
         }
      }
      
	  //below repeats with an overlay image
      playerx*=2f;
      playery*=2f;
   
      x = (playerx) / 400;
      y = (playery) / 400;
      
      xi = (int) x;
      yi = (int) y;
      
      for(int i=xi-3;i<xi+3;i++)
      {
         for(int j=yi-3;j<yi+3;j++)
         {
            gc.drawImage(overlay,-playerx+i*400,-playery+j*400);
         }
      }
   }
   
   float x=300,y=300;
   float forceX = 0;
   float forceY = 0;
   boolean up,down,left,right;
   public class KeyListenerDown implements EventHandler<KeyEvent>  
   {
      public void handle(KeyEvent event) 
      {
         //Checks whether W, A, S, or D has been pressed down
         if (event.getCode() == KeyCode.A) 
         {
            left = true;
         }
         if (event.getCode() == KeyCode.W)  
         {
            up = true;
         }
         if (event.getCode() == KeyCode.S)  
         {
            down = true;
         }
         if (event.getCode() == KeyCode.D)  
         {
            right = true;
         }
      }
   }
   
   public class KeyListenerUp implements EventHandler<KeyEvent>  
   {
      public void handle(KeyEvent event) 
      {
         //Checks whether W, A, S, or D has been let go of
         if (event.getCode() == KeyCode.A) 
         {
            left = false;
         }
         if (event.getCode() == KeyCode.W)  
         {
            up = false;
         }
         if (event.getCode() == KeyCode.S)  
         {
            down = false;
         }
         if (event.getCode() == KeyCode.D)  
         {
            right = false;
         }
      }
   }
   
   public class AnimationHandler extends AnimationTimer
   {
      public void handle(long currentTimeInNanoSeconds) 
      {
         //Increases how much force should be applied on the x or
         //y axis depending on which key has been pressed
         if(up && forceY > -5)
            forceY-=0.1;
         if(down && forceY < 5)
            forceY+=0.1;
         if(left && forceX > -5)
            forceX-=0.1;
         if(right && forceX < 5)
            forceX+=0.1;
         
         //Decreases how much force should be applied on the x or
         //y axis depending on which key has been let go of
         if(!up && forceY < -0.25)
            forceY+=0.025;
         if(!down && forceY > 0.25)
            forceY-=0.025;
         if(!left && forceX < -0.25)
            forceX+=0.025;
         if(!right && forceX > 0.25)
            forceX-=0.025;
         
         //If none of the keys are held down and the forces are less than the
         //given value, set the forces to 0
         if(!up && !down && !left && !right && (forceX > -0.25 && forceX < 0.25))
            forceX=0;
         if(!up && !down && !left && !right && (forceY > -0.25 && forceY < 0.25))
            forceY=0;
         
         //Applies the forces to the x and y values
         x+=1*forceX;
         y+=1*forceY;
         
         //Clears the canvas for it to be redrawn
         gc.clearRect(0,0,600,600);
         //Sets the player's x and y values
         thePlayer.setX(x);
         thePlayer.setY(y);
         //Sets the score to the distance between the player and the origin 
         score = (int)Math.sqrt(Math.pow(300-thePlayer.getX(),2)+Math.pow(300-thePlayer.getY(),2));
         
         //Mine Generator
         for(int i=0;i<4;i++)
         {
            //Gets the player's current gridx and gridy values
            int cgridx = ((int)thePlayer.getX())/100;
            int cgridy = ((int)thePlayer.getY())/100;
            
            //Places the mines around the player 4 spots away from the player
            //on the x-axis and 5 spots away on the y-axis
            switch(i)
            {
               case 0:
               {
                  cgridx-=4;
                  cgridy-=5;
                  addMine(cgridx,cgridy,true);
                  break;
               }
               case 1:
               {
                  cgridx-=5;
                  cgridy+=4;
                  addMine(cgridx,cgridy,false);
                  break;
               }
               case 2:
               {
                  cgridx-=4;
                  cgridy+=5;
                  addMine(cgridx,cgridy,true);
                  break;
               }
               case 3:
               {
                  cgridx+=5;
                  cgridy+=4;
                  addMine(cgridx,cgridy,false);
                  break;
               }
            }
         }
         
         //Draws the background, player, and mines
         drawBackground(thePlayer.getX(),thePlayer.getY(),gc);
         thePlayer.draw(300,300,gc,true);
         drawMines();
         
         //Mine Detection
         for(int i=0;i<mines.size();i++)
         {
            //If the distance between a given mine and the player is less than
            //the given value, it stops the game, removes the player and the mine,
            //and sets the high score if the current score is greater than the 
            //previous high score
            if(mines.get(i).distance(thePlayer)<=20)
            {
               ta.stop();
               gc.clearRect(0,0,600,600);
               drawBackground(thePlayer.getX(),thePlayer.getY(),gc);
               mines.remove(i);
               drawMines();
               try
               {
                  if(score > highScore)
                  {
                     fos = new FileOutputStream("HighScore.txt", false);
                     pw = new PrintWriter(fos);
                     pw.println(""+score);
                     pw.close();
                  }
               }
               catch(Exception e)
               {
                  e.printStackTrace();
               }
            }
            //If a given mine is 800 units away from the player, delete it
            else if(mines.get(i).distance(thePlayer)>=800)
            {
               mines.remove(i);
            }
         }
         
         //Draws the text for the score and the high score
         gc.setFont(font);
         gc.setFill(Color.GRAY);
         gc.fillText("Score is: "+ score, 15, 30);
         gc.fillText("High Score is: " + highScore, 15, 55);
      }
   }
   
   //Adds mines based on the given gridx and gridy
   public void addMine(int x, int y, boolean isX)
   {
      for(int j=0;j<9;j++)
      {
         double randN = rand.nextDouble(99)+1;
         
         //If the random number generated is less than the current rate
         //it adds a mine
         if(randN <= rate)
         {
            //If the mine is to be drawn along the x-axis, it increments the x value and
            //vice versa if not
            if(isX)
            {
               mines.add(new Mine((x+j)*100+rand.nextInt(100),(y)*100+rand.nextInt(100)));
            }
            else
            {
               mines.add(new Mine((x)*100+rand.nextInt(100),(y-j)*100+rand.nextInt(100)));
            }
         }
         //Increases the rate of which mines are generated the more the game goes on
         if(rate <= 6)
         {
            rate+=0.001;
         }
      }
   }
   
   public void drawMines()
   {
      for(int i=0;i<mines.size();i++)
      {
         mines.get(i).advanceColor();
         mines.get(i).draw(thePlayer.getX(),thePlayer.getY(),gc,false);
      }
   }

   public static void main(String[] args)
   {
      launch(args);
   }
}
