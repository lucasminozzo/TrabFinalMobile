package com.example.trabalho_final;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FilmesRepository {

    private final FilmeDAO mFilmeDao;
    private final LiveData<List<Filme>> mTodosOsFilmes;
    private final ExecutorService databaseWriteExecutor = Executors.newSingleThreadExecutor();

    public FilmesRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mFilmeDao = db.filmeDAO();
        mTodosOsFilmes = mFilmeDao.listar();
    }

    public LiveData<List<Filme>> getTodosOsFilmes() {
        return mTodosOsFilmes;
    }

    public void inserir(Filme filme) {
        databaseWriteExecutor.execute(() -> {
            mFilmeDao.inserir(filme);
        });
    }

    public void atualizar(Filme filme) {
        databaseWriteExecutor.execute(() -> {
            mFilmeDao.atualizar(filme);
        });
    }

    public void remover(Filme filme) {
        databaseWriteExecutor.execute(() -> {
            mFilmeDao.remover(filme);
        });
    }

    public void findFilmeById(int id, OnFilmeResultCallback callback) {
        databaseWriteExecutor.execute(() -> {
            Filme filme = mFilmeDao.SelectId(id);
            callback.onResult(filme);
        });
    }

    public interface OnFilmeResultCallback {
        void onResult(Filme filme);
    }
}