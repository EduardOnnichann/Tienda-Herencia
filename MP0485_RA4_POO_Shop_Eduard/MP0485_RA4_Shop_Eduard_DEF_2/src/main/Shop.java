/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author EduardOnnichannn
 */
import java.util.ArrayList;
import model.Product;
import model.Sale;
import java.util.Scanner;
import model.Amount;
import model.Empleado;
import model.Logable;
import model.Cliente;

public class Shop {

    private Amount cash = new Amount(100.00);
    private ArrayList<Product> inventory;
    private int numberProducts;
    private int numberSale;
    private ArrayList<Sale> sales;
    final static double TAX_RATE = 1.04;

    public Shop() {
        inventory = new ArrayList<Product>();
        //Issue 6
        sales = new ArrayList<Sale>();
    }

    public static void login() {
        Scanner sc = new Scanner(System.in);
        boolean logged = false;
        while (!logged) {
            System.out.println("Introduce numero de empleado: ");
            int idEmpleado = sc.nextInt();
            sc.nextLine();
            System.out.println("Introduce contrasena:");
            String password = sc.nextLine();
            Logable empleado = new Empleado("test", idEmpleado, password);
            
            if(empleado.login(idEmpleado, password)){
                System.out.println("Login correcto");
                logged = true;
            } else {
                System.out.println("Datos incorrectos. Intentalo de nuevo.");
            }
        }
    }

