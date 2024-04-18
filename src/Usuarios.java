import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.InputMismatchException;


public class Usuarios extends Compra {
    private String nombre;
    private String apellidos;
    private String telefono;
    private String correo;

    public Usuarios(String dni, String serial, String nombre, String apellidos, String telefono, String correo) {
        super(dni, serial);
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    // Método para buscar un usuario por DNI
    public static void buscarDni(String dni) {
        // Verificar si el DNI es válido
        if (validarDNI(dni)) {
            try {
                // Abrir el archivo de usuarios
                FileReader file = new FileReader("C:\\Users\\Dani-PC\\Desktop\\Usuarios.txt");
                BufferedReader bufferedReader = new BufferedReader(file);
                String linea;
                // Convertir el DNI a mayúsculas para comparar con el archivo
                String dniMayu = dni.toUpperCase();

                // Leer cada línea del archivo
                while ((linea = bufferedReader.readLine()) != null) {
                    // Dividir la línea en partes utilizando la coma como separador
                    String[] datosUsuario = linea.split(", ");
                    // Comprobar si el DNI coincide con el DNI buscado
                    if (datosUsuario[0].equals(dniMayu)) {
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
        } else {
            // Informar si el DNI introducido no es válido
            System.out.println("El DNI introducido no es válido");
        }
    }

    // Método para mostrar la información del usuario
    public static void mostrar(String[] usuario) {
        System.out.println("Información del usuario: ");
        System.out.println("Nombre: " + usuario[1]);
        System.out.println("Apellidos: " + usuario[2]);
        System.out.println("Correo electrónico: " + usuario[3]);
        System.out.println("Número de teléfono: " + usuario[4]);
    }

    // Método para agregar usuarios
    public static void agregarUsuarios(String dni2, String nombre, String apellidos, String correo, String numero) {
        // Verificar si el DNI ya existe en el archivo
        if (existeDNI(dni2)) {
            System.out.println("El DNI introducido ya existe en el archivo. Por favor, introduzca un DNI nuevo.");
            return;
        }

        // Validar los datos introducidos
        if (validarDNI(dni2) && validarTelefono(numero) && validaEmail(correo)) {
            try {
                // Crear un objeto File para el archivo de usuarios
                File archivoUsuarios = new File("C:\\Users\\Dani-PC\\Desktop\\Usuarios.txt");
                // Crear un BufferedWriter para escribir en el archivo
                BufferedWriter writer = new BufferedWriter(new FileWriter(archivoUsuarios, true));
                // Convertir el DNI a mayúsculas
                String dniMayu = dni2.toUpperCase();
                // Escribir la información del nuevo usuario en el archivo
                writer.write(dniMayu + ", " + nombre + ", " + apellidos + ", " + correo + ", " + numero + "\n");
                // Cerrar el BufferedWriter
                writer.close();
                System.out.println("Información agregada correctamente");
            } catch (IOException ex) {
                System.out.println("Error al modificar el archivo: " + ex.getMessage());
            }
        } else {
            // Informar si los datos introducidos no son válidos
            System.out.println("Los datos introducidos son erróneos.");
        }
    }

    // Método para eliminar un usuario por DNI
    public static void eliminarUsuario(String dniUsuario) {
        File inputFile = new File("C:\\Users\\Dani-PC\\Desktop\\Usuarios.txt");
        File tempFile = new File("C:\\Users\\Dani-PC\\Desktop\\Temp.txt");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String currentLine;
            boolean encontrado = false;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.startsWith(dniUsuario + ", ")) {
                    encontrado = true;
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }

            // Cerrar los recursos
            writer.close();
            reader.close();

            // Eliminar el archivo original y renombrar el archivo temporal
            if (inputFile.exists()) {
                if (!encontrado) {
                    System.out.println("El usuario con el DNI " + dniUsuario + " no existe en el archivo.");
                    tempFile.delete();
                } else {
                    inputFile.delete();
                    tempFile.renameTo(inputFile);
                    System.out.println("Se ha eliminado el usuario con el DNI: " + dniUsuario);
                }
            } else {
                System.out.println("No se encontró el archivo de usuarios.");
            }

        } catch (FileNotFoundException ex) {
            System.out.println("No se ha encontrado el archivo");
        } catch (IOException ex) {
            System.out.println("Error al eliminar usuario.");
        }
    }

    // Método para validar un DNI
    public static boolean validarDNI(String dni) {
        // Verificar la longitud del DNI
        if (dni.length() != 9) {
            return false;
        }
        // Verificar que los primeros 8 caracteres son dígitos
        for (int i = 0; i < 8; i++) {
            if (!Character.isDigit(dni.charAt(i))) {
                return false;
            }
        }
        // Verificar que el último carácter es una letra
        char lastChar = Character.toUpperCase(dni.charAt(8));
        return lastChar >= 'A' && lastChar <= 'Z';
    }

    // Método para validar un correo electrónico
    public static boolean validaEmail(String email) {
        // Patrón para validar direcciones de correo electrónico
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        // Compilar el patrón en un objeto Pattern
        Pattern pattern = Pattern.compile(emailPattern);
        // Verificar si el correo electrónico coincide con el patrón
        return pattern.matcher(email).matches();
    }

    // Método para validar un número de teléfono en España
    public static boolean validarTelefono(String telefono) {
        // Patrón para validar números de teléfono en España
        String telefonoPattern = "(\\+34|0034|34)?[ -]*(6|7|9)[ -]*([0-9][ -]*){8}";

        // Compilar el patrón en un objeto Pattern
        Pattern pattern = Pattern.compile(telefonoPattern);

        // Verificar si el número de teléfono coincide con el patrón
        Matcher matcher = pattern.matcher(telefono);

        // Verificar la longitud del número de teléfono
        boolean longitudValida = telefono.length() == 9;

        // Devolver true si tanto el patrón como la longitud son válidos
        return matcher.matches() && longitudValida;
    }

    // Método para verificar si un DNI ya existe en el archivo
    public static boolean existeDNI(String dni) {
        try {
            // Abrir el archivo de usuarios
            FileReader file = new FileReader("C:\\Users\\Dani-PC\\Desktop\\Usuarios.txt");
            BufferedReader bufferedReader = new BufferedReader(file);
            String linea;
            // Convertir el DNI a mayúsculas para comparar con el archivo
            String dniMayu = dni.toUpperCase();

            // Leer cada línea del archivo
            while ((linea = bufferedReader.readLine()) != null) {
                // Dividir la línea en partes utilizando la coma como separador
                String[] datosUsuario = linea.split(", ");
                // Comprobar si el DNI coincide con el DNI buscado
                if (datosUsuario[0].equals(dniMayu)) {
                    // Cerrar el BufferedReader
                    bufferedReader.close();
                    // El DNI ya existe en el archivo
                    return true;
                }
            }
            // Cerrar el BufferedReader
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            // Manejar la excepción si el archivo no se encuentra
            System.out.println("No se ha encontrado el archivo");
        } catch (IOException ex) {
            // Manejar la excepción si ocurre un error al leer el archivo
            System.out.println("Error al leer el archivo");
        }
        // El DNI no existe en el archivo
        return false;
    }

    public static boolean validarTexto(String texto) {
        // Patrón para validar que el texto contenga solo letras y espacios
        String textoPattern = "^[a-zA-Z\\s]+$";
        // Compilar el patrón en un objeto Pattern
        Pattern pattern = Pattern.compile(textoPattern);
        // Verificar si el texto coincide con el patrón
        Matcher matcher = pattern.matcher(texto);
        return matcher.matches();
    }

    // Método para mostrar el menú de opciones
    // Método para mostrar el menú de opciones
    public static void Menu() {
        Scanner sc = new Scanner(System.in);
        int opcion;
        boolean salir = false;
        while (!salir) {
            try {
                // Mostrar el menú
                System.out.println("Menu");
                System.out.println("1. Buscar DNI");
                System.out.println("2. Agregar Usuarios");
                System.out.println("3. Eliminar Usuarios");
                System.out.println("4. Volver al menú principal");
                System.out.println("Elige: ");
                opcion = sc.nextInt();
                switch (opcion) {
                    case 1:
                        System.out.println("Introducir DNI: ");
                        String dni = sc.next();
                        // Llamar al método buscarDni para buscar el usuario por DNI
                        buscarDni(dni);
                        break;
                    case 2:
                        System.out.println("Introducir DNI: ");
                        String dni2 = sc.next();
                        sc.nextLine(); // Consumir el carácter de nueva línea pendiente
                        String nombre;
                        String apellidos;
                        do {
                            System.out.println("Introducir nombre: ");
                            nombre = sc.nextLine();
                            System.out.println("Introducir apellidos: ");
                            apellidos = sc.nextLine();
                        } while (nombre.isEmpty() || apellidos.isEmpty() || !validarTexto(nombre) || !validarTexto(apellidos));
                        System.out.println("Introducir correo electrónico: ");
                        String correo = sc.nextLine();
                        System.out.println("Introducir número de teléfono: ");
                        String numero = sc.next();
                        if (validarDNI(dni2) || validarTelefono(numero) || validaEmail(correo)) {
                            agregarUsuarios(dni2, nombre, apellidos, correo, numero);
                        } else {
                            System.out.println("Los datos introducidos son erróneos");
                        }
                        break;

                    case 3:
                        System.out.println("Introducir DNI del usuario que desea eliminar: ");
                        String dniEliminar = sc.next();
                        eliminarUsuario(dniEliminar);
                        break;
                    case 4:
                        // Salir del programa
                        salir = true;
                        System.out.println("Ha vuelto al menú principal");
                        Compra.Menu();
                        break;
                    default:
                        // Informar si se elige una opción incorrecta
                        System.out.println("Opción incorrecta. Por favor, elige una opción válida.");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Introduce una opción válida");
                // Limpiar el buffer de entrada
                sc.next();
            }
        }
        sc.close();
    }



    public static void main(String[] args) {
        // Llamar al método Menu para iniciar el programa
        Menu();
    }
}

