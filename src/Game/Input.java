package Game;

import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;


public class Input extends JComponent
{
    private boolean[] Map;
    
    public Input()
    {
        Map = new boolean[256];
        
        for(int i = 0; i < Map.length; i++)
        {
            final int KeyCode = i;
            
            //проверка нажатых клавишь
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i,0,false),i*2);
            getActionMap().put(i*2,new AbstractAction()
            {
                public void actionPerformed(ActionEvent arg0)
                {
                    Map[KeyCode] = true;
                }
            });
            
            //проверка отпущенных клавишь
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i,0,true),i*2 + 1);
            getActionMap().put(i*2+1,new AbstractAction()
            {
                public void actionPerformed(ActionEvent arg0)
                {
                    Map[KeyCode] = false;
                }
            });
        }
    }
    
    public boolean[] GetMap()
    {
        return Arrays.copyOf(Map,Map.length);
    }
    
    public boolean GetKey(int KeyCode)
    {
        return Map[KeyCode];
    }
}
