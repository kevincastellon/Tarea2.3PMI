package com.example.tarea23pmi.config;

public class Fotos {
    private byte[] foto;
    private String id = "id", descripcion = "descripcion";

    public Fotos() {
        this.foto = foto;
        this.id = id;
        this.descripcion = descripcion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
