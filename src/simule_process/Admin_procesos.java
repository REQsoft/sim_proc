
package simule_process;

import java.awt.FlowLayout;
import java.awt.ScrollPane;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;



public class Admin_procesos extends Thread{
    private Proceso proceso,ejecucion_anterior,en_ejecucion;
    private int rata_quantum;
    //Procesos en un estado anterior
    private Vector<Proceso> preparados_anterior
            ,suspendidos_anterior;
    //procesos en un estado actual
    private Vector<Proceso> inactivos_actual,preparados_actual,suspendidos_actual,lista_procesos;
    private JFrame principal_frame;
    private JScrollPane panel_inactivos,panel_preparados,panel_ejecucion,panel_suspendidos;
    private int numero_procesos,velocidad;
    private String estado_emulador;
    private boolean emulando;
    
    public Admin_procesos(JFrame principal_frame,JScrollPane inactivos, JScrollPane preparados, 
                            JScrollPane ejecucion, JScrollPane suspendidos){
        rata_quantum = 10;
        inactivos_actual = new Vector();
        lista_procesos=new Vector();
        this.principal_frame=principal_frame;
        panel_inactivos=inactivos;
        panel_preparados=preparados;
        panel_ejecucion=ejecucion;
        panel_suspendidos=suspendidos;
        numero_procesos=0;
        velocidad=10;
        estado_emulador="no iniciado";
        emulando=false;
    }
    
    public boolean agragar_proceso(String nombre_procesos){

        if(!existe_proceso(nombre_procesos)){
           proceso = new Proceso();
           proceso.setNombre(nombre_procesos);
           proceso.setPrioridad(ThreadLocalRandom.current().nextInt(1,5)); 
           proceso.setPID(ThreadLocalRandom.current().nextInt(1,100));
           proceso.setTiempo_procesador(ThreadLocalRandom.current().nextInt(50,200));
           proceso.setQuantum((double)proceso.getTiempo_procesador()/rata_quantum);
           proceso.barra_progreso.setMaximum((int)proceso.getQuantum()+1);
           if(ThreadLocalRandom.current().nextInt(0,2)==1){
                proceso.setInteractividad(true);
            } else {
                proceso.setInteractividad(false);
            } 
            proceso.generar_info_proceso();
            lista_procesos.addElement(proceso);
            numero_procesos+=1; 
            return true;
        }
        return false;
    }
    
    public void eliminar_proceso(int index){
        lista_procesos.removeElementAt(index);
    }
    
    public void cargar_simulador(){  
        inactivos_actual = new Vector();
        preparados_actual = new Vector();
        suspendidos_actual = new Vector();
        en_ejecucion = null;
        inactivos_actual.addAll(lista_procesos);
        //Dibujamos los procesos inactivos en un primer estado
        dibujar_procesos(panel_inactivos, lista_procesos);
    }
    
    public void iniciar_simulador(){
        estado_emulador="iniciado";
        inactivos_actual.clear();
        priorizar_procesos();
        preparados_actual.addAll(lista_procesos);
    }
    
    private void priorizar_procesos(){
        Proceso p=new Proceso();
        for(int i=0;i<lista_procesos.size();i++) {
            for(int j=0;j<lista_procesos.size()-1;j++) {
                if (lista_procesos.elementAt(j).compareTo(lista_procesos.elementAt(j+1))==1) {                   
                    p=lista_procesos.elementAt(j);
                    lista_procesos.remove(j);
                    lista_procesos.add(j+1, p);

                }
                System.out.println(lista_procesos.size());
                
            }

        }
    }
    
    public void siguien_estado(){
        preparados_anterior=(Vector<Proceso>) preparados_actual.clone();
        ejecucion_anterior = en_ejecucion;
        suspendidos_anterior = (Vector<Proceso>) suspendidos_actual.clone();
        
        preparados_actual.clear();
        suspendidos_actual.clear();
        
        actualizar_preparados();
        actualizar_ejecucion();
        actualizar_suspendidos();
        
        actualizar_lista_procesos();
    }
    
    
    private void actualizar_preparados(){
        preparados_actual.addAll(preparados_anterior);
        for(int i=0;i<inactivos_actual.size();i++){
            if(inactivos_actual.elementAt(i).isIniciar()){
                inactivos_actual.elementAt(i).setEstado("Preparado");
                inactivos_actual.elementAt(i).actualizar_info();
                preparados_actual.addElement(inactivos_actual.elementAt(i));
                inactivos_actual.removeElementAt(i);
                break;
            }
        }
        for(int i=0;i<suspendidos_anterior.size();i++){
            if(!suspendidos_anterior.elementAt(i).isInteractividad()){
                suspendidos_anterior.elementAt(i).setEstado("Preparado");
                suspendidos_anterior.elementAt(i).actualizar_info();
                preparados_actual.addElement(suspendidos_anterior.elementAt(i));
                suspendidos_anterior.removeElementAt(i);
                i-=1;
            }
        } 
    }
  
    private void actualizar_ejecucion(){
        if(preparados_anterior.size()>0){
            preparados_anterior.elementAt(0).setEstado("En ejecucion");
            preparados_actual.elementAt(0).actualizar_info();
            en_ejecucion=preparados_anterior.elementAt(0);
            en_ejecucion.setQuantum(en_ejecucion.getQuantum()-1);
            preparados_actual.removeElementAt(0);
            return;
        }
        en_ejecucion=null;
    }
    
