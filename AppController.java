package com.paintify.app;
import java.awt.event.*;

public class AppController implements ActionListener {

    private static AppController instance=null;
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand() + " Pressed ");

        // TODO Add Code to Change the DRAWING CONTROLLERS and also change Toolbar on Side
        
    }
    public static AppController getInstance(){
        if (instance==null)
        {
            instance=new AppController();
        }
        return instance;
    }    
}
