package main;

import model.Amount;
import model.Product;
import model.Sale;
import model.Client;
import model.Employee;

import java.util.Scanner;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Shop {
    private Amount cash = new Amount(100);
    private ArrayList<Product> inventory;
    private ArrayList<Sale> sales;
    final static double TAX_RATE = 1.04;
    Employee employee; 

    public Shop() {
        inventory = new ArrayList<>();
        sales = new ArrayList<>();
    }

    public static void main(String[] args) {
        Shop shop = new Shop();
        shop.loadInventory();
        shop.initSession();

        Scanner scanner = new Scanner(System.in);
        int opcion;
        boolean exit = false;

        do {
            System.out.println("\n");
            System.out.println("===========================");
            System.out.println("Menu principal miTienda.com");
            System.out.println("===========================");
            System.out.println("1) Contar caja");
            System.out.println("2) Añadir producto");
            System.out.println("3) Añadir stock");
            System.out.println("4) Marcar producto proxima caducidad");
            System.out.println("5) Ver inventario");
            System.out.println("6) Venta");
            System.out.println("7) Ver ventas");
            System.out.println("8) Ver venta total");
            System.out.println("9) Eliminar producto");
            System.out.println("10) Salir programa");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("Caja actual: " + shop.showCash());
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
                    shop.showTotalSales();
                    break;
                case 9:
                    shop.deleteProduct();
                    break;
                case 10:
                    System.out.println("Saliendo...");
                    exit = true;
                    break;
            }
        } while (!exit);
    }

    public void initSession() {
        boolean logged = false;
        Scanner input = new Scanner(System.in);

        do {
            System.out.print("Introduce número de empleado: ");
            int user = input.nextInt();
            input.nextLine();  // Consume the newline character
            System.out.print("Introduce contraseña de empleado: ");
            String password = input.nextLine();
            
            System.out.println();
            employee = new Employee("Martina"); // Placeholder for employee name, since login method creates a new instance.
            logged = employee.login(user, password);
            if (!logged) {
                System.out.println("Credenciales incorrectas. Inténtalo de nuevo.");
            }
        } while (!logged);
    }

    public void loadInventory() {
        try {
            File f = new File("./files/inputInventory.txt");
            BufferedReader r = new BufferedReader(new FileReader(f));
            String line;
            while ((line = r.readLine()) != null) {
                String[] parts = line.split(";");
                String name = null;
                double wholesalerPrice = 0.0;
                int stock = 0;
                for (String part : parts) {
                    String[] keyValue = part.split(":");
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();
                    switch (key) {
                        case "Product":
                            name = value;
                            break;
                        case "Wholesaler Price":
                            wholesalerPrice = Double.parseDouble(value);
                            break;
                        case "Stock":
                            stock = Integer.parseInt(value);
                            break;
                        default:
                            break;
                    }
                }
                if (name != null) {
                    Product product = new Product(name, wholesalerPrice, true, stock);
                    addProduct(product);
                }
            }
            System.out.println("Inventario cargado desde el fichero inputInventory.txt");
            System.out.println();
            Scanner mostrarInventario = new Scanner(f);
            while (mostrarInventario.hasNextLine()) {
                String inventario = mostrarInventario.nextLine();
                System.out.println(inventario); 
            }
            r.close();
        } catch (IOException e) {
            System.out.println("Error al cargar el inventario");
            e.printStackTrace();
        }
    }

    public double showCash() {
        return cash.getValue();
    }

    public boolean addProduct(String name, int stock, double price) {
        Product product = new Product(name, price, true, stock);
        inventory.add(product);
        return true;
    }

    public void addProduct() {	
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        System.out.print("Precio mayorista: ");
        double wholesalerPrice = scanner.nextDouble();
        System.out.print("Stock: ");
        int stock = scanner.nextInt();
        addProduct(new Product(name, wholesalerPrice, true, stock));
    }

    public boolean addStock(String name, int stock) {
        Product product = findProduct(name);
        if (product != null) {
            product.setStock(product.getStock() + stock);
            return true;
        }
        return false;
    }

    public void addStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();
        Product product = findProduct(name);
        if (product != null) {
            System.out.print("Seleccione la cantidad a añadir: ");
            int stock = scanner.nextInt();
            product.setStock(product.getStock() + stock);
            System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getStock());
        } else {
            System.out.println("No se ha encontrado el producto con nombre " + name);
        }
    }

    public void setExpired() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();
        Product product = findProduct(name);
        if (product != null) {
            product.expire();
            System.out.println("El precio del producto " + name + " ha sido actualizado a " + product.getPublicPrice());
        }
    }

    public void showInventory() {
        System.out.println("Contenido actual de la tienda:");
        for (Product product : inventory) {
            if (product != null) {
                if(product.getStock() == 0) {
                    product.setAvailable(false);
                }
                System.out.println(product.toString());
            }
        }
    }

    public void sale() {
        int productCount = 0;
        ArrayList<Product> productSales = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Realizar venta, escribir nombre cliente");
        String nameClient = scanner.nextLine();
        Client client = new Client(nameClient);
        double totalAmount = 0.0;
        String nameProduct = "";
        while (!nameProduct.equals("0")) {
            System.out.println("Introduce el nombre del producto, escribir 0 para terminar:");
            nameProduct = scanner.nextLine();
            if (nameProduct.equals("0")) {
                break;
            }
            Product product = findProduct(nameProduct);
            boolean productAvailable = false;
            if (product != null && product.isAvailable()) {
                productAvailable = true;
                totalAmount += product.getPublicPrice();
                product.setStock(product.getStock() - 1);
                if (product.getStock() == 0) {
                    product.setAvailable(false);
                }
                productSales.add(product);
                productCount++;
                System.out.println("Producto añadido con éxito");
            }
            if (!productAvailable) {
                System.out.println("Producto no encontrado o sin stock");
            }
        }
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String salesDate = date.format(format);
        System.out.println();
        System.out.println("Hora de venta: " + salesDate);
        Sale currentSale = new Sale(nameClient, productSales, totalAmount, date);
        System.out.println(currentSale.toString());
        addSales(currentSale);
        totalAmount = totalAmount * TAX_RATE;
        cash.setValue(cash.getValue() + totalAmount); 
        System.out.println("Venta realizada con éxito, total: " + totalAmount + cash.getCurrency());
        boolean pay = client.pay(totalAmount);
        if(!pay) {
            System.out.print(cash.getCurrency());
        } else {
            System.out.print(cash.getCurrency() + " restantes.");
        }
    }

    public void showSales() {
        System.out.println("Lista de ventas:");
        for (Sale sale : sales) {
            if (sale != null) {
                System.out.println(sale.toString());
            }
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("¿Guardar la información de venta en un fichero?(S / N)");
        String option = sc.next();
        if (option.equalsIgnoreCase("S")) {
            try {
                LocalDateTime currentDate = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
                String formattedDate = currentDate.format(formatter);
                String fileName = "Ventas_" + formattedDate + ".txt";
                FileWriter fileWriter = new FileWriter(fileName);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                for (Sale sale : sales) {
                    if (sale != null) {
                        printWriter.println(sale.toString());
                    }
                }
                printWriter.close();
                System.out.println("Las ventas se han guardado en el fichero: " + fileName);
            } catch (IOException e) {
                System.out.println("Error al guardar la información de ventas en el fichero.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Las ventas no se guardarán en un fichero.");
        }
    }

    public void showTotalSales() {
        double totalAmount = 0.0;
        for (Sale sale : sales) {
            if (sale != null) {
                totalAmount += sale.getAmount();
            }
        }
        System.out.println("El total de las ventas es: " + totalAmount + cash.getCurrency());
    }

    public boolean deleteProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();
        Product product = findProduct(name);
        if (product != null) {
            inventory.remove(product);
            System.out.println("Producto " + name + " eliminado con éxito.");
            return true;
        } else {
            System.out.println("No se ha encontrado el producto con nombre " + name);
            return false;
        }
    }

    public Product findProduct(String name) {
        for (Product product : inventory) {
            if (product != null && product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    public void addProduct(Product product) {
        inventory.add(product);
    }

    public void addSales(Sale sale) {
        sales.add(sale);
    }
}
