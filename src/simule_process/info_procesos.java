
package simule_process;

import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class info_procesos{
    
    JScrollPane panel_principal; 
    JPanel sub_panel;
    
    public info_procesos(JScrollPane panel){
        panel_principal=panel;
        sub_panel = new JPanel();
        sub_panel.setLayout(new BoxLayout(sub_panel, BoxLayout.Y_AXIS));
    }   
    
    public void procesos_iniciales(Vector<Proceso> procesos){
        for(int i=0;i<procesos.size();i++){
           sub_panel.add(procesos.elementAt(i).getPanel()); 
        }  
        panel_principal.setViewportView(sub_panel);
    }  
    
    public void nuevo_proceso(Proceso proceso){
        if(proceso!=null){
           sub_panel.add(proceso.getPanel());
        }
        panel_principal.setViewportView(sub_panel);
    }
    

    public void borrar_procesos(){
        sub_panel.removeAll();
        panel_principal.repaint();
    }
    
}
