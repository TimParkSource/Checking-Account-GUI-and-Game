package a6;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.JPanel;

public class CardPanel extends JPanel
{

   private final int WIDTH = 760, HEIGHT = 500;
   private final int DELAY = 50, MOVEDELAY = 7, IMAGE_SIZE = 35;

   private static String[] flipList = { 
                                        "flip1.png","flip2.png","flip3.png","flip4.png","flip5.png",
                                        "flip6.png","flip7.png","cardback.png", "cardback.png"
                                      }; 
   private static String[] cardList = { 
                                        "1.png","2.png","3.png","4.png","5.png"
                                      };
   private static String money;
   private static ImageIcon image, cardback, table;
   private static boolean soundFlag = true;
   private static boolean gotWager = false;

   private static int imageNum = 8;
   private static int currentNum;
   private static int newNum;
   private static double wager;
   private static double originalwager;
  
   private Timer timer;
   private Timer mover;
   private int x, y, moveX, moveY;
   
   public CardPanel()
   {
         String url = "file:music.wav";
         try
             {
                      LoopSound.myLoop(url);
             }
         catch (InterruptedException ie) 
             {
                      System.out.println(ie);
             }

         addKeyListener (new ButtonListener());
         
         x = 441; 
        
         timer = new Timer(DELAY, new FlipListener());
         mover = new Timer(MOVEDELAY, new MoveListener());
         setPreferredSize (new Dimension(WIDTH, HEIGHT));
         setBackground (Color.white);
         setFocusable(true);   
         currentNum = (int)(Math.random()*5);
         image = new ImageIcon (cardList[currentNum]);    
         cardback = new ImageIcon ("cardback.png");
         table = new ImageIcon ("table.png");
         repaint(); 
         
         if (gotWager == false)
         {
            wager = A6.getWager();
            originalwager = wager;
            gotWager = true;
         }
   }

   public void paintComponent (Graphics page)
   {
      super.paintComponent (page);
      table.paintIcon(this, page, 0, 0);
      cardback.paintIcon (this, page, 441, 100);
      cardback.paintIcon (this, page, x, 100);
      if (image !=null)
      image.paintIcon (this, page, 90, 100);
      
      page.setColor (Color.white);
      NumberFormat fmt = new DecimalFormat("#0.00");
      page.drawString ("Credit: $" + fmt.format(wager), 5, 15);
   }
   
   private class ButtonListener implements KeyListener
   {


      public void keyPressed (KeyEvent event)
      {
         
         newNum = (int)(Math.random()*5);  
         
         if (event.getKeyCode() == KeyEvent.VK_UP)
         {
               if(currentNum < newNum)
               {
                   wager = wager + originalwager;
                   money = "file:cash.wav";
               }
               else if (currentNum > newNum)
               {
                   wager = wager - originalwager;
                   money = "file:lose.wav";
               }
               else
               {   
                   wager = wager + originalwager/2;
                   money = "file:bet.wav";
               }
               currentNum = newNum;
         
               mover.start();
               timer.start();
          
         }
         if (event.getKeyCode() == KeyEvent.VK_DOWN)
         {
              if(currentNum > newNum)
              {
                   wager = wager + originalwager;
                   money = "file:cash.wav";
              }
               
              else if (currentNum < newNum)
              {
                   wager = wager - originalwager;
                   money = "file:lose.wav";
              }
              else
              {
                   wager = wager + originalwager/2;
                   money = "file:bet.wav";
              }
                      
              currentNum = newNum;
         
              mover.start();
              timer.start();
               
             
         }


      }
         public void keyTyped (KeyEvent event) {}
         public void keyReleased (KeyEvent event) {}
         

   }   
   
   private class FlipListener implements ActionListener
   {

      public void actionPerformed (ActionEvent event)
      {
         
         String url = "file:flip.wav";

         if (soundFlag)
         {
             try
             {
                      PlaySound.myPlay(url);
                      soundFlag = false;
             }
             catch (InterruptedException ie) {
                System.out.println(ie);
                }
         }
         
         if (imageNum >= 0)
           {
             
              
              image = new ImageIcon (flipList[imageNum]);             
              repaint();
              imageNum--;
           }  
         else
         {
             timer.stop();

             image = new ImageIcon (cardList[newNum]);
             repaint(); 
             imageNum = 8;
             soundFlag = true;
             try
               {
                      PlaySound.myPlay(money);
               }
               catch (InterruptedException ie) 
               {
                      System.out.println(ie);
               }
         }
          
       }
    }
    
   private class MoveListener implements ActionListener
   {

      public void actionPerformed (ActionEvent event)
      {
         x -= 20;
         if (x > 90)       
         {
              repaint();
         }
         else
         {
             x = 441;
             mover.stop();
         }
       }
    }
   
   public static double getCredit()
    {
       double credit = wager - originalwager;
       return credit;
    }
       public static void gameEnd()
    {
       gotWager = false;
    }
    
}
