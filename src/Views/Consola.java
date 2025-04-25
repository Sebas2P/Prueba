package Views;

import Models.Jugador;
import Models.Sistema;
import Utils.Utils;

import java.util.Scanner;

public class Consola {
    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("Menú:");
            System.out.println("1. Agregar jugador");
            System.out.println("2. MostrarJugadores");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el nombre del jugador: ");
                    String nombre = scanner.nextLine();
                    Jugador nuevoJugador = sistema.agregarJugador(nombre);
                    if (nuevoJugador != null) {
                        System.out.println("Jugador agregado: " + nuevoJugador.getNombre());
                    } else {
                        System.out.println("No se pudo agregar el jugador.");
                    }
                    break;
                case 2:
                    break;
                case 3:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 3);

        scanner.close();
    }


}
