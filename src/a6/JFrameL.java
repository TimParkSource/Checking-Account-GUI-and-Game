package a6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class JFrameL extends JFrame implements WindowListener
{
    
    /** Creates a new instance of JFrameL */
    public JFrameL(String title) {
        super(title);
        addWindowListener(this);
    }
    public void windowClosing(WindowEvent e) {
            //This will only be seen on standard output.
        if(A6.hassaved == false)
       {
            int save;
            save = JOptionPane.showConfirmDialog (null, "Do you wish to save");
            
            if(save == JOptionPane.YES_OPTION)
            {
                A6.writeFile();
                System.out.println("WindowListener method called: windowClosed.");
                this.setVisible(false);
                System.exit(0);
            }
            else if(save == JOptionPane.NO_OPTION)
            {
                System.out.println("WindowListener method called: windowClosed.");
                this.setVisible(false);
                System.exit(0);
            }
       
       }
       
        else
       {
            System.out.println("WindowListener method called: windowClosed.");
            this.setVisible(false);
            System.exit(0);
       }
    }

    public void windowClosed(WindowEvent e) {    }

    public void windowOpened(WindowEvent e) {    }

    public void windowIconified(WindowEvent e) {    }

    public void windowDeiconified(WindowEvent e) {    }

    public void windowActivated(WindowEvent e) {    }

    public void windowDeactivated(WindowEvent e) {    }    
}
