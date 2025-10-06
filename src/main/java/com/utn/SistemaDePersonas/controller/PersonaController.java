package com.utn.SistemaDePersonas.controller;


import com.utn.SistemaDePersonas.exception.DniAlreadyExistsException;
import com.utn.SistemaDePersonas.exception.PersonaNoEliminableException;
import com.utn.SistemaDePersonas.models.PersonaDTO;
import com.utn.SistemaDePersonas.models.PersonaModel;
import com.utn.SistemaDePersonas.models.SecurityUser;
import com.utn.SistemaDePersonas.service.PersonaService;
import com.utn.SistemaDePersonas.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PersonaController {

    @Autowired
    PersonaService personaService;
    @Autowired
    UsuarioService usuarioService;

    /**
     * Muestra el formulario de creación de persona.
     *
     * @param model           modelo para la vista
     * @param usuarioLogueado usuario actualmente logueado
     * @return vista agregarPersona.html o login.html si no está autenticado
     */
    @GetMapping("/persona/agregar")
    public String showAgregar(Model model, @AuthenticationPrincipal SecurityUser usuarioLogueado) {

        boolean primerUsuario = !usuarioService.existeAlgunUsuario();

        // Solo permitimos acceder si es el primer usuario o si el usuario logueado es admin
        if (!primerUsuario) {
            if (usuarioLogueado == null || usuarioLogueado.getPriority() != 1) {
                return "/public/login"; // Redirige si no es admin
            }
        }

        PersonaDTO personaDto = new PersonaDTO();
        model.addAttribute("personaDto", personaDto);
        return "/public/agregarPersona";
    }//showAgregar()

    /**
     * Guarda una nueva persona. Lanza excepción si el DNI ya existe.
     *
     * @param personaDto DTO de persona a guardar
     * @param result     resultado de validación
     * @return redirección a primer registro o lista de personas
     */
    @PostMapping("/persona/agregar")
    public String guardarPersona(@Valid @ModelAttribute("personaDto") PersonaDTO personaDto, BindingResult result) {
        boolean primerUsuario = !usuarioService.existeAlgunUsuario();

        if (result.hasErrors()) {
            return "/public/agregarPersona";
        }

        try {
            personaService.crearPersona(personaDto);
        } catch (DniAlreadyExistsException e) {
            result.rejectValue("dni", "error.user", e.getMessage());
            return "/public/agregarPersona";
        }

        // Si es el primer usuario, redirige al registro de usuario
        if (primerUsuario) {
            return "redirect:/public/primerRegistro";
        }
        return "redirect:/public/lista";
    }//guardarPersona()

    /**
     * Muestra formulario para editar una persona existente.
     *
     * @param model modelo para la vista
     * @param id    id de la persona a editar
     * @return vista editarPersona.html
     */
    @GetMapping("/private/persona/edit")
    public String showEditPage(Model model, @RequestParam Long id) {
        PersonaModel persona = personaService.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada"));

        model.addAttribute("persona", persona);

        PersonaDTO personaDto = mapToDTO(persona);
        model.addAttribute("personaDto", personaDto);

        return "/private/editarPersona";
    }//showEditPage()

    /**
     * Edita una persona existente. Lanza excepción si el DNI ya existe.
     *
     * @param model      modelo para la vista
     * @param id         id de la persona a editar
     * @param personaDto datos actualizados de la persona
     * @param result     resultado de validación
     * @return redirección a lista de personas
     */
    @PostMapping("/private/persona/edit")
    public String editarPersona(Model model, @RequestParam Long id,
                                @Valid @ModelAttribute("personaDto") PersonaDTO personaDto,
                                BindingResult result) {
        PersonaModel persona = personaService.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada"));

        if (result.hasErrors()) {
            return "/private/editarPersona";
        }

        try {
            personaService.editarPersona(id, personaDto);
        } catch (DniAlreadyExistsException e) {
            result.rejectValue("dni", "error.user", "El DNI ya existe");
            return "/private/editarPersona";
        }

        return "redirect:/public/lista";
    }//editarPersona()

    /**
     * Elimina una persona. Captura excepción si no se puede eliminar.
     *
     * @param id                 id de la persona a eliminar
     * @param redirectAttributes atributos para pasar mensajes flash a la vista
     * @return redirección a lista de personas
     */
    @GetMapping("/private/persona/eliminar")
    public String eliminarPersona(@RequestParam Long id, RedirectAttributes redirectAttributes) {

        try {
            personaService.eliminarPersona(id);
            redirectAttributes.addFlashAttribute("success", "Persona eliminada correctamente");
        } catch (PersonaNoEliminableException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/public/lista";
    }//eliminarPersona()

    /**
     * Método privado para mapear PersonaModel a PersonaDTO
     *
     * @param persona PersonaModel a mapear
     * @return PersonaDTO con los datos
     */
    private PersonaDTO mapToDTO(PersonaModel persona) {
        PersonaDTO dto = new PersonaDTO();
        dto.setNombre(persona.getNombre());
        dto.setApellido(persona.getApellido());
        dto.setDni(persona.getDni());
        dto.setEmail(persona.getEmail());
        dto.setTelefono(persona.getTelefono());
        dto.setObservaciones(persona.getObservaciones());
        return dto;
    }//mapToDTO()
}

