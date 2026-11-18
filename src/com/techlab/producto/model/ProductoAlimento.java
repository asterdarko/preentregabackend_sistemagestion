package com.techlab.producto.model;

public class ProductoAlimento extends Producto {

    private int diasVencimiento;

    public ProductoAlimento(int codigo, String nombre, double precio, int stock, Categoria categoria, int diasVencimiento) {
        super(codigo, nombre, precio, stock, categoria);
        this.diasVencimiento = diasVencimiento;
    }

    public int getDiasVencimiento() {
        return diasVencimiento;
    }

    public void setDiasVencimiento(int diasVencimiento) {
        this.diasVencimiento = diasVencimiento;
    }

    @Override
    public String getTipoProducto() {
        return "Alimento";
    }

    @Override
    public String getDetalleEspecifico() {
        return "Días para vencimiento: " + diasVencimiento;
    }

    @Override
    public String toString() {
        return super.toString() + " [subtipo alimento]";
    }
}