    public static void main(String[] args) {
        login();
        Shop shop = new Shop();

        shop.loadInventory();

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        boolean exit = false;

        do {
            System.out.println("\n");
            System.out.println("===========================");
            System.out.println("Menu principal miTienda.com");
            System.out.println("===========================");
            System.out.println("1) Contar caja");
            System.out.println("2) A\u00f1adir producto");
            System.out.println("3) A\u00f1adir stock");
            System.out.println("4) Marcar producto proxima caducidad");
            System.out.println("5) Ver inventario");
            System.out.println("6) Venta");
            System.out.println("7) Ver ventas");
            System.out.println("9) Eliminar producto");
            System.out.println("8) Ver total de ventas");
            System.out.println("10) Salir programa");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    shop.showCash();
                    break;

                case 2:
                    shop.addProduct();
                    break;

                case 3:
                    shop.addStock();
                    break;

                case 4:
                    shop.setExpired();
                    break;

                case 5:
                    shop.showInventory();
                    break;

                case 6:
                    shop.sale();
                    break;

                case 7:
                    shop.showSales();
                    break;

                case 8:
                    shop.showTotalSalesAmount();
                    break;

                case 9:
                    shop.eliminateProduct();
                    break;
                case 10:
                    exit = true;
                    break;
            }
        } while (!exit);
    }

    /**
     * load initial inventory to shop
     */
    public void loadInventory() {
        addProduct(new Product("Manzana", new Amount(10.00), true, 10));
        addProduct(new Product("Pera", new Amount(20.00), true, 20));
        addProduct(new Product("Hamburguesa", new Amount(30.00), true, 30));
        addProduct(new Product("Fresa", new Amount(5.00), true, 20));
    }

    /**
     * show current total cash
     */
    private void showCash() {
        //Issue 1
        System.out.println("Dinero actual: " + cash);
    }

    /**
     * add a new product to inventory getting data from console
     */
    public void addProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        //Issue 3   
        Product product = findProduct(name);
        if (product == null) {
            System.out.print("Precio mayorista: ");
            double price = scanner.nextDouble();
            Amount wholesalerPrice = new Amount(price);
            System.out.print("Stock: ");
            int stock = scanner.nextInt();
            addProduct(new Product(name, wholesalerPrice, true, stock));
        } else {
            System.out.println("El producto ya existe.");
        }

    }

    /**
     * add stock for a specific product
     */
    public void addStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();
        Product product = findProduct(name);

        if (product != null) {
            // ask for stock
            System.out.print("Seleccione la cantidad a a\u00f1adir: ");
            int stock = scanner.nextInt();
            // update stock product
            //Issue 5
            product.setStock(product.getStock() + stock);
            System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getStock());

        } else {
            System.out.println("No se ha encontrado el producto con nombre " + name);
        }
    }

    /**
     * set a product as expired
     */
    private void setExpired() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();

        Product product = findProduct(name);

        if (product != null) {
            // Issue 8
            product.expire();
            System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getPublicPrice());
        } else {
            System.out.println("Producto no encontrado");
        }
    }

    /**
     * show all inventory
     */
    public void showInventory() {
        System.out.println("Contenido actual de la tienda:");
        for (Product product : inventory) {
            if (product != null) {
                //Issue 2
                System.out.println(product.toString());
            }
        }
    }

    /**
     * make a sale of products to a client
     */
    public void sale() {

        Scanner sc = new Scanner(System.in);

        System.out.println("Realizar venta, escribir nombre cliente");
        String nombre = sc.nextLine();

        Cliente cliente = new Cliente(nombre);

        ArrayList<Product> cart = new ArrayList<>();
        Amount totalAmount = new Amount(0.0);

        String name = "";

        while (!name.equals("0")) {

            System.out.println("Productos disponibles:");
            for (Product product : inventory) {
                if (product != null && product.isAvailable() && product.getStock() > 0) {
                    System.out.println(product.getName() + " - " + product.getPublicPrice());
                }
            }

            System.out.println("Introduce nombre producto (0 para terminar):");
            name = sc.nextLine();

            if (name.equals("0")) break;

            Product product = findProduct(name);

            if (product != null && product.isAvailable()) {

                totalAmount.setValue(totalAmount.getValue() + product.getPublicPrice().getValue());
                product.setStock(product.getStock() - 1);

                if (product.getStock() == 0) {
                    product.setAvailable(false);
                }

                cart.add(product);
                System.out.println("Producto añadido");

            } else {
                System.out.println("No disponible");
            }
        }

        totalAmount.setValue(totalAmount.getValue() * TAX_RATE);

        boolean pagado = cliente.pay(totalAmount);

        addSale(new Sale(cliente, cart, totalAmount));

        if (pagado) {
            cash.setValue(cash.getValue() + totalAmount.getValue());
            System.out.println("Venta realizada con exito. Total: " + totalAmount);
        } else {
            System.out.println("Saldo insuficiente. Cliente debe: " + cliente.getSaldo());
        }
    }

    /**
     * show all sales
     */
    private void showSales() {
        System.out.println("Lista de ventas:");
        for (Sale sale : sales) {
            if (sale != null) {
                System.out.println("Cliente: " + sale.getClient().getNombre());
                System.out.print("Productos: ");

                for (Product products : sale.getProducts()) {
                    if (products != null) {
                        System.out.println(products.getName());
                    }
                }
                System.out.println("Total: " + sale.getAmount());
            }
        }
    }

    private void showTotalSalesAmount() {
        Amount amount = new Amount(0.0);

        for (Sale sale : sales) {
            if (sale != null) {
                amount.setValue(amount.getValue() + sale.getAmount().getValue());
            }
        }

        System.out.println("Total acumulado de todas las ventas: " + amount);
    }

    public void eliminateProduct() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el producto que quieres eliminar:");
        String nombre = sc.nextLine();

        Product product = findProduct(nombre);

        if (product != null) {
            inventory.remove(product);
        } else {
            System.out.println("El producto no existe");
        }
    }

    /**
     * add a product to inventory
     *
     * @param product
     */
    public void addProduct(Product product) {
        inventory.add(product);
    }

    //Añadir clientes a sale
    public void addSale(Sale sale) {
        sales.add(sale);
    }

    /**
     * find product by name
     *
     * @param name
     * @return product found by name
     */
    public Product findProduct(String name) {
        for (Product product : inventory) {
            if (product != null && product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }
}
