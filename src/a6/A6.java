package a6;

// Timothy Park
// Assignment 6

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.awt.Font;
import java.io.*;
import javax.swing.*;
import java.util.Vector;


public class A6

{
    public static double initialBalance;
    public static String acctName;
    public static double transAmt;
    public static int index = -1;  // not zero so that we can use index++ in addAcc
    public static int tCode;
    public static int tCount;   
    public static int checkNum;
    public static double currentServiceCharge = 0;
    public static boolean hasrun = true;
    public static boolean hassaved = true;
    public static boolean hasacc = false;
    
    static Transaction t;  
    static Account a;
    static Check c;
    public static String filename = "C:\\student\\account.dat";
    public static COptionsFrame frame;
    public static Vector<CheckingAccount> dataStore; 
    public static JTextArea ta;
    
   public static void main (String[] args)
   {
      
      String balancestr;
      
      t = new Transaction(tCount, tCode, transAmt);
      a = new Account(acctName,initialBalance);
      c = new Check( tCount,tCode,transAmt,checkNum);
      
      dataStore = new Vector<CheckingAccount>();

      frame = new COptionsFrame("Checking Account Menu");

      ta = new JTextArea(10,50);
      frame.getContentPane().add(ta);
      frame.pack();
      frame.setVisible(true);
      
   }

   public static void inputTrans()
   {  
       CheckingAccount datum;
       String message, balancestr, name, line;
       double finalbalance, balance;
       frame.setVisible(false);
       
       if (hasacc == false)
           addAcc();

       datum = dataStore.elementAt(index);      
       
           tCode = getTransCode();
           
           if( tCode == 1)
           {
                checkNum = getCheckNumber();
                transAmt = getTransAmt();
                JOptionPane.showMessageDialog (null,processCheck(initialBalance, currentServiceCharge) );
                hassaved = false;

                line = "Check transaction complete";
                ta.setText(line);
           }
           else if( tCode == 2 )
           {
                transAmt = getTransAmt();
                JOptionPane.showMessageDialog (null, processDeposit( initialBalance, currentServiceCharge ) );
                hassaved = false;
                
                line = "Deposit transaction complete";
                ta.setText(line);
           }
           else if( tCode == 0)
           {
                finalbalance = datum.getBalance() - datum.getServiceCharge();
       
                NumberFormat fmt = new DecimalFormat("#0.00");
                message = datum.getName() + "'s Account" + "\n" + "Transaction: End"+"\n"+
                 "Current Balance: $"+fmt.format(datum.getBalance())+"\n"+
                 "Total Service Charge: $"+fmt.format(datum.getServiceCharge())+"\n"+
                 "Final Balance: $"+fmt.format(finalbalance); 
                JOptionPane.showMessageDialog (null, message);
                
                line = "Transactions ended";
                ta.setText(line);
           }

       frame.setVisible(true);
             
   }
   
   public static void addAcc()
   {
      CheckingAccount datum;
      String balancestr, name, line;
      double balance;
      int yes;
      
      frame.setVisible(false);
      name = JOptionPane.showInputDialog ("Enter the account name: ");
      acctName = name;
      
      balancestr = JOptionPane.showInputDialog ("Enter your initial balance: ");
      balance = Double.parseDouble(balancestr);
      initialBalance = balance;      
      
      datum = new CheckingAccount(name, balance);
      datum.setName( acctName );  
      datum.setBalance( initialBalance );  
      dataStore.addElement(datum);               
      index++;
      
      line = "New account created for: " + name; 
      ta.setText(line);
      
      hassaved = false;
      hasacc = true;
      frame.setVisible(true);
      
   }
   
   public static void listTrans()
   {  
      
      CheckingAccount datum;   
      int i, n; 
      String amtStr,line,check, deposit, svcchrg;

      NumberFormat fmt = NumberFormat.getCurrencyInstance();
      check = "check";
      deposit = "deposit";
      svcchrg = "svc.chrg";
      
      datum = dataStore.elementAt(index); 

      line = String.format("Transaction List for " + datum.getName()      
              +":\n--------------------------------------\nID       Type            Amount\n"); 
      
      for ( i=0; i < datum.getTransCount();i++)         
      {  
           
         t = datum.getTrans(i);                 
         n = t.getTransNumber();      
         amtStr = fmt.format(t.getTransAmount());
         
         if( t.getTransId() == 1)
         {
            line += String.format("%-7d  %-10s  %10s", i, "check", amtStr)+ "\n";  
         }
         else if( t.getTransId() == 2)
         {      
            line += String.format("%-7d  %-10s  %10s", i, "deposit", amtStr)+ "\n"; 
         }
         else if( t.getTransId() == 3)
         {    
            line += String.format("%-7d  %-10s  %10s", i, "svr.chrg", amtStr)+ "\n";   
         }
         else if( t.getTransId() == 4)
         {    
            line += String.format("%-7d  %-10s  %10s", i, "winnings", amtStr)+ "\n";   
         }
         else if( t.getTransId() == 5)
         {    
            line += String.format("%-7d  %-10s  %10s", i, "loosings", amtStr)+ "\n";   
         }

      }

	 ta.setBorder(null);
	 ta.setOpaque(false);
	 ta.setFont(new Font("Monospaced", Font.PLAIN, 14) );
         ta.setText(line);              
     
    }
   
