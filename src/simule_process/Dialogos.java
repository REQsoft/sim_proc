/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simule_process;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author PC1
 */
public class Dialogos extends JDialog{
    JFrame ventana_padre;
    
    public Dialogos(JFrame frame ){
        super(frame,true);
    }
    
    public void nombre_repetido(JButton ubucacion){
        setSize(120,120);
        JLabel mensaje = new JLabel("El procesos ya existe");
        add(mensaje);
         setLocationRelativeTo(ubucacion);
        setVisible(true);
        
    }
    
    
    public void model_vacio(JButton ubucacion){
        setSize(170,120);
       JLabel mensaje = new JLabel("   Lista de procesos vacía");     
       add(mensaje);
       setLocationRelativeTo(ubucacion);
       setVisible(true);
    }
}
