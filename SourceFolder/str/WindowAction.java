package str;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;
import javax.swing.JFrame;

public class WindowAction extends JFrame {

    private class EventListener extends WindowAdapter {

        @Override
        public void windowOpened(WindowEvent we) {
            onCreate();
        }
       
    }

    public WindowAction(){
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);
        
        WindowAdapter listener = new EventListener();
        addWindowListener(listener);
    }
    
    protected void onCreate(){ //Метод который создает все элементы окна
    	
    } 
}