import javafx.scene.paint.*;
import javafx.scene.canvas.*;

public class Mine extends DrawableObject
{
   private double colorValue;
	//takes in its position
   public Mine(float x, float y)
   {
      super(x,y);
      colorValue = 0;
   }
   
   int way = 1;
   public void advanceColor()
   {
      colorValue += 0.1f * way;
      
      if(colorValue >= 1)
      {
         colorValue = 1;
         way = - 1;
      }
      if(colorValue <= 0)
      {
         colorValue = 0;
         way = 1;
      }
   }
   
   //draws itself at the passed in x and y.
   public void drawMe(float x, float y, GraphicsContext gc)
   {
      double g = 0*colorValue + 1*(1 - colorValue);
      double b = 0*colorValue + 1*(1 - colorValue);
      
      Color c = new Color(1,g,b,1);
      gc.setFill(Color.BLACK);
      gc.fillOval(x-5,y-5,10,10);
      gc.setFill(c);
      gc.fillOval(x-4,y-4,8,8);
   }
}