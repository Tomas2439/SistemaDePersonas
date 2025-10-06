package com.utn.SistemaDePersonas.models;

/**
 * DTO para información de versión de la API.
 */
public class VersionDTO {
    private String nombreApi;
    private String autores;
    private String version;
    private String fecha;

    // Constructores
    public VersionDTO(String n, String a, String v, String f) {
        setNombreApi(n);
        setAutores(a);
        setVersion(v);
        setFecha(f);
    }

    public VersionDTO() {
    }

    // Getters y setters
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNombreApi() {
        return nombreApi;
    }

    public void setNombreApi(String nombreApi) {
        this.nombreApi = nombreApi;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
