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
        private int Id; 
        private String raza; 
        private int edad; 
        private int granjaId;
        private Integer madreId; // Puede ser nulo
        private Integer padreId; // Puede ser nulo  
   
    public Vaca(int id, String raza, int edad, int granjaId) {
        this.Id = id;
        this.raza = raza;
        this.edad = edad;
        this.granjaId = granjaId;
    }

    public Vaca() {}//constructor vacio 
    
        //Metodos getters y setters 

        public int getId() {
            return Id;
        }
        public int setId(int aInt){
        return Id; 
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

   public Integer getMadreId() { return madreId; }
    public void setMadreId(Integer madreId) { this.madreId = madreId; }

    public Integer getPadreId() { return padreId; }
    public void setPadreId(Integer padreId) { this.padreId = padreId; }

    }