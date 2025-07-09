package com.example.trabalho_final;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "filme")
public class Filme {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String titulo;
    private String diretor;
    private int ano;
    private String genero;
    private float nota;

    public Filme(String titulo, String diretor, int ano, String genero, float nota) {
        this.titulo = titulo;
        this.diretor = diretor;
        this.ano = ano;
        this.genero = genero;
        this.nota = nota;
    }

    // Getters
    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDiretor() { return diretor; }
    public int getAno() { return ano; }
    public String getGenero() { return genero; }
    public float getNota() { return nota; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDiretor(String diretor) { this.diretor = diretor; }
    public void setAno(int ano) { this.ano = ano; }
    public void setGenero(String genero) { this.genero = genero; }
    public void setNota(float nota) { this.nota = nota; }
}