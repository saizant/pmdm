package com.fpmislata.practicas.clase4.pojo;

import java.util.ArrayList;

/**
 * Created by loren on 07/10/15.
 */
public class Cuenta {

    private int id;
    private String banco;
    private String sucursal;
    private String dc;
    private String numeroCuenta;
    private float saldoActual;
    private Cliente cliente;
    private ArrayList<Movimiento> listaMovimientos;

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<Movimiento> getListaMovimientos() {
        return listaMovimientos;
    }

    public void setListaMovimientos(ArrayList<Movimiento> listaMovimientos) {
        this.listaMovimientos = listaMovimientos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cuenta(int id, String banco, String sucursal, String dc, String numeroCuenta, Cliente cliente, float saldoActual) {
        this.id = id;
        this.banco = banco;
        this.sucursal = sucursal;
        this.dc = dc;
        this.numeroCuenta = numeroCuenta;
        this.cliente = cliente;
        this.saldoActual = saldoActual;
        this.listaMovimientos = new ArrayList<Movimiento>();
    }

    public Cuenta(){
        super();
    }

    public float getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(float saldoActual) {
        this.saldoActual = saldoActual;
    }
}