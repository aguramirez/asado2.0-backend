package pruebas;

import java.math.BigDecimal;

public class Usuario {

    private String nombre;
    private Double dinero;
    private String alias;

    public Usuario(String nombre, Double dinero, String alias) {
        this.nombre = nombre;
        this.dinero = dinero;
        this.alias = alias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getDinero() {
        return dinero;
    }

    public void setDinero(Double dinero) {
        this.dinero = dinero;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return nombre + " " +dinero;
    }
}
