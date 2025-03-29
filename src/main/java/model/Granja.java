
package model;

/**
 *
 * @author alna7
 */
public class Granja {
    
    private int id; 
    private String direccion; 
    private int  cantidadAnimal; 
    private int granjeroId; 
    
    
    public Granja() {}//Constructor vacio
    
    // Constructor para el objro Granja 
    public Granja(int id, String direccion, int cantidadAnimal, int granjeroId) {
        this.id = id;
        this.direccion = direccion;
        this.cantidadAnimal = cantidadAnimal;
        this.granjeroId = granjeroId;
    }


    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getCantidadAnimal() {
        return cantidadAnimal;
    }

    public void setCantidadAnimal(int cantidadAnimal) {
        if (cantidadAnimal < 0) {
            throw new IllegalArgumentException("La cantidad de animales no puede ser negativa.");
        }
        this.cantidadAnimal = cantidadAnimal;
    }

    public int getGranjeroId() {
        return granjeroId;
    }

    public void setGranjeroId(int granjeroId) {
        this.granjeroId = granjeroId;
    }

    public void setcantidadAnimal(String string) {  }
}

