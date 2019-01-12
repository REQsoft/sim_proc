
package simule_process;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.BevelBorder;


class Proceso implements Comparable<Proceso>{
    String nombre;
    private int PID;
    private int prioridad,escala_progreso,progreso;
    private boolean  interactividad,iniciar;
    private int tiempo_procesador;
    private double quantum;
    private JButton identificador;
    private String estado;
    public JProgressBar barra_progreso;
    JPanel panel;
    private JLabel label_estado,label_interactividad;
    

    public Proceso() {
        nombre = "";
        PID = 0;
        prioridad = 0;
        interactividad = false;
        tiempo_procesador = 0;
        quantum = 0;
        estado = "Inactivo";
        iniciar = false;
        identificador = new JButton(); 
        asignar_identificador();
        identificador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(estado.equals("Suspendido")){
                    interactividad=false;
                    return;
                }
                if(estado.equals("Inactivo")){
                    iniciar=true;
                }
            }
        });
        barra_progreso=new JProgressBar();
        barra_progreso.setValue(0);
        barra_progreso.setStringPainted(true);
        
        panel=new JPanel();
        panel.setLayout(new GridLayout(1, 9));  
        label_estado=new JLabel();
        label_interactividad=new JLabel();
    }
    

    public int getPID() {
        return PID;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public boolean isInteractividad() {
        return interactividad;
    }

    public int getTiempo_procesador() {
        return tiempo_procesador;
    }

    public String getEstado() {
        return estado;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public void setInteractividad(boolean interactividad) {
        this.interactividad = interactividad;
    }

    public void setEscala_progreso(int escala_progreso) {
        this.escala_progreso = escala_progreso;
    }

    public void setTiempo_procesador(int tiempo_procesador) {
        this.tiempo_procesador = tiempo_procesador;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getQuantum() {
        return quantum;
    }

    public void setQuantum(double quantum) {
        this.quantum = quantum;
    }

    public JButton getIdentificador() {
        return identificador;
    }

    public JProgressBar getBarra_progreso() {
        return barra_progreso;
    }

    public boolean isIniciar() {
        return iniciar;
    }

    public void setIniciar(boolean iniciar) {
        this.iniciar = iniciar;
    }

    public JPanel getPanel() {
        return panel;
    }


    @Override
    public int compareTo(Proceso o) {
        if(prioridad == o.getPrioridad()){
            if(PID < o.getPID()){
                return -1;
            }
            return 1;
        }
        if(prioridad > o.getPrioridad()){
            return 1;
        }
        return -1;
    }

    public void setIdentificador(JButton identificador) {
        this.identificador = identificador;
    }
    
    public String mostrar(){
        String info = "";
        info=   "Prioridad: "+prioridad+"\n"
                +"PID: "+PID+"\n"
                +"Nombre: "+ nombre+"\n"
                +"Interactividad: "+interactividad+"\n"
                +"Tiempo_procesador: "+tiempo_procesador+"\n"
                +"Quamtum: "+quantum+"\n"
                +"Boton: "+identificador+"\n"
                +"Estado: "+estado+"\n"
                +"Escala: "+escala_progreso+"\n";
        return info;
    }
    
    private void asignar_identificador(){
        int rojo,verde,azul;
        rojo=ThreadLocalRandom.current().nextInt(1,254);
        verde=ThreadLocalRandom.current().nextInt(1,254);
        azul=ThreadLocalRandom.current().nextInt(1,254);
        Color color = new Color(rojo,verde,azul);
        identificador.setBackground(color);
        identificador.setMaximumSize(new Dimension(10,10));
    }
    
    public void actualizar_progreso(){
        progreso+=1;
        barra_progreso.setValue(progreso);
    }
    
    public void generar_info_proceso(){
         JLabel nombre= new JLabel("Nombre: " + this.nombre,JLabel.CENTER);
         nombre.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
         JLabel prioridad= new JLabel("Prioridad: "+String.valueOf(this.prioridad),JLabel.CENTER); 
         prioridad.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
         JLabel PID= new JLabel("PID: "+String.valueOf(this.PID),JLabel.CENTER); 
         PID.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
         JLabel tiempo_procesador= new JLabel("Tiempo: "+String.valueOf(this.tiempo_procesador),JLabel.CENTER); 
         tiempo_procesador.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
         JLabel quamtums= new JLabel("Quamtums: "+String.valueOf(this.quantum),JLabel.CENTER); 
         quamtums.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
         JButton identificador = new JButton();
         identificador.setBackground(this.identificador.getBackground());
         label_interactividad.setText("Interactividad: "+String.valueOf(interactividad));
         label_interactividad.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
         label_estado.setText("Estado: "+this.estado);  
         label_estado.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
         
           panel.add(nombre);
           panel.add(prioridad);
           panel.add(PID);
           panel.add(tiempo_procesador);
           panel.add(quamtums);
           panel.add(label_interactividad);
           panel.add(label_estado);
           panel.add(identificador);
           panel.add(barra_progreso);
    }
    
    public void actualizar_info(){
        label_estado.setText("Estado: "+estado);
        label_interactividad.setText("Interactividad: "+String.valueOf(interactividad));
        
    }
}
