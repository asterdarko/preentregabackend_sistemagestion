package com.techlab.producto;

import java.util.ArrayList;
import java.util.Scanner;

import com.techlab.producto.model.Producto;
import com.techlab.producto.model.ProductoAlimento;
import com.techlab.producto.model.ProductoElectronico;
import com.techlab.producto.model.ProductoRopa;
import com.techlab.producto.model.Categoria;
import com.techlab.producto.model.Pedido;
import com.techlab.producto.model.LineaPedido;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<Producto> productos = new ArrayList<>();
        ArrayList<Categoria> categorias = new ArrayList<>();
        ArrayList<Pedido> pedidos = new ArrayList<>();
        
        precargarCategorias(categorias);

        int opcion;

        do {
            System.out.println("\n-----------------------------------");
            System.out.println(" SISTEMA DE GESTION DE PRODUCTOS");
            System.out.println("------------------------------------");
            System.out.println("1 - Ingresar producto");
            System.out.println("2 - Mostrar catálogo completo");
            System.out.println("3 - Buscar producto por código");
            System.out.println("4 - Actualizar datos de producto");
            System.out.println("5 - Dar de baja un producto");
            System.out.println("6 - Consultar listado de categorías");
            System.out.println("7 - Generar pedido");
            System.out.println("8 - Consultar pedidos efectuados");
            System.out.println("0 - Finalizar ejecución");
            System.out.println("-------------------------------------");

            opcion = leerEntero(scanner, "Seleccione una alternativa: ");

            switch (opcion) {
                case 1:
                    ingresarProducto(scanner, productos, categorias);
                    break;
                case 2:
                    listarProductos(productos);
                    break;
                case 3:
                    consultarProducto(scanner, productos);
                    break;
                case 4:
                    modificarProducto(scanner, productos, categorias);
                    break;
                case 5:
                    eliminarProducto(scanner, productos);
                    break;
                case 6:
                    listarCategorias(categorias);
                    break;
                case 7:
                    crearPedido(scanner, productos, pedidos);
                    break;
                case 8:
                    listarPedidos(pedidos);
                    break;
                case 0:
                    System.out.println("\nCerrando aplicación.");
                    break;
                default:
                    System.out.println("\nAviso: la opción ingresada no se encuentra disponible.");
            }

        } while (opcion != 0);

        scanner.close();
    }

    public static void precargarCategorias(ArrayList<Categoria> categorias) {
        categorias.add(new Categoria(1, "Alimentos", "Lácteos y Frescos"));
        categorias.add(new Categoria(2, "Alimentos", "Almacén y Secos"));
        categorias.add(new Categoria(3, "Electrónica", "Telefonía y aparatos"));
        categorias.add(new Categoria(4, "Electrónica", "Computación e Informática"));
        categorias.add(new Categoria(5, "Ropa", "Indumentaria Masculina"));
        categorias.add(new Categoria(6, "Ropa", "Indumentaria Femenina"));
    }

    public static void ingresarProducto(
            Scanner scanner,
            ArrayList<Producto> productos,
            ArrayList<Categoria> categorias
    ) {
        System.out.println("\n--- REGISTRO DE PRODUCTO ---");
        System.out.println("1 - Alimento");
        System.out.println("2 - Electrónico");
        System.out.println("3 - Ropa");

        int tipo;
        do {
            tipo = leerEntero(scanner, "Seleccione el tipo de artículo (1-3): ");

            if (tipo < 1 || tipo > 3) {
                System.out.println("Aviso: debe indicar 1, 2 o 3.");
            }

        } while (tipo < 1 || tipo > 3);

        int codigo = leerEntero(scanner, "Ingrese el código identificador: ");

        if (buscarProductoPorCodigo(productos, codigo) != null) {
            System.out.println("Aviso: ya existe un registro con este código.");
            return;
        }

        String nombre = leerTextoNoVacio(scanner, "Ingrese la denominación del producto: ");
        double precio = leerDoubleNoNegativo(scanner, "Ingrese el valor monetario: ");
        int stock = leerEnteroNoNegativo(scanner, "Ingrese la cantidad disponible en stock: ");

        listarCategorias(categorias);
        Categoria categoria = pedirCategoriaExistente(scanner, categorias);

        Producto producto;

        if (tipo == 1) {
            int diasVencimiento = leerEnteroNoNegativo(scanner, "Ingrese los días para el vencimiento: ");
            producto = new ProductoAlimento(codigo, nombre, precio, stock, categoria, diasVencimiento);
        } else if (tipo == 2) {
            int mesesGarantia = leerEnteroNoNegativo(scanner, "Ingrese los meses de garantía: ");
            producto = new ProductoElectronico(codigo, nombre, precio, stock, categoria, mesesGarantia);
        } else {
            String talle = leerTextoNoVacio(scanner, "Ingrese el talle: ");
            producto = new ProductoRopa(codigo, nombre, precio, stock, categoria, talle);
        }

        productos.add(producto);

        System.out.println("El producto fue registrado de manera exitosa.");
        System.out.println("Detalle de la instancia generada:");
        System.out.println(producto);
    }

    public static void listarProductos(ArrayList<Producto> productos) {
        System.out.println("\n--- INVENTARIO DE PRODUCTOS ---");

        if (productos.isEmpty()) {
            System.out.println("Actualmente no hay artículos cargados.");
            return;
        }

        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }

    public static void consultarProducto(Scanner scanner, ArrayList<Producto> productos) {
        System.out.println("\n--- BÚSQUEDA DE PRODUCTO ---");

        if (productos.isEmpty()) {
            System.out.println("Actualmente no hay artículos cargados.");
            return;
        }

        int codigo = leerEntero(scanner, "Ingrese el código del artículo a consultar: ");

        Producto producto = buscarProductoPorCodigo(productos, codigo);

        if (producto == null) {
            System.out.println("No se encontró ningún artículo con ese código.");
            return;
        }

        System.out.println("Registro localizado:");
        System.out.println(producto);
        System.out.println("Especificación técnica: " + producto.getDetalleEspecifico());
    }

    public static void modificarProducto(
            Scanner scanner,
            ArrayList<Producto> productos,
            ArrayList<Categoria> categorias
    ) {
        System.out.println("\n--- ACTUALIZACIÓN DE PRODUCTO ---");

        if (productos.isEmpty()) {
            System.out.println("Actualmente no hay artículos cargados.");
            return;
        }

        int codigo = leerEntero(scanner, "Ingrese el código del artículo a modificar: ");

        Producto producto = buscarProductoPorCodigo(productos, codigo);

        if (producto == null) {
            System.out.println("No se encontró ningún artículo con ese código.");
            return;
        }

        String nuevoNombre = leerTextoNoVacio(scanner, "Ingrese la nueva denominación: ");
        double nuevoPrecio = leerDoubleNoNegativo(scanner, "Ingrese el nuevo valor monetario: ");
        int nuevoStock = leerEnteroNoNegativo(scanner, "Ingrese el stock actualizado: ");

        listarCategorias(categorias);
        Categoria nuevaCategoria = pedirCategoriaExistente(scanner, categorias);

        producto.setNombre(nuevoNombre);
        producto.setPrecio(nuevoPrecio);
        producto.setStock(nuevoStock);
        producto.setCategoria(nuevaCategoria);

        if (producto instanceof ProductoAlimento) {
            ProductoAlimento alimento = (ProductoAlimento) producto;
            int nuevosDias = leerEnteroNoNegativo(scanner, "Ingrese los días de vencimiento actualizados: ");
            alimento.setDiasVencimiento(nuevosDias);
        }

        if (producto instanceof ProductoElectronico) {
            ProductoElectronico electronico = (ProductoElectronico) producto;
            int nuevosMeses = leerEnteroNoNegativo(scanner, "Ingrese los meses de garantía actualizados: ");
            electronico.setMesesGarantia(nuevosMeses);
        }

        if (producto instanceof ProductoRopa) {
            ProductoRopa ropa = (ProductoRopa) producto;
            String nuevoTalle = leerTextoNoVacio(scanner, "Ingrese el talle actualizado: ");
            ropa.setTalle(nuevoTalle);
        }

        System.out.println("La actualización se completó correctamente.");
    }

    public static void eliminarProducto(Scanner scanner, ArrayList<Producto> productos) {
        System.out.println("\n--- BAJA DE PRODUCTO ---");

        if (productos.isEmpty()) {
            System.out.println("Actualmente no hay artículos cargados.");
            return;
        }

        int codigo = leerEntero(scanner, "Ingrese el código del artículo a eliminar: ");

        Producto producto = buscarProductoPorCodigo(productos, codigo);

        if (producto == null) {
            System.out.println("No se encontró ningún artículo con ese código.");
            return;
        }

        productos.remove(producto);
        System.out.println("El registro fue removido satisfactoriamente.");
        System.out.println(producto);
    }

    public static void crearPedido(Scanner scanner, ArrayList<Producto> productos, ArrayList<Pedido> pedidos) {
        System.out.println("\n--- GENERACIÓN DE PEDIDO ---");

        if (productos.isEmpty()) {
            System.out.println("No existen artículos disponibles para procesar un pedido.");
            return;
        }

        int idPedido = pedidos.size() + 1;
        ArrayList<LineaPedido> lineasPedido = new ArrayList<>();
        char continuar;

        do {
            listarProductos(productos);
            int codigoProducto = leerEntero(scanner, "Indique el código del artículo que desea incorporar al pedido: ");
            Producto producto = buscarProductoPorCodigo(productos, codigoProducto);

            if (producto == null) {
                System.out.println("Aviso: El artículo indicado no existe.");
            } else {
                int cantidad = leerEnteroNoNegativo(scanner, "Indique las unidades requeridas: ");

                if (cantidad == 0) {
                    System.out.println("La cantidad debe ser mayor a cero.");
                } else if (cantidad > producto.getStock()) {
                    System.out.println("Aviso: Stock insuficiente. Unidades disponibles: " + producto.getStock());
                } else {
                    lineasPedido.add(new LineaPedido(producto, cantidad));
                    producto.setStock(producto.getStock() - cantidad);
                    System.out.println("Item incorporado al pedido de manera correcta.");
                }
            }

            System.out.print("¿Desea incluir otro artículo? (s/n): ");
            continuar = scanner.nextLine().trim().toLowerCase().charAt(0);

        } while (continuar == 's');

        if (!lineasPedido.isEmpty()) {
            Pedido nuevoPedido = new Pedido(idPedido, lineasPedido);
            pedidos.add(nuevoPedido);
            System.out.println("\n¡Pedido generado con éxito!");
            System.out.println(nuevoPedido);
        } else {
            System.out.println("No se añadieron elementos, por lo que la operación fue cancelada.");
        }
    }

    public static void listarPedidos(ArrayList<Pedido> pedidos) {
        System.out.println("\n--- REGISTRO DE PEDIDOS ---");

        if (pedidos.isEmpty()) {
            System.out.println("No constan pedidos registrados hasta el momento.");
            return;
        }

        for (Pedido pedido : pedidos) {
            System.out.println(pedido);
            System.out.println("--------------------------------------------");
        }
    }

    public static void listarCategorias(ArrayList<Categoria> categorias) {
        System.out.println("\n--- SECCION DE CATEGORÍAS ---");

        for (Categoria categoria : categorias) {
            System.out.println(categoria);
        }
    }

    public static Categoria pedirCategoriaExistente(Scanner scanner, ArrayList<Categoria> categorias) {
        while (true) {
            int codigoCategoria = leerEntero(scanner, "Indique el código correspondiente a la categoría: ");

            Categoria categoria = buscarCategoriaPorCodigo(categorias, codigoCategoria);

            if (categoria != null) {
                return categoria;
            }

            System.out.println("Aviso: la categoría ingresada no es válida.");
        }
    }

    public static Producto buscarProductoPorCodigo(ArrayList<Producto> productos, int codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }
        return null;
    }

    public static Categoria buscarCategoriaPorCodigo(ArrayList<Categoria> categorias, int codigo) {
        for (Categoria categoria : categorias) {
            if (categoria.getCodigo() == codigo) {
                return categoria;
            }
        }
        return null;
    }

    public static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Aviso: debe teclear un número entero válido.");
            }
        }
    }

    public static int leerEnteroNoNegativo(Scanner scanner, String mensaje) {
        while (true) {
            int valor = leerEntero(scanner, mensaje);

            if (valor < 0) {
                System.out.println("Aviso: el valor ingresado no puede ser inferior a cero.");
                continue;
            }

            return valor;
        }
    }

    public static double leerDoubleNoNegativo(Scanner scanner, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                double valor = Double.parseDouble(scanner.nextLine());

                if (valor < 0) {
                    System.out.println("Aviso: el importe no puede ser negativo.");
                    continue;
                }

                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Aviso: debe teclear un valor numérico decimal válido.");
            }
        }
    }

    public static String leerTextoNoVacio(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine();

            if (!texto.trim().isEmpty()) {
                return texto.trim();
            }

            System.out.println("Aviso: este campo no puede permanecer vacío.");
        }
    }
}


