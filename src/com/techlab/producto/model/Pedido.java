package com.techlab.producto.model;

import java.util.ArrayList;

public class Pedido {
    private int id;
    private ArrayList<LineaPedido> lineas;

    public Pedido(int id, ArrayList<LineaPedido> lineas) {
        this.id = id;
        this.lineas = lineas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<LineaPedido> getLineas() {
        return lineas;
    }

    public void setLineas(ArrayList<LineaPedido> lineas) {
        this.lineas = lineas;
    }

    public double calcularTotal() {
        double total = 0;
        for (LineaPedido linea : lineas) {
            total += linea.getSubtotal();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido ID: ").append(id).append("\n");
        for (LineaPedido linea : lineas) {
            sb.append("  - ").append(linea.toString()).append("\n");
        }
        sb.append("Total del Pedido: $").append(calcularTotal());
        return sb.toString();
    }
}