    private void actualizar_suspendidos(){
        suspendidos_actual.addAll(suspendidos_anterior);
        if(ejecucion_anterior!=null){
            if(ejecucion_anterior.getQuantum()<=0){
                ejecucion_anterior.setEstado("Inactivo");
                ejecucion_anterior.actualizar_info();
                ejecucion_anterior.setIniciar(false);
                inactivos_actual.addElement(ejecucion_anterior);
                ejecucion_anterior=null;
                return;
            }
            ejecucion_anterior.setEstado("Suspendido");
            ejecucion_anterior.actualizar_info();
            suspendidos_actual.addElement(ejecucion_anterior);
            ejecucion_anterior=null;
        }
    }

    
    public void setRata_quantum(int rata_quantum) {
        this.rata_quantum = rata_quantum;
    }

    public Proceso getEn_ejecucion() {
        return en_ejecucion;
    }

    public Vector<Proceso> getInactivos_actual() {
        return inactivos_actual;
    }

    public Vector<Proceso> getPreparados_actual() {
        return preparados_actual;
    }

    public Vector<Proceso> getSuspendidos_actual() {
        return suspendidos_actual;
    }

    public int getNumero_procesos() {
        return numero_procesos;
    }

    public int getRata_quantum() {
        return rata_quantum;
    }

    public Vector<Proceso> getLista_procesos() {
        return lista_procesos;
    }

    public String getEstado_emulador() {
        return estado_emulador;
    }

    public void setEmulando(boolean emulando) {
        this.emulando = emulando;
    }

    public boolean isEmulando() {
        return emulando;
    }

       
    public void run(){
        emulando=true;
        while(emulando){
            dibujar_procesos(panel_inactivos,inactivos_actual);
            dibujar_procesos(panel_preparados,preparados_actual);
            JPanel panel = new JPanel();
            if(en_ejecucion!=null){
                panel.add(en_ejecucion.getIdentificador());
                en_ejecucion.actualizar_progreso();
            }
            panel_ejecucion.setViewportView(panel);
            dibujar_procesos(panel_suspendidos,suspendidos_actual);
            siguien_estado();
            try{
              sleep(velocidad*100);  
            }catch(InterruptedException e){}          
        }
    }
    
    public void paso_a_paso(){
        dibujar_procesos(panel_inactivos,inactivos_actual);
        dibujar_procesos(panel_preparados,preparados_actual);
        JPanel panel = new JPanel();
        if(en_ejecucion!=null){
            panel.add(en_ejecucion.getIdentificador());
            en_ejecucion.actualizar_progreso();
        }
        panel_ejecucion.setViewportView(panel);
        dibujar_procesos(panel_suspendidos,suspendidos_actual);
        siguien_estado();         
    }

    public void reducir_velocidad() {
            if(velocidad<15){
               velocidad+=1; 
            }     
    }
    
    public void aumentar_velocidad() {
        if(velocidad>6){
            velocidad-=1;
        }
    }
    
    private void dibujar_procesos(JScrollPane panel,Vector<Proceso> vector){
        JPanel procesos = new JPanel();
        if(vector.size()!=0){
            procesos.setLayout(new FlowLayout());           
            for(int i=0;i<vector.size();i++){
            procesos.add(vector.elementAt(i).getIdentificador());
                //System.out.println(vector.elementAt(i).mostrar());
            } 
        } 
        panel.setViewportView(procesos);
    }
    
    private void actualizar_lista_procesos(){
        lista_procesos.clear();
        
        lista_procesos.addAll(inactivos_actual);
  
        lista_procesos.addAll(preparados_actual);
        
        lista_procesos.addElement(en_ejecucion);

        lista_procesos.addAll(suspendidos_actual);
    }
    
    
    public void reiniciar_emulador(){
        JPanel panel=new JPanel();
        emulando=false;
        inactivos_actual.clear();
        suspendidos_actual.clear();
        en_ejecucion=null;
        preparados_actual.clear(); 
        lista_procesos.clear();
        panel_inactivos.setViewportView(panel);
        panel_preparados.setViewportView(panel);
        panel_ejecucion.setViewportView(panel);
        panel_suspendidos.setViewportView(panel);
    }

    public void setInactivos_actual(Proceso proceso) {
        inactivos_actual.addElement(proceso);
    }
    
    public Proceso pos_proceso_entrada(String nombre_proceso){
        proceso = new Proceso();
        proceso.setNombre(nombre_proceso);
        proceso.setPrioridad(ThreadLocalRandom.current().nextInt(1,5)); 
        proceso.setPID(ThreadLocalRandom.current().nextInt(1,20));
        proceso.setTiempo_procesador(ThreadLocalRandom.current().nextInt(50,200));
        proceso.setQuantum((double)proceso.getTiempo_procesador()/rata_quantum);
        proceso.barra_progreso.setMaximum((int)proceso.getQuantum());
        if(ThreadLocalRandom.current().nextInt(0,2)==1){
                proceso.setInteractividad(true);
            } else {
                proceso.setInteractividad(false);
            } 
        proceso.generar_info_proceso();
        lista_procesos.addElement(proceso);
        return proceso;
    }
    
    public Proceso buscar_proceso(String nombre){
        for(int i=0;i<inactivos_actual.size();i++){
            if(nombre.equals(inactivos_actual.elementAt(i).getNombre())){
                return inactivos_actual.elementAt(i);
            }
        }
        return null;
    }
  
    public boolean existe_proceso(String nombre){
        for(int i=0;i<lista_procesos.size();i++){
            if(lista_procesos.elementAt(i).getNombre().equals(nombre)){
                return true;
            }
        }
        return false;
    }
}
