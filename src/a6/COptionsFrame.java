package a6;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class COptionsFrame extends JFrameL
{
   public static final int WIDTH = 300;
   public static final int HEIGHT = 200;
   private JMenu fileMenu, CheckingAccountMenu, TransactionMenu, GambleMenu;
   private JMenuItem readFile,writeFile,addAccount,listAccount, listTransaction,findAccount, addTrans, listCheck,listDeposit,gamble, howto;
   //-----------------------------------------------------------------
   //  Sets up a panel with a label and a set of radio buttons
   //  that present options to the user.
   //-----------------------------------------------------------------
   public COptionsFrame(String title )
   {
        super(title);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrameL.DO_NOTHING_ON_CLOSE);  
       

        JMenu fileMenu = new JMenu("File");
        
        MenuListener ml = new MenuListener();     

        JMenuItem readFile = new JMenuItem("Read Account");
        readFile.addActionListener(ml);
        fileMenu.add(readFile);

        JMenuItem writeFile = new JMenuItem("Save Account");
        writeFile.addActionListener(ml);
        fileMenu.add(writeFile);

        JMenu CheckingAccountMenu = new JMenu("Account");
        
        JMenuItem addAccount = new JMenuItem("Add Account");
        addAccount.addActionListener(ml);
        CheckingAccountMenu.add(addAccount);
        
        JMenuItem listTransaction = new JMenuItem("List Transactions");
        listTransaction.addActionListener(ml);
        CheckingAccountMenu.add(listTransaction);
        
        JMenuItem listCheck = new JMenuItem("List Checks");
        listCheck.addActionListener(ml);
        CheckingAccountMenu.add(listCheck);
        
        JMenuItem listDeposit = new JMenuItem("List Deposits");
        listDeposit.addActionListener(ml);
        CheckingAccountMenu.add(listDeposit);

        JMenuItem findAccount = new JMenuItem("Find Account");
        findAccount.addActionListener(ml);
        CheckingAccountMenu.add(findAccount);
        
        JMenuItem listAccount = new JMenuItem("List Accounts");
        listAccount.addActionListener(ml);
        CheckingAccountMenu.add(listAccount);
        
        JMenu TransactionMenu = new JMenu("Transaction");
        
        JMenuItem addTrans = new JMenuItem("Add Transaction");
        addTrans.addActionListener(ml);
        TransactionMenu.add(addTrans);
        
        JMenu GambleMenu = new JMenu("Gamble");
        
        JMenuItem gamble = new JMenuItem("Play Hi-Lo");
        gamble.addActionListener(ml);
        GambleMenu.add(gamble);
        
        JMenuItem howto = new JMenuItem("How to Play");
        howto.addActionListener(ml);
        GambleMenu.add(howto);

        JMenuBar bar = new JMenuBar( );
        bar.add(fileMenu);
        bar.add(CheckingAccountMenu);
        bar.add(TransactionMenu);
        bar.add(GambleMenu);
        setJMenuBar(bar);
          
   }

    private class MenuListener implements ActionListener
    {
      public void actionPerformed (ActionEvent event) 
      {
         String source = event.getActionCommand();

         if (source.equals("Add Transaction"))
           A6.inputTrans();
         else
            if (source.equals("Add Account"))
               A6.addAcc();
            else
                if (source.equals("Read Account"))
                   A6.readFile();
                else
                   if (source.equals("Save Account"))
                      A6.writeFile();
                  else
                     if (source.equals("List Transactions"))
                        A6.listTrans();
                     else
                        if (source.equals("List Checks"))
                            A6.listChecks();
                        else
                            if (source.equals("List Deposits"))
                               A6.listDeposits();
                            else
                               if (source.equals("Find Account"))
                                  A6.findAcc();
                               else
                                  if (source.equals("List Accounts"))
                                     A6.accList();
                                 else
                                    if (source.equals("Play Hi-Lo"))
                                       A6.playCards(); 
                                   else
                                       if (source.equals("How to Play"))
                                         A6.instructions(); 
         
      }
   }
}
