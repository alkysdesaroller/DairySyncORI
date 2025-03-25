/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package reproduccion;

import java.time.LocalDate;

/**
 *
 * @author josea
 */

//Declaracion de variables 
public class Reproduccion {
        private int vacaId;
        private int toroId;
        private String razaVaca;
        private String razaToro;
        private String estadoVaca;
        private LocalDate fecha;
        private int partoEstimado;

  //Constructor para Reproduccion
       
   public Reproduccion(int vacaId, int  toroId, String razaVaca, String razaToro, String estadoVaca, LocalDate fecha, int partoEstimado){
            this.vacaId =  vacaId;
            this.toroId =   toroId;
            this.razaVaca = razaVaca;
            this.razaToro = razaToro;
            this.estadoVaca = estadoVaca;
            this.fecha = fecha;
            this.partoEstimado = partoEstimado;
        }
   
   
   // Getters y Setters
   public int getVacaid(){
       return vacaId;
   }
   
   public void setvacaId(int vacaId){
       this.vacaId = vacaId;
   }
   
    public int getToroId(){
        return toroId;
    }
    
    public void settoroId(int toroId){
        this.toroId = toroId;
    }
    
    public String getRazaVaca(){
        return razaVaca;
    }
    
    public void setrazaVaca(String razaVaca){
        this.razaVaca = razaVaca;
    }
 
    public String getRazaToro(){
        return razaToro;
    }
    
    public void setrazaToro(String razaToro){
        this.razaToro = razaToro;
    }
 
    public String getEstadoVaca(){
        return estadoVaca;
    }
    
    public void setestadoVaca(String estadoVaca){
        this.estadoVaca = estadoVaca;
    }
            
    public LocalDate getFecha(){
        return fecha;
    }
    
    public void setfehca(LocalDate fecha){
        this.fecha = fecha;
    }
    
    public int getPartoEstimado(){
        return partoEstimado;
    } 
        public void setpartoEstimado(int partoEstimado){
            this.partoEstimado = partoEstimado;
        }
    
  
}
