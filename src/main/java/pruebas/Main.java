package pruebas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Usuario> usuarios = new ArrayList<>();

        usuarios.add(new Usuario("Agustin", 1500.0, "agustin.mp"));
        usuarios.add(new Usuario("Oscar", 3000.0, "oscar.mp"));
        usuarios.add(new Usuario("Nacho", 2000.0, "nacho.mp"));
        usuarios.add(new Usuario("Rolando", 1000.0, "rolando.mp"));
        usuarios.add(new Usuario("Vene", 500.0, "vene.mp"));
        usuarios.add(new Usuario("Facu", 3000.0, "facu.mp"));

        double sumaDinero = 0;
        for (Usuario usuario : usuarios) {
            sumaDinero += usuario.getDinero();
        }
        double promedioDinero = sumaDinero / usuarios.size();

        System.out.println(promedioDinero);

        List<Usuario> usuariosConSaldoNegativo = new ArrayList<>();
        List<Usuario> usuariosConSaldoPositivo = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            if (usuario.getDinero() < promedioDinero) {
                usuario.setDinero(usuario.getDinero() - promedioDinero);
                usuariosConSaldoNegativo.add(usuario);
            } else if (usuario.getDinero() > promedioDinero){
                usuario.setDinero(usuario.getDinero() - promedioDinero);
                usuariosConSaldoPositivo.add(usuario);
            }

        }
        System.out.println("---------- Usuarios positivos ----------");
        System.out.println(usuariosConSaldoPositivo);
        System.out.println("---------- Usuarios negativos ----------");
        System.out.println(usuariosConSaldoNegativo);
        System.out.println("---------- fin ----------");

        Collections.sort(usuariosConSaldoPositivo, new Comparator<Usuario>() {
            public int compare(Usuario u1, Usuario u2) {
                return Double.compare(u2.getDinero(), u1.getDinero());
            }
        });
        Collections.sort(usuariosConSaldoNegativo, new Comparator<Usuario>() {
            public int compare(Usuario u1, Usuario u2) {
                return Double.compare(u1.getDinero(), u2.getDinero());
            }
        });
        System.out.println("---------- Usuarios + ordenados ----------");
        System.out.println(usuariosConSaldoPositivo);
        System.out.println("---------- Usuarios - ordenados ----------");
        System.out.println(usuariosConSaldoNegativo);

        String resultado = "";
        double resto = 0;
            int i = 0;
            int j = 0;

            forExterno:
            for (i = 0 ; i < usuariosConSaldoPositivo.size() ; i++){
                double saldoPositivoI = usuariosConSaldoPositivo.get(i).getDinero();
                if ( i > 1) {
                    saldoPositivoI = resto;
                    resto = 0;
                }
                while (saldoPositivoI > 0){
                    double saldoNegativoJ = usuariosConSaldoNegativo.get(j).getDinero() * -1;
                    if ( i >= 1) {
                        saldoNegativoJ = resto;
                    }
                    if (saldoPositivoI > saldoNegativoJ) {
                    resto = saldoPositivoI - saldoNegativoJ;
                    resultado += usuariosConSaldoNegativo.get(j).getNombre() + " le debe: " + saldoNegativoJ + " a " +
                            usuariosConSaldoPositivo.get(i).getNombre() + "\n";
                    saldoPositivoI = resto;
                    j++;
                    continue;
                    }
                    if (saldoPositivoI <= saldoNegativoJ) {
                        resto = saldoNegativoJ - saldoPositivoI;
                        saldoNegativoJ = resto;
                        resultado += usuariosConSaldoNegativo.get(j).getNombre() + " le debe: " + saldoPositivoI + " a " +
                                usuariosConSaldoPositivo.get(i).getNombre() + "\n";
                        continue forExterno;
                    }
                }
            }

        if (resultado.equals("")) {
            resultado = "Todos los usuarios han contribuido al dinero promedio";
        }

        System.out.println(resultado);

    }
}
