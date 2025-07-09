package com.example.trabalho_final;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.trabalho_final.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FilmeAdapter.OnFilmeClickListener {

    private ActivityMainBinding binding;
    private FilmeAdapter adapter;
    private FilmesRepository filmesRepository;
    private List<Filme> cacheFilmes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        filmesRepository = new FilmesRepository(getApplication());
        cacheFilmes = new ArrayList<>();

        configurarRecyclerView();
        observarFilmes();
        configurarFab();
        configurarSwipeParaRemover();
    }

    private void configurarRecyclerView() {
        binding.contentMain.recyclerViewFilmes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FilmeAdapter(cacheFilmes);
        adapter.setOnFilmeClickListener(this);
        binding.contentMain.recyclerViewFilmes.setAdapter(adapter);
    }

    private void observarFilmes() {
        filmesRepository.getTodosOsFilmes().observe(this, new Observer<List<Filme>>() {
            @Override
            public void onChanged(List<Filme> filmes) {
                cacheFilmes.clear();
                cacheFilmes.addAll(filmes);
                adapter.setFilmes(filmes);
            }
        });
    }

    private void configurarFab() {
        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddFilmeActivity.class);
            startActivity(intent);
        });
    }

    private void configurarSwipeParaRemover() {
        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            private final Drawable icon = ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_delete_24);
            private final ColorDrawable background = new ColorDrawable(Color.RED);

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Filme filmeParaRemover = cacheFilmes.get(position);
                filmesRepository.remover(filmeParaRemover);

                Snackbar.make(binding.getRoot(), "Filme removido", Snackbar.LENGTH_LONG)
                        .setAction("DESFAZER", v -> filmesRepository.inserir(filmeParaRemover))
                        .show();
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                View itemView = viewHolder.itemView;
                int backgroundCornerOffset = 20;

                int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                int iconBottom = iconTop + icon.getIntrinsicHeight();

                if (dX > 0) {
                    int iconLeft = itemView.getLeft() + iconMargin;
                    int iconRight = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                    background.setBounds(itemView.getLeft(), itemView.getTop(),
                            itemView.getLeft() + ((int) dX) + backgroundCornerOffset, itemView.getBottom());
                } else if (dX < 0) {
                    int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
                    int iconRight = itemView.getRight() - iconMargin;
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                    background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                            itemView.getTop(), itemView.getRight(), itemView.getBottom());
                } else { // Sem deslizar
                    background.setBounds(0, 0, 0, 0);
                }

                background.draw(c);
                icon.draw(c);
            }
        };

        new ItemTouchHelper(itemTouchCallback).attachToRecyclerView(binding.contentMain.recyclerViewFilmes);
    }

    @Override
    public void onFilmeClick(Filme filme) {
        Intent intent = new Intent(MainActivity.this, EditFilmeActivity.class);
        intent.putExtra("filme_id", filme.getId());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_sobre) {
            startActivity(new Intent(MainActivity.this, SobreActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}