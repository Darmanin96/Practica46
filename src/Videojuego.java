import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Videojuego extends Compra {

    private String nombre;
    private String consola;
    private boolean Venta;

    // Constante para la ruta del archivo de juegos
    private static final String RUTA_ARCHIVO = "C:\\Users\\Dani-PC\\Desktop\\Juegos.txt";
    private static final String RUTA_ARCHIVO2 = "C:\\Users\\Dani-PC\\Desktop\\Temp.txt";

    public Videojuego(String dni, String serial, String nombre, String consola, Boolean Venta) {
        super(dni, serial);
        this.nombre = nombre;
        this.consola = consola;
        this.Venta=true;
    }

    public String getNombre() {
        return nombre;
    }

    public String getConsola() {
        return consola;
    }

    public static boolean validarSerial(String serial) {
        // Patrón para validar el número serial
        String serialPattern = "^[A-Za-z0-9]+$";

        // Compilar el patrón en un objeto Pattern
        Pattern pattern = Pattern.compile(serialPattern);

        // Verificar si el número serial coincide con el patrón
        Matcher matcher = pattern.matcher(serial);
        return matcher.matches();
    }

    public static void agregarVideojuego(String serial, String nombre, String consola) {
        String venta = "venta";
        try {
            // Crear un objeto File para el archivo de juegos
            File archivoJuegos = new File(RUTA_ARCHIVO);
            // Crear un BufferedWriter para escribir en el archivo
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivoJuegos, true));
            // Escribir la información del nuevo juego en el archivo
            writer.write(serial + ", " + nombre + ", " + consola + ", " + venta +  "\n");
            // Cerrar el BufferedWriter
            writer.close();
            System.out.println("Información agregada correctamente");
        } catch (IOException ex) {
            // Imprimir la traza de la excepción en caso de error
            ex.printStackTrace();
            System.out.println("Error al modificar el archivo: " + ex.getMessage());
        }
    }

    public static void buscarVideojuego(String serial2) {
        try {
            // Abrir el archivo de usuarios
            FileReader file = new FileReader("C:\\Users\\Dani-PC\\Desktop\\Juegos.txt");
            BufferedReader bufferedReader = new BufferedReader(file);
            String linea;
            // Leer cada línea del archivo
            while ((linea = bufferedReader.readLine()) != null) {
                // Dividir la línea en partes utilizando la coma como separador
                String[] datosUsuario = linea.split(", ");
                // Comprobar si el DNI coincide con el DNI buscado
                if (datosUsuario[0].equals(serial2)) {
                    // Mostrar la información del usuario
                    mostrar(datosUsuario);
                    bufferedReader.close();
                    return;
                }
            }
            bufferedReader.close();
            // Informar si el usuario no se encuentra en el archivo
            System.out.println("Usuario no encontrado");
        } catch (FileNotFoundException ex) {
            // Manejar la excepción si el archivo no se encuentra
            System.out.println("No se ha encontrado el archivo");
        } catch (IOException ex) {
            // Manejar la excepción si ocurre un error al leer el archivo
            System.out.println("Error al leer el archivo");
        }
    }

    public static void mostrar(String[] usuario) {
        System.out.println("Información del videojuego: ");
        System.out.println("Serial: " + usuario[0]);
        System.out.println("Nombre: " + usuario[1]);
        System.out.println("Consola: " + usuario[2]);
        System.out.println("El videojuego se encuentra: " + usuario[3]);
    }

    public static void Obsoleto(String serial3) {
        try {
            boolean obsoleto=false;
            // Abrir el archivo de juegos
            File archivo = new File(RUTA_ARCHIVO);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo));
            String linea;
            // Crear un nuevo archivo temporal para escribir los datos actualizados
            File archivoTemp = new File(RUTA_ARCHIVO2);
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivoTemp));
            // Leer cada línea del archivo
            while ((linea = bufferedReader.readLine()) != null) {
                // Dividir la línea en partes utilizando la coma como separador
                String[] datosJuegos = linea.split(", ");
                // Verificar si la línea tiene el formato esperado
                if (datosJuegos.length >= 4) {
                    // Comprobar si el serial coincide con el serial buscado y el juego no está obsoleto
                    if (datosJuegos[0].equals(serial3) && !datosJuegos[3].equals("Obsoleto")) {
                        // Marcar el juego como obsoleto
                        datosJuegos[3] = "Obsoleto";
                        obsoleto = true;
                    }
                    // Escribir la línea en el archivo temporal
                    writer.write(String.join(", ", datosJuegos) + "\n");
                }
            }
            // Cerrar los lectores y escritores
            bufferedReader.close();
            if(obsoleto){
                writer.close();
                // Eliminar el archivo original y renombrar el archivo temporal
                archivo.delete();
                archivoTemp.renameTo(archivo);
                System.out.println("El juego ha sido marcado como obsoleto.");
            }else {
                System.out.println("El juego ya se encuentra obsoleto ");
            }

        } catch (IOException ex) {
            // Manejar la excepción si ocurre un error al leer o escribir el archivo
            System.out.println("Error al modificar el archivo: " + ex.getMessage());
        }
    }

    public static void Menu() {
        Scanner sc = new Scanner(System.in);
        Set<String> consolasDisponibles = new HashSet<>();
        consolasDisponibles.add("Xbox");
        consolasDisponibles.add("PC");
        consolasDisponibles.add("PlayStation");
        int opcion;
        do {
            // Mostrar el menú
            System.out.println("Menu");
            System.out.println("1. Buscar Videojuego");
            System.out.println("2. Agregar Videojuego");
            System.out.println("3. Marcar como obsoleto");
            System.out.println("4. Volver al menú principal");
            System.out.print("Elige: ");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    System.out.println("Introducir serial del videojuego que desea buscar: ");
                    String serial2 = sc.next();
                    buscarVideojuego(serial2);
                    break;
                case 2:
                    System.out.println("Introducir serial: ");
                    String serial = sc.next();
                    sc.nextLine(); // Consumir la nueva línea pendiente
                    System.out.println("Introdcucir nombre: ");
                    String nombre = sc.nextLine(); // Leer toda la línea
                    System.out.println("Consolas disponibles: " + consolasDisponibles);
                    System.out.println("Selecciona una consola: ");
                    String consola = sc.next();

                    if (consolasDisponibles.contains(consola) && validarSerial(serial)) {
                        agregarVideojuego(serial, nombre, consola);
                    } else {
                        System.out.println("Error: Nombre, consola o serial inválido.");
                        // Menu(); No es necesario llamar recursivamente al menú
                    }
                    break;
                case 3:
                    System.out.println("Introducir serial del videojuego que desea poner en obsoleto");
                    String obsoleto = sc.next();
                    Obsoleto(obsoleto);
                    break;
                case 4:
                    System.out.println("Volviendo al menú principal");
                    Compra.Menu();
                    break;
                default:
                    // Informar si se elige una opción incorrecta
                    System.out.println("Opción incorrecta. Por favor, elige una opción válida.");
            }
        } while (opcion != 4); // Revisar la condición de salida del bucle
    }

    public static void main(String[] args) {
        // Llamar al método Menu para iniciar el programa
        Menu();
    }
}

