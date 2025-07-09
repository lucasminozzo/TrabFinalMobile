package com.example.trabalho_final;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.trabalho_final.databinding.ActivityEditFilmeBinding;

public class EditFilmeActivity extends AppCompatActivity {

    private ActivityEditFilmeBinding binding;
    private FilmesRepository filmesRepository;
    private Filme filmeParaEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditFilmeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        filmesRepository = new FilmesRepository(getApplication());
        setTitle("Editar Filme");

        int filmeId = getIntent().getIntExtra("filme_id", -1);

        if (filmeId != -1) {
            carregarDadosDoFilme(filmeId);
        }

        binding.btnAtualizar.setOnClickListener(v -> atualizarFilme());
    }

    private void carregarDadosDoFilme(int id) {

        filmesRepository.findFilmeById(id, filme -> {
            filmeParaEditar = filme;
            if (filmeParaEditar != null) {
                runOnUiThread(() -> {
                    binding.edtTitulo.setText(filmeParaEditar.getTitulo());
                    binding.edtDiretor.setText(filmeParaEditar.getDiretor());
                    binding.edtAno.setText(String.valueOf(filmeParaEditar.getAno()));
                    binding.edtGenero.setText(filmeParaEditar.getGenero());
                    binding.ratingBarInput.setRating(filmeParaEditar.getNota());
                });
            }
        });
    }

    private void atualizarFilme() {
        if (filmeParaEditar == null) return;

        String titulo = binding.edtTitulo.getText().toString();
        String diretor = binding.edtDiretor.getText().toString();
        String anoStr = binding.edtAno.getText().toString();
        String genero = binding.edtGenero.getText().toString();
        float nota = binding.ratingBarInput.getRating();

        if (TextUtils.isEmpty(titulo) || TextUtils.isEmpty(diretor) || TextUtils.isEmpty(anoStr) || TextUtils.isEmpty(genero)) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        filmeParaEditar.setTitulo(titulo);
        filmeParaEditar.setDiretor(diretor);
        filmeParaEditar.setAno(Integer.parseInt(anoStr));
        filmeParaEditar.setGenero(genero);
        filmeParaEditar.setNota(nota);

        filmesRepository.atualizar(filmeParaEditar);

        Toast.makeText(this, "Filme atualizado!", Toast.LENGTH_SHORT).show();
        finish();
    }
}