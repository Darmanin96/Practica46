import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Compra {

    private String dni;
    private String serial;

    public Compra(String dni, String serial) {
        this.dni = dni;
        this.serial = serial;
    }

    public String getDni() {
        return dni;
    }

    public String getSerial() {
        return serial;
    }

    public static void mostrarVideojuegos() {
        Scanner sc = new Scanner(System.in);
        String ruta = "C:\\Users\\Dani-PC\\Desktop\\Juegos.txt";

        try {
            FileReader file = new FileReader(ruta);
            BufferedReader bufferedReader = new BufferedReader(file);
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                String[] usuario = linea.split(", ");
                System.out.println("Nombre " + usuario[1] + " Consola: " + usuario[2]);
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("No se ha encontrado el archivo");
        } catch (IOException ex) {
            System.out.println("Error al leer el archivo");
        }
        System.out.println("Introducir nombre del videojuego que desea comprar");
        String compra = sc.nextLine();
        System.out.println("Introducir dni para hacer la compra: ");
        String dni = sc.next();
        Compra(compra, dni);
    }

    public static void Compra(String compra, String dni) {
        String ruta = "C:\\Users\\Dani-PC\\Desktop\\Compra.txt";
        try {
            File archivoJuegos = new File(ruta);
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivoJuegos, true));
            writer.write(compra + ", " + dni + "\n");
            writer.close();
            System.out.println("Información agregada correctamente");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Error al modificar el archivo: " + ex.getMessage());
        }
    }

    public static void Menu() {
        Scanner sc = new Scanner(System.in);
        int opcion;
        System.out.println("Seleccione qué desea hacer");
        System.out.println("1. Videojuegos");
        System.out.println("2. Usuarios");
        System.out.println("3. Comprar Videojuegos");
        System.out.println("4. Volver al menú principal");
        System.out.println("Elige: ");
        opcion = sc.nextInt();
        switch (opcion) {
            case 1:
                System.out.println("Ha elegido Videojuegos: ");
                System.out.println("¿Qué desea hacer?");
                Videojuego.Menu();
                break;
            case 2:
                System.out.println("Ha elegido Usuarios: ");
                System.out.println("¿Qué desea hacer?");
                Usuarios.Menu();
                break;
            case 3:
                System.out.println("Estos son los videojuegos que están a la venta: ");
                mostrarVideojuegos();
                break;
            case 4:
                System.out.println("Ha salido del programa");
                break;
            default:
                System.out.println("Seleccione una opción válida");
        }
    }


    public static void main(String[] args) {
        Menu();
    }
}


