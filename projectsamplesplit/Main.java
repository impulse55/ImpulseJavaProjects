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
   FlowPane fp;
   Player thePlayer = new Player(300f,300f);
   Canvas theCanvas = new Canvas(600,600);
   Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 12);
   String score = "";
   String highScore = "";

   public void start(Stage stage)
   {
      fp = new FlowPane();
      
      fp.getChildren().add(theCanvas);
      gc = theCanvas.getGraphicsContext2D();
      drawBackground(300,300,gc);
      thePlayer.draw(300,300,gc,true);
      
      Scene scene = new Scene(fp, 600, 600);
      stage.setScene(scene);
      stage.setTitle("Project :)");
      stage.show();
      
      fp.setOnKeyPressed(new KeyListenerDown());
      fp.setOnKeyReleased(new KeyListenerUp());
      
      AnimationHandler ta = new AnimationHandler();
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
         if(up && forceY < 5)
            forceY-=0.1;
         if(down && forceY > -5)
            forceY+=0.1;
         if(left && forceX < 5)
            forceX-=0.1;
         if(right && forceX > -5)
            forceX+=0.1;
            
         if(!up && forceY < -0.25)
            forceY+=0.025;
         if(!down && forceY > 0.25)
            forceY-=0.025;
         if(!left && forceX < -0.25)
            forceX+=0.025;
         if(!right && forceX > 0.25)
            forceX-=0.025;
            
         if(!up && !down && !left && !right && (forceX > -0.25 && forceX < 0.25))
            forceX=0;
         if(!up && !down && !left && !right && (forceY > -0.25 && forceY < 0.25))
            forceY=0;
         
         x+=1*forceX;
         y+=1*forceY;
         gc.clearRect(0,0,600,600);
         thePlayer.setX(x);
         thePlayer.setY(y);
         drawBackground(thePlayer.getX(),thePlayer.getY(),gc);
         score = "Score is: "+((long)Math.sqrt((300-thePlayer.getX())*(300-thePlayer.getX())+(300-thePlayer.getY())*(300-thePlayer.getY())))/100;
         gc.setFont(font);
         gc.fillText(score, 15, 30);
         gc.fillText("High Score is: 0", 15, 55);
         thePlayer.draw(300,300,gc,true);
      }
   }

   public static void main(String[] args)
   {
      launch(args);
   }
}

