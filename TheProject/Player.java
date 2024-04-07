import javafx.scene.paint.*;
import javafx.scene.canvas.*;

public class Player extends DrawableObject
{
	//takes in its position
   public Player(float x, float y)
   {
      super(x,y);
   }
   //draws itself at the passed in x and y.
   public void drawMe(float x, float y, GraphicsContext gc)
   {
      gc.setFill(Color.BLACK);
      gc.fillOval(x-14,y-14,27,27);
      gc.setFill(Color.GRAY);
      gc.fillOval(x-13,y-13,25,25);
      gc.setFill(Color.BLACK);
      gc.fillOval(x-7,y-7,13,13);
      gc.setFill(Color.CYAN);
      gc.fillOval(x-6,y-6,11,11);
   }
}
