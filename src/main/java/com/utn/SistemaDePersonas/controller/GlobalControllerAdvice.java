package com.utn.SistemaDePersonas.controller;

import com.utn.SistemaDePersonas.models.SecurityUser;
import com.utn.SistemaDePersonas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Agrega el usuario actualmente logueado al modelo para todas las vistas.
     *
     * @param model modelo para pasar datos a la vista
     */
    @ModelAttribute
    public void agregarUsuarioLogueado(@AuthenticationPrincipal SecurityUser usuario, Model model) {
        if (usuario != null) {
            model.addAttribute("usuarioLogueado", usuario);
        }
    }//agregarUsuarioLogueado()

    /**
     * Agrega al modelo un indicador de si no hay usuarios registrados.
     *
     * @param model modelo para pasar datos a la vista
     */
    @ModelAttribute
    public void agregarPrimerUsuario(Model model) {
        boolean primerUsuario = !usuarioService.existeAlgunUsuario();
        model.addAttribute("primerUsuario", primerUsuario);
    }//agregarPrimerUsuario()
}