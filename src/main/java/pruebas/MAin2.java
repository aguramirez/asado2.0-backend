package pruebas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MAin2 {
        public static void main(String[] args) {

            // Creamos una lista de usuarios
            List<Usuario> usuarios = new ArrayList<>();
            usuarios.add(new Usuario("Agustin", 1500.0, "agustin.mp"));
            usuarios.add(new Usuario("Oscar", 3000.0, "oscar.mp"));
            usuarios.add(new Usuario("Nacho", 2000.0, "nacho.mp"));
            usuarios.add(new Usuario("Rolando", 1000.0, "rolando.mp"));
            usuarios.add(new Usuario("Vene", 500.0, "vene.mp"));
            usuarios.add(new Usuario("Facu", 3000.0, "facu.mp"));

            // Calculamos el promedio de dinero gastado por usuario
            double promedio = usuarios.stream()
                    .mapToDouble(Usuario::getDinero)
                    .average()
                    .orElse(0);

            // Separamos los usuarios con saldo positivo y negativo en dos listas
            List<Usuario> saldosPositivos = new ArrayList<>();
            List<Usuario> saldosNegativos = new ArrayList<>();

            for (Usuario usuario : usuarios) {
                if (usuario.getDinero() >= promedio) {
                    saldosPositivos.add(usuario);
                } else {
                    saldosNegativos.add(usuario);
                }
            }

            // Ordenamos las listas de usuarios con saldo positivo y negativo de mayor a menor
            Collections.sort(saldosPositivos, Comparator.comparing(Usuario::getDinero).reversed());
            Collections.sort(saldosNegativos, Comparator.comparing(Usuario::getDinero).reversed());

            List<String> resultado = new ArrayList<>();

            // Calculamos las deudas entre usuarios y las imprimimos
            for (Usuario usuarioPositivo : saldosPositivos) {
                for (Usuario usuarioNegativo : saldosNegativos) {
                    double diferencia = usuarioPositivo.getDinero() - promedio;
                    if (diferencia <= 0) {
                        break;
                    }
                    if (usuarioNegativo.getDinero() < promedio) {
                        double deuda = Math.min(diferencia, promedio - usuarioNegativo.getDinero());
                        resultado.add(usuarioNegativo.getNombre() + " le debe pagar $" + deuda + " a " + usuarioPositivo.getNombre());
                        usuarioPositivo.setDinero(usuarioPositivo.getDinero() - deuda);
                        usuarioNegativo.setDinero(usuarioNegativo.getDinero() + deuda);
                        diferencia = usuarioPositivo.getDinero() - promedio;
                    }
                }
            }
            if (resultado.equals("")) {
                resultado.add("Todos los usuarios han contribuido al dinero promedio");
            }
        }

    }
