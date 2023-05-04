package com.agustin.asado.controllers;

import com.agustin.asado.models.entity.Usuario;
import com.agustin.asado.models.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/asado")
public class UsuarioRestController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/usuarios")
    public List<Usuario> index(){
        return usuarioService.findAll();
    }

    @GetMapping("/usuarios/{id}")
    public Usuario show(@PathVariable Long id){
        return usuarioService.findById(id);
    }

    @GetMapping("/transferencias")
    public List<String> transf() {
        List<Usuario> usuarios = usuarioService.findAll();

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
        resultado.add("Promedio: " + Math.floor(promedio));
        // Calculamos las deudas entre usuarios y las imprimimos
        for (Usuario usuarioPositivo : saldosPositivos) {
            for (Usuario usuarioNegativo : saldosNegativos) {
                double diferencia = usuarioPositivo.getDinero() - promedio;
                if (diferencia <= 0) {
                    break;
                }
                if (usuarioNegativo.getDinero() < promedio) {
                    double deuda = Math.min(diferencia, promedio - usuarioNegativo.getDinero());
                    if (deuda > 1) {
                        resultado.add(usuarioNegativo.getNombre() + " le debe pagar $" + Math.floor(deuda) + " a " + usuarioPositivo.getNombre());
                    }
                    usuarioPositivo.setDinero(usuarioPositivo.getDinero() - deuda);
                    usuarioNegativo.setDinero(usuarioNegativo.getDinero() + deuda);
                    diferencia = usuarioPositivo.getDinero() - promedio;
                }
            }
        }
        if (resultado.equals("")) {
            resultado.add("Todos los usuarios han contribuido al dinero promedio");
        }

        return resultado;
    }

    @PostMapping("/usuarios")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario create(@RequestBody Usuario usuario){
        return usuarioService.save(usuario);
    }

    @PutMapping("/usuarios/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario update(@RequestBody Usuario usuario, @PathVariable Long id){
        Usuario usuarioActual = usuarioService.findById(id);
        usuarioActual.setNombre(usuario.getNombre());
        usuarioActual.setDinero(usuario.getDinero());
        usuarioActual.setAlias(usuario.getAlias());

        return usuarioService.save(usuarioActual);
    }

    @DeleteMapping("usuarios/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        usuarioService.delete(id);
    }
}
