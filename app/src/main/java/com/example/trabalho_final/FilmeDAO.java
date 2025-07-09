package com.example.trabalho_final;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FilmeDAO {
    @Insert
    void inserir(Filme filme);

    @Update
    void atualizar(Filme filme);

    @Delete
    void remover(Filme filme);

    @Query("SELECT * FROM filme ORDER BY titulo ASC")
    LiveData<List<Filme>> listar();

    @Query("SELECT * FROM filme WHERE id = :id LIMIT 1")
    Filme SelectId(int id);
}