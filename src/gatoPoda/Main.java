package gatoPoda;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LinkedList<String> tablero = new LinkedList<String>();
        boolean turnoPC = true;
        crearTablero(tablero);
        mostrarTablero(tablero);
        do {
            turnoPC = !turnoPC;
            if (turnoPC) {
                PC(tablero);
            } else {
                jugador(tablero);
            }
            mostrarTablero(tablero);
        } while (!veriVictoria(tablero, turnoPC) && !veriEmpate(tablero));
        if (veriVictoria(tablero, turnoPC)) {
            String ganador = turnoPC ? "MiniMax" : "Jugador";
            System.out.println("\n El Ganador es : " + ganador);
        } else {
            System.out.println("\n Es un Empate!!!");
        }
    }

    public static void jugador(LinkedList<String> t) {
        @SuppressWarnings("resource")
        Scanner entrada = new Scanner(System.in);
        System.out.println("\nTurno del Jugador... ('O')");
        System.out.print("Ingrese una posicion 1-9: ");
        int pos = entrada.nextInt() - 1;
        if (t.get(pos).equals("X") || t.get(pos).equals("O")) {
            System.out.println("\nIngrese un espacio disponible...");
            jugador(t);
        } else {
            t.set(pos, "O");
        }
    }

    public static void PC(LinkedList<String> t) {
        int mPuntaje = -100;
        int pos = 0;
        for (int i = 0; i < 9; i++) {
            String temp = t.get(i);
            if (!temp.equals("X") && !temp.equals("O")) {
                t.set(i, "X");
                int puntos = algoritmoMiniMax(t, 0, false, -100, 100);
                t.set(i, temp);
                if (puntos > mPuntaje) {
                    mPuntaje = puntos;
                    pos = i;
                }
            }
        }
        System.out.println("\nTurno de PC... ('X')");
        t.set(pos, "X");
    }

    public static int algoritmoMiniMax(LinkedList<String> t, int depth, boolean tPC, int alpha, int beta) {
        if (veriVictoria(t, !tPC)) {
            return depth - 10;
        } else if (veriVictoria(t, tPC)) {
            return 10 - depth;
        } else if (veriEmpate(t)) {
            return 0;
        } else if (depth == 9) {
            return 0;
        }

        if (tPC) {
            int mPuntaje = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                String temp = t.get(i);
                if (!temp.equals("X") && !temp.equals("O")) {
                    t.set(i, "X");
                    int puntos = algoritmoMiniMax(t, depth + 1, false, alpha, beta);
                    t.set(i, temp);
                    mPuntaje = Math.max(mPuntaje, puntos);
                    alpha = Math.max(alpha, mPuntaje);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return mPuntaje;
        } else {
            int mPuntaje = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                String temp = t.get(i);
                if (!temp.equals("X") && !temp.equals("O")) {
                    t.set(i, "O");
                    int puntos = algoritmoMiniMax(t, depth + 1, true, alpha, beta);
                    t.set(i, temp);
                    mPuntaje = Math.min(mPuntaje, puntos);
                    beta = Math.min(beta, mPuntaje);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return mPuntaje;
        }
    }

    public static boolean veriVictoria(LinkedList<String> t, boolean tPC) {
        String val = tPC ? "X" : "O";
        for (int i = 0; i < 3; i++) {
            if (t.get(i).equals(val) && t.get(i + 3).equals(val) && t.get(i + 6).equals(val)) {
                return true;
            }
        }
        for (int i = 0; i < 9; i += 3) {
            if (t.get(i).equals(val) && t.get(i + 1).equals(val) && t.get(i + 2).equals(val)) {
                return true;
            }
        }
        if (t.get(0).equals(val) && t.get(4).equals(val) && t.get(8).equals(val)) {
            return true;
        }
        if (t.get(2).equals(val) && t.get(4).equals(val) && t.get(6).equals(val)) {
            return true;
        }
        return false;
    }

    public static boolean veriEmpate(LinkedList<String> t) {
        for (String val : t) {
            if (!val.equals("X") && !val.equals("O")) {
                return false;
            }
        }
        return true;
    }

    public static void mostrarTablero(LinkedList<String> t) {
        System.out.println();
        for (int i = 0; i < 9; i++) {
            String salida = (i + 1) % 3 != 0 ? t.get(i) + "|" : t.get(i) + "\n";
            System.out.print(salida);
        }
    }

    public static void crearTablero(LinkedList<String> t) {
        for (int i = 1; i <= 9; i++) {
            t.add(String.valueOf(i));
        }
    }
}
