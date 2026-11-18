package com.techlab.producto.model;

public class ProductoElectronico extends Producto {

    private int mesesGarantia;

    public ProductoElectronico(int codigo, String nombre, double precio, int stock, Categoria categoria, int mesesGarantia) {
        super(codigo, nombre, precio, stock, categoria);
        this.mesesGarantia = mesesGarantia;
    }

    public int getMesesGarantia() {
        return mesesGarantia;
    }

    public void setMesesGarantia(int mesesGarantia) {
        this.mesesGarantia = mesesGarantia;
    }

    @Override
    public String getTipoProducto() {
        return "Electrónico";
    }

    @Override
    public String getDetalleEspecifico() {
        return "Garantía: " + mesesGarantia + " meses";
    }

    public String nroTelMesaDeAyudaParaReclamos() {
        return "0800-123-4567";
    }

    @Override
    public String toString() {
        return super.toString() + " [subtipo electrónico]";
    }
}