package a6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class JFrameC extends JFrame implements WindowListener
{
    
    public JFrameC(String title) {
        super(title);
        addWindowListener(this);
    }
    public void windowClosing(WindowEvent e) 
    {
            A6.inputGamble();       
            CardPanel.gameEnd();
            this.setVisible(false);
    }

    public void windowClosed(WindowEvent e) {    }

    public void windowOpened(WindowEvent e) {    }

    public void windowIconified(WindowEvent e) {    }

    public void windowDeiconified(WindowEvent e) {    }

    public void windowActivated(WindowEvent e) {    }

    public void windowDeactivated(WindowEvent e) {    }    
}
