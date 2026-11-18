package com.techlab.producto.model;

public abstract class Producto {

    private int codigo;
    private String nombre;
    private double precio;
    private int stock;
    private Categoria categoria;

    public Producto(int codigo, String nombre, double precio, int stock, Categoria categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public abstract String getTipoProducto();

    public abstract String getDetalleEspecifico();

    @Override
    public String toString() {
        return "Producto {" +
                "código=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", categoría='" + categoria.getNombre() + '\'' +
                ", tipo='" + this.getTipoProducto() + '\'' +
                ", detalle='" + this.getDetalleEspecifico() + '\'' +
                '}';
    }
}