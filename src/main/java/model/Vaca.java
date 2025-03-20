/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author alna7
 */
public class Vaca {
//Declaramos variables en base a las entidades de las bases de datos 
    private int id; 
    private String raza; 
    private int edad; 
    private int granjaId;
    
    public Vaca(int id, String raza, int edad, int granjaId ) // Contructor para las vacas
    {
      this.id = id;
      this.raza = raza;
      this.edad = edad;
      this.granjaId = granjaId;
    }

    //Metodos getters y setters 

    public int getId() {
        return id;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getGranjaId() {
        return granjaId;
    }

    public void setGranjaId(int granjaId) {
        this.granjaId = granjaId;
    }
   
}
