package com.utn.SistemaDePersonas.controller;

import com.utn.SistemaDePersonas.exception.UserNoEliminableException;
import com.utn.SistemaDePersonas.exception.UsernameAlreadyExistsException;
import com.utn.SistemaDePersonas.models.*;
import com.utn.SistemaDePersonas.service.UsuarioService;
import com.utn.SistemaDePersonas.service.PersonaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    PersonaService personaService;

    /**
     * Muestra la página de login público.
     *
     * @return vista login.html
     */
    @GetMapping("/public/login")
    public String login() {
        return "/public/login"; //login.html
    }

    /**
     * Muestra el formulario de registro de usuario.
     *
     * @param model modelo para la vista
     * @param id    id de la persona asociada
     * @return vista register.html
     */
    @GetMapping("/private/register")
    public String mostrarFormularioRegistro(Model model,
                                            @RequestParam Long id) {

        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setPersonaId(id);
        model.addAttribute("usuario", usuario);
        return "private/register";
    }//mostrarFormularioRegistro()

    /**
     * Registra un usuario nuevo.
     *
     * @param dto    DTO con los datos del usuario
     * @param result resultado de validación
     * @param model  modelo para la vista
     * @return redirección a lista de usuarios o formulario con errores
     */
    @PostMapping("/private/register")
    public String registerUser(@Valid @ModelAttribute("usuario") UsuarioDTO dto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "private/register";
        }

        try {
            usuarioService.registrarUsuario(dto);
        } catch (UsernameAlreadyExistsException e) {
            result.rejectValue("username", "error.user", e.getMessage());
            return "private/register";
        }

        return "redirect:/private/usuarios?id=" + dto.getPersonaId();
    }//registerUser()

    /**
     * Lista los usuarios asociados a una persona.
     *
     * @param id    id de la persona
     * @param model modelo para la vista
     * @return vista listaUsuarios.html
     */
    @GetMapping("/private/usuarios")
    public String listarUsuarios(@RequestParam Long id, Model model) {
        PersonaModel persona = personaService.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada"));

        model.addAttribute("persona", persona);
        model.addAttribute("usuarios", persona.getUsuarios());
        return "/private/listaUsuarios";
    }//listarUsuarios()

    /**
     * Elimina un usuario por id.
     *
     * @param id id del usuario a eliminar
     * @return redirección a la lista de usuarios de la misma persona
     */
    @GetMapping("/private/usuarios/eliminar")
    public String eliminarUsuario(@RequestParam Long id, RedirectAttributes redirectAttributes) {

        User user = usuarioService.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Long idPersona = user.getPersona().getId();

        try {
            usuarioService.eliminarUsuario(id);
            redirectAttributes.addFlashAttribute("success", "Usuario eliminado correctamente");
        } catch (UserNoEliminableException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/private/usuarios?id=" + idPersona;
    }//eliminarUsuario()

    /**
     * Cambia la prioridad/admin de un usuario.
     *
     * @param id id del usuario
     * @return redirección a la lista de usuarios de la misma persona
     */
    @PostMapping("/private/usuarios/toggleAdmin")
    public String toggleAdmin(@RequestParam Long id) {
        usuarioService.toggleAdmin(id);
        Long personaId = usuarioService.devolverIdPersona(id);
        return "redirect:/private/usuarios?id=" + personaId;
    }//toggleAdmin()

    /**
     * Muestra el formulario de edición de la cuenta del usuario.
     *
     * @param model           modelo para la vista
     * @param usuarioLogueado usuario actualmente logueado
     * @return vista editarUsuario.html
     */
    @GetMapping("/cuenta")
    public String showEditPage(Model model, @AuthenticationPrincipal SecurityUser usuarioLogueado) {
        UsuarioEditDTO usuarioDto = new UsuarioEditDTO();
        usuarioDto.setUsername(usuarioLogueado.getUsername());

        model.addAttribute("usuarioDto", usuarioDto);

        return "/editarUsuario";
    }//showEditPage()

    /**
     * Edita la información de la cuenta del usuario.
     *
     * @param usuarioDto      DTO con los nuevos datos
     * @param result          resultado de validación
     * @param model           modelo para la vista
     * @param usuarioLogueado usuario actualmente logueado
     * @return redirección a login tras logout si todo OK
     */
    @PostMapping("/cuenta")
    public String editarUsuario(@Valid @ModelAttribute("usuarioDto") UsuarioEditDTO usuarioDto,
                                BindingResult result,
                                Model model,
                                @AuthenticationPrincipal SecurityUser usuarioLogueado) {

        if (result.hasErrors()) {
            return "/editarUsuario";
        }

        try {
            usuarioService.editarUsuario(usuarioDto, usuarioLogueado);
        } catch (UsernameAlreadyExistsException e) {
            result.rejectValue("username", "error.user", e.getMessage());
            return "/editarUsuario";
        }

        return "redirect:/public/login?logout";
    }//editarUsuario()

    /**
     *  Muestra el formulario de primer registro de usuario (cuando no hay usuarios aún).
     *
     *  @param model modelo para la vista
     *  @param usuarioLogueado usuario actualmente logueado
     *  @return vista primerRegistro.html o login.html
     */
    @GetMapping("/public/primerRegistro")
    public String mostrarFormularioRegistro(Model model, @AuthenticationPrincipal SecurityUser usuarioLogueado) {

        boolean primerUsuario = !usuarioService.existeAlgunUsuario();

        if (!primerUsuario) {
            return "/public/home";
        }
        UsuarioDTO usuario = new UsuarioDTO();

        List<PersonaModel> personas = personaService.obtenerPersonas();

        model.addAttribute("usuario", usuario);
        model.addAttribute("personas", personas);
        return "/public/primerRegistro";
    }//mostrarFormularioRegistro()


    /**
     * Registra el primer usuario del sistema.
     *
     * @param dto    DTO con los datos del usuario
     * @param result resultado de validación
     * @param model  modelo para la vista
     * @return redirección a login o formulario con errores
     */
    @PostMapping("/public/primerRegistro")
    public String registerFirstUser(@Valid @ModelAttribute("usuario") UsuarioDTO dto,
                                    BindingResult result,
                                    Model model) {

        List<PersonaModel> personas = personaService.obtenerPersonas();
        model.addAttribute("personas", personas);

        if (result.hasErrors()) {
            return "public/primerRegistro";
        }

        if (personas.isEmpty()) {
            result.rejectValue("personaId", "error.user", "Debe agregar al menos una persona antes de crear un usuario");
            return "public/primerRegistro";
        }

        try {
            usuarioService.registrarUsuario(dto);
        } catch (UsernameAlreadyExistsException e) {
            result.rejectValue("username", "error.user", e.getMessage());
            return "public/primerRegistro";
        }

        return "redirect:/public/login";
    }//registerFirstUser()
}
