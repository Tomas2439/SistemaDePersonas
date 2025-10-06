package com.utn.SistemaDePersonas.controller;

import com.utn.SistemaDePersonas.models.PersonaModel;
import com.utn.SistemaDePersonas.models.VersionDTO;
import com.utn.SistemaDePersonas.service.PersonaService;
import com.utn.SistemaDePersonas.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @Autowired
    PersonaService personaService;

    @Autowired
    UsuarioService usuarioService;

    /**
     * Redirige la raíz del proyecto a la página de inicio pública.
     *
     * @return ruta de redirección a home
     */
    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/public/home";
    }

    /**
     * Página de inicio pública.
     *
     * @return vista home.html
     */
    @GetMapping("/public/home")
    public String home() {
        return "/public/home";
    }//home()

    /**
     * Lista todas las personas registradas.
     *
     * @param model modelo para pasar datos a la vista
     * @return vista lista.html
     */
    @GetMapping("/public/lista")
    public String listar(Model model) {
        List<PersonaModel> personas = personaService.obtenerPersonas();
        model.addAttribute("personas", personas);

        return "/public/lista";
    }//listar()

    /**
     * Obtiene la versión del sistema y autores.
     *
     * @param model modelo para pasar datos a la vista
     * @return vista version.html
     */
    @GetMapping("/public/version")
    public String obtenerVersion(Model model) {
        VersionDTO version = personaService.getVersion();
        model.addAttribute("version", version);
        return "/public/version";
    }//obtenerVersion()

    /**
     * Página de acceso denegado.
     *
     * @return vista access-denied.html
     */
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }//accessDenied()
}