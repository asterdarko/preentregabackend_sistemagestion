package com.techlab.producto.model;

public class ProductoRopa extends Producto {

    private String talle;

    public ProductoRopa(int codigo, String nombre, double precio, int stock, Categoria categoria, String talle) {
        super(codigo, nombre, precio, stock, categoria);
        this.talle = talle;
    }

    public String getTalle() {
        return talle;
    }

    public void setTalle(String talle) {
        this.talle = talle;
    }

    @Override
    public String getTipoProducto() {
        return "Ropa";
    }

    @Override
    public String getDetalleEspecifico() {
        return "Talle: " + talle;
    }

    @Override
    public String toString() {
        return super.toString() + " [subtipo ropa]";
    }
}