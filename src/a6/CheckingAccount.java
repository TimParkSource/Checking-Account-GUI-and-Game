package a6;

import java.util.ArrayList;

public class CheckingAccount extends Account
{


      private double totalServiceCharge;
      
      private ArrayList<Transaction> transList; // keeps a list of Transaction objects for the account
      
      private int transCount = 0;   // the count of Transaction objects and used as the ID for each transaction 


      public CheckingAccount(String name, double initialBalance)

      {
            
            super( name , initialBalance);  
            
            transList = new ArrayList<Transaction>();  
            
            balance = initialBalance;

            totalServiceCharge = 0;

      }

 
      public void addTrans( Transaction newTrans)   // adds a transaction object to the transList
      {
          
          transList.add( newTrans); 
          transCount++;
      }
      
      public int getTransCount() //returns the current value of transCount
      {
          return transCount;
      }

      public Transaction getTrans(int i) // returns the i-th Transaction object in the list
      {
          return transList.get(i);         
      }                  

      public void setBalance(double transAmt, int tCode)

      {

            if(tCode == 1)

            balance = balance - transAmt;


            else 

                balance = balance + transAmt;


      }

 

      public double getServiceCharge()

      {

            return totalServiceCharge;

      }

 

        public void setServiceCharge(double currentServiceCharge)

      {
            
          totalServiceCharge = currentServiceCharge;

      }
      
}

 

 

 

 
