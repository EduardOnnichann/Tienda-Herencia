/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author EduardOnnichannn
 */
public class Empleado extends Persona implements Logable{
    private int idEmpleado;
    private String password;
    
    public static final int EMPLOYEE_ID = 123;
    public static final String PASSWORD = "test";
    public static final String NOMBRE = "test";


    public Empleado(String nombre, int idEmpleado, String password) {
        super(nombre);
        this.idEmpleado = idEmpleado;
        this.password = password;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean login(int user, String password){
        return user == EMPLOYEE_ID && password.equals(PASSWORD);
    }
}