    public static void listChecks()
   {  
      CheckingAccount datum;
      int i, check; 
      String amtStr,line;

      NumberFormat fmt = NumberFormat.getCurrencyInstance();
      
      datum = dataStore.elementAt(index);      
      
      line = String.format("Listing all checks for " + datum.getName()    
              + ":\n--------------------------------------\nID      Chk#        Amount\n"); 
      
      for ( i=0; i < datum.getTransCount() ;i++)   
      {  
          
          t = datum.getTrans(i);       
          
          if( t.getTransId() == 1)            
         {   
            c = (Check)t;                           
            amtStr = fmt.format(c.getTransAmount()); 
            check = c.getCheckNumber();
            line += String.format("%-7d %-7d %10s", i, check ,amtStr)+ "\n";  
         }
      }
         ta.setBorder(null);
	 ta.setOpaque(false);
	 ta.setFont(new Font("Monospaced", Font.PLAIN, 14) );
         ta.setText(line);   
    }
    
     public static void listDeposits()
   {  
      CheckingAccount datum;
      int i; 
      String amtStr,line;

      NumberFormat fmt = NumberFormat.getCurrencyInstance();
      
      datum = dataStore.elementAt(index);      
      
      line = String.format("Listing all deposits for " + datum.getName() + ":\n"    
              + "--------------------------------------\nID           Amount\n");  
      
      for ( i=0; i < datum.getTransCount();i++)          
      {  
        t = datum.getTrans(i);                        
        amtStr = fmt.format(t.getTransAmount());    
        
        if( t.getTransId() == 2)
         {
            line += String.format("%-7d  %10s", i ,amtStr)+ "\n";    
         }
       
      }
         ta.setBorder(null);
	 ta.setOpaque(false);
	 ta.setFont(new Font("Monospaced", Font.PLAIN, 14) );
         ta.setText(line);   
    }
   
     
   public static int getTransCode()

   {
       int code;
       String numstr;
       numstr = JOptionPane.showInputDialog ("Enter trans code: ");
       code = Integer.parseInt(numstr);
       return code;
   }

   public static double getTransAmt()

   {
       double amt;
       String amtstr;
       amtstr = JOptionPane.showInputDialog ("Enter trans amt: ");
       amt = Double.parseDouble(amtstr);
       return amt;

   }
   
   public static int getCheckNumber()  
   {
       String numberstr;
       int number;
       numberstr = JOptionPane.showInputDialog ("Enter the Check Number: ");
       number = Integer.parseInt(numberstr);
       return number;
       
   }
  
   public static String processCheck( double initialBalance, double currentServiceCharge)

   {
       CheckingAccount datum;
       double newbalance;
       String checkstr, check500 = "", check50 = "", check0 = "";     
       
       datum = dataStore.elementAt(index);      
       
       datum.setBalance(transAmt,tCode);    
          
       newbalance = datum.getBalance();   
       currentServiceCharge = datum.getServiceCharge();    
       
       c = new Check(datum.getTransCount(), 1 , transAmt, checkNum);   
       datum.addTrans( c );          
       
       if( newbalance < 500 && hasrun == true )    
       {
           check500 = "Service charge: Below $500 --- charge $5.00"+"\n";
           currentServiceCharge = currentServiceCharge + 5;
           hasrun = false;
           t = new Transaction(datum.getTransCount(),3,5.00);    
           datum.addTrans( t );      
           
       }
       if( newbalance < 50 )
       {
           check50 = "Warning: Balance below $50"+"\n";
       }
       if( newbalance < 0 )
       {
           check0 = "Service charge: Below $0 --- charge $10.00"+"\n";
           currentServiceCharge = currentServiceCharge + 10;
           t = new Transaction(datum.getTransCount(),3,10);     
           datum.addTrans( t );          
       }
       currentServiceCharge = currentServiceCharge +0.15;
       datum.setServiceCharge(currentServiceCharge);   
       
       t = new Transaction(datum.getTransCount(),3,0.15);    
       datum.addTrans( t );   
       
       NumberFormat fmt = new DecimalFormat("#0.00");
 
       checkstr = datum.getName() + "'s Acccount" + "\n" +  
                  "Transaction: Check #" + c.getCheckNumber() + " in the amount of $"+fmt.format(transAmt)+"\n"+
                  "Current Balance: $"+fmt.format(newbalance)+"\n"+
                  "Service Charge: Check --- charge $0.15"+"\n"+
                  check500 + check50 + check0 +
                  "Total Service Charge : $"+fmt.format(currentServiceCharge);  
      
               
       return checkstr;
       
   }

   public static String processDeposit( double initialBalance, double currentServiceCharge )

   {
       CheckingAccount datum; 
       double newbalance;
       String depositstr;
      
       datum = dataStore.elementAt(index);      
      
       datum.setBalance(transAmt,tCode);   
       newbalance = datum.getBalance();   
       currentServiceCharge = datum.getServiceCharge();   
       t = new Transaction(datum.getTransCount(),2,transAmt);     
       datum.addTrans( t );          
       
       currentServiceCharge = currentServiceCharge +0.10;
       datum.setServiceCharge(currentServiceCharge);    
       t = new Transaction(datum.getTransCount(),3,0.10); 
       datum.addTrans( t );   
       
       NumberFormat fmt = new DecimalFormat("#0.00");
       depositstr = datum.getName() + "'s Acccount" + "\n" +
                    "Transaction: Deposit in amount of $"+fmt.format(transAmt)+"\n"+
                    "Current Balance: $"+fmt.format(newbalance)+"\n"+
                    "Service Charge: Deposit --- charge $0.10"+"\n"+
                    "Total Service Charge : $"+fmt.format(currentServiceCharge);  
       return depositstr;
   }
   
   public static void findAcc()
   {  
        String name, symbol, message;
        int number;

        name = JOptionPane.showInputDialog ("Enter Account name: ");
	

        for (int i=0; i != dataStore.size(); i++)  
        {
				
	  if (name.equals(dataStore.elementAt(i).getName()))
		{
                        message = "Found account for " + name;
                        ta.setText(message);
                        index = i;                
                        return;
		}
	}
        
        message = name + " does not exist.\nPlease check List Accounts under the Accounts tab "
                + "to see the list of existing accounts."; 
        ta.setText(message);  
   }

   public static void accList()
   {  
        String name, symbol, message;
        int number;

        message = "Found accounts for: \n---------------------------------------\n";
        for (int i=0; i != dataStore.size(); i++)  
        {
		
            message += dataStore.elementAt(i).getName() + "\n";
                ta.setText(message);
                
        }
        
        if(dataStore.isEmpty())
        {
            message = "There are no accounts on file at the moment." ;
            ta.setText(message);
        }      
                       
   }
   
   //==================================Game=========================================
   //===============================================================================
   public static void playCards()
   {
      if( hasacc == false)
          addAcc();
      
      frame.setVisible(false);
      String url = "file:music.wav";
      try
             {
                      LoopSound.myLoop(url);
             }
         catch (InterruptedException ie) 
             {
                      System.out.println(ie);
             }

      JFrameC frameC = new JFrameC ("Hi-Lo");
      frameC.setDefaultCloseOperation (JFrameC.DO_NOTHING_ON_CLOSE);
      frameC.getContentPane().add(new CardPanel());
      
      frameC.pack();
      frameC.setVisible(true);
   }
   
   public static double getWager()
    {
       double amt;
       String amtstr;
       amtstr = JOptionPane.showInputDialog ("This is a game of Hi-Lo.  Numbers 1 to 5.\nGame has"
               + " no service charge!!!\n"
               + "-------------------------------------------------\n"
               + "Press Up: guess higher\nPress Down: guess lower\n-------------------------------------------------\nEnter your bet: ");
       amt = Double.parseDouble(amtstr);
       return amt;
    }

   public static void inputGamble()
   {  
       CheckingAccount datum;
       String message, balancestr, name, line;
       double finalbalance, balance;
       frame.setVisible(false);

       datum = dataStore.elementAt(index);      
       transAmt = CardPanel.getCredit();
           
           
           if( transAmt > 0 )
           {
                
                JOptionPane.showMessageDialog (null,processWin(initialBalance, currentServiceCharge) );
                hassaved = false;
           }
           else if( transAmt < 0 )
           {
                JOptionPane.showMessageDialog (null, processLoss( initialBalance, currentServiceCharge ) );
                hassaved = false;
 
           }

       frame.setVisible(true);
             
   }
   
   public static String processWin( double initialBalance, double currentServiceCharge )

   {
       CheckingAccount datum; 
       double newbalance;
       String depositstr;
      
       datum = dataStore.elementAt(index);      
      
       datum.setBalance(CardPanel.getCredit(),4);   
       newbalance = datum.getBalance();   
       t = new Transaction(datum.getTransCount(),4,CardPanel.getCredit());     
       datum.addTrans( t );          
       
       NumberFormat fmt = new DecimalFormat("#0.00");
       depositstr = datum.getName() + "'s Acccount" + "\n" +
                    "Transaction: Wining in amount of $"+fmt.format(CardPanel.getCredit())+"\n"+
                    "Current Balance: $"+fmt.format(newbalance)+"\n";  
       return depositstr;
   }
   
   public static String processLoss( double initialBalance, double currentServiceCharge )

   {
       CheckingAccount datum; 
       double newbalance;
       String depositstr;
      
       datum = dataStore.elementAt(index);      
      
       datum.setBalance(CardPanel.getCredit(),5);   
       newbalance = datum.getBalance();   
       t = new Transaction(datum.getTransCount(),5,CardPanel.getCredit());     
       datum.addTrans( t );         
       
       NumberFormat fmt = new DecimalFormat("#0.00");
       depositstr = datum.getName() + "'s Acccount" + "\n" +
                    "Transaction: Loss in amount of $"+fmt.format(CardPanel.getCredit())+"\n"+
                    "Current Balance: $"+fmt.format(newbalance)+"\n"+
                    "Total Service Charge : $"+fmt.format(currentServiceCharge);  
       return depositstr;
   }
   
   public static void instructions()
   {
       String message;
       message = "                                                                    "
               + "     How to Play"
               + "\n------------------------------------------------------------"
               + "--------------------------------------------------------------------------"
               + "\nThere are five types of cards numbered from 1 to 5.  You will guess "
               + "if the next card will be higher or \nlower.  If you guess correctly then"
               + "you will get the amount of money that you have bet.  If the next card"
               + "\nis equal to the card then you'll get half the amount of money that you "
               + "have bet.  If you guessed \nwrong then you lose equal to the amount of money "
               + "you have bet.\n\n"
               + "Controls"
               + "\nUp: guess higher"
               +"\nDown: guess lower";
       
       ta.setText(message);
   }
   //======================================================================================
   //======================================================================================
   
   public static void readFile() 
   {  
        if(hassaved == false) // save before deleting old file
        {
            int save;
            save = JOptionPane.showConfirmDialog (null, "Do you wish to save the current file "
                                                       + "before reading the new one?");
           
            if(save == JOptionPane.YES_OPTION)
                writeFile();
          
        }
       
       chooseFile(1);	
	try
		{
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(fis);
                        
                        String line;
                        line = "Reading object from: " + filename;
                        ta.setText(line); 
                        
			Vector storeIn = (Vector)in.readObject();
			in.close();
			dataStore = storeIn;
                        //dataStore.firstElement();  // probably not neccesary cause of index

                        hassaved = true;   
                        index = 0;        
                      
		}	
		catch(ClassNotFoundException e)	
                 { 
                     System.out.println(e);
                 }

                catch (IOException e) 
                 { 
                     System.out.println(e);
                 }
   }
   public static void writeFile() 
   {  
        chooseFile(2);
      	try
		{
                        FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fos);
                        
                        String line;
                        line = "Writing object to: " + filename;
                        ta.setText(line); 
                        
                        out.writeObject(dataStore);
                        out.close();
                        hassaved = true;        // has saved==============
                        hasacc = true;
		
		}	
	catch(IOException e)	
                { 
                     System.out.println(e);
                }
 
   }
   public static void chooseFile(int ioOption) 
   {  
      int status, confirm;       
                
      String  message = "Would you like to use the current default file: \n" +
                          filename;
      confirm = JOptionPane.showConfirmDialog (null, message);
      if (confirm == JOptionPane.YES_OPTION)
          return;
      JFileChooser chooser = new JFileChooser();
      if (ioOption == 1)
          status = chooser.showOpenDialog (null);
      else
          status = chooser.showSaveDialog (null);
      if (status == JFileChooser.APPROVE_OPTION)
      {
          File file = chooser.getSelectedFile();
          filename = file.getPath();
      }
   }

}



