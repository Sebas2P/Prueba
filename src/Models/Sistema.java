package Models;

import Utils.Utils;

public class Sistema {

    public Jugador agregarJugador(String nombre) {
        Jugador jugador = new Jugador(nombre);
        for (int i = 0; i < Utils.jugadores.length; i++) {
            if (Utils.jugadores[i] == null) {
                Utils.jugadores[i] = jugador;
                Utils.numJugadores++;
                return jugador;
            }
        }
        // Si no hay espacio, expandir el arreglo
        Jugador[] nuevoArreglo = new Jugador[Utils.jugadores.length + 5];
        System.arraycopy(Utils.jugadores, 0, nuevoArreglo, 0, Utils.jugadores.length);
        Utils.jugadores = nuevoArreglo;
        Utils.jugadores[Utils.numJugadores] = jugador;
        Utils.numJugadores++;
        return jugador;
    }

    public String[] mostrarJugadores() {
            String[] nombres = new String[Utils.jugadores.length];
            for (int i = 0; i < Utils.jugadores.length; i++) {
                if (Utils.jugadores[i] != null) {
                    nombres[i] = Utils.jugadores[i].getNombre();
                }
            }
            return nombres;
    }

    public String[][] emparejarJugadores() {
            int numJugadores = 0;
            for (Jugador jugador : Utils.jugadores) {
                if (jugador != null) {
                    numJugadores++;
                }
            }

            String[][] emparejamientos = new String[numJugadores / 2][2];
            int index = 0;

            for (int i = 0; i < Utils.jugadores.length; i++) {
                if (Utils.jugadores[i] != null) {
                    for (int j = i + 1; j < Utils.jugadores.length; j++) {
                        if (Utils.jugadores[j] != null) {
                            emparejamientos[index][0] = Utils.jugadores[i].getNombre();
                            emparejamientos[index][1] = Utils.jugadores[j].getNombre();
                            Utils.jugadores[i] = null; // Marcar como emparejado
                            Utils.jugadores[j] = null; // Marcar como emparejado
                            index++;
                            break;
                        }
                    }
                }
            }

            return emparejamientos;
    }

}
