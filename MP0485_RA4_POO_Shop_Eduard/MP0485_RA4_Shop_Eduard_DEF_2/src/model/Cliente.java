/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author EduardOnnichannn
 */
public class Cliente extends Persona implements Payable {
    
    private int numeroSocio;
    private Amount saldo;

    public static final int MEMBER_ID = 456;
    public static final double BALANCE = 50.00;

    public Cliente(String nombre) {
        super(nombre);
        this.numeroSocio = MEMBER_ID;
        this.saldo = new Amount(BALANCE);
    }

    public int getNumeroSocio() {
        return numeroSocio;
    }

    public Amount getSaldo() {
        return saldo;
    }

    @Override
    public boolean pay(Amount amount) {
        double nuevoSaldo = saldo.getValue() - amount.getValue();
        saldo.setValue(nuevoSaldo);

        if (nuevoSaldo >= 0) {
            return true;
        } else {
            return false;
        }
    }
}
