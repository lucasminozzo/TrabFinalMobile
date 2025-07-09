package com.example.trabalho_final;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.trabalho_final.databinding.ActivityAddFilmeBinding;

public class AddFilmeActivity extends AppCompatActivity {

    private ActivityAddFilmeBinding binding;
    private FilmesRepository filmesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddFilmeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        filmesRepository = new FilmesRepository(getApplication());
        setTitle("Adicionar Filme");

        binding.btnSalvar.setOnClickListener(v -> salvarFilme());
    }

    private void salvarFilme() {
        String titulo = binding.edtTitulo.getText().toString();
        String diretor = binding.edtDiretor.getText().toString();
        String anoStr = binding.edtAno.getText().toString();
        String genero = binding.edtGenero.getText().toString();
        float nota = binding.ratingBarInput.getRating();

        if (TextUtils.isEmpty(titulo) || TextUtils.isEmpty(diretor) || TextUtils.isEmpty(anoStr) || TextUtils.isEmpty(genero)) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        int ano = Integer.parseInt(anoStr);
        Filme novoFilme = new Filme(titulo, diretor, ano, genero, nota);

        filmesRepository.inserir(novoFilme);

        Toast.makeText(this, "Filme salvo com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }
}