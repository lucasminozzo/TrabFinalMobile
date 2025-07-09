package com.example.trabalho_final;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.trabalho_final.databinding.ItemFilmeBinding;
import java.util.List;
import java.util.Locale;

public class FilmeAdapter extends RecyclerView.Adapter<FilmeAdapter.FilmeViewHolder> {

    private List<Filme> filmeList;
    private OnFilmeClickListener listener;

    public interface OnFilmeClickListener {
        void onFilmeClick(Filme filme);
    }

    public void setOnFilmeClickListener(OnFilmeClickListener listener) {
        this.listener = listener;
    }

    public FilmeAdapter(List<Filme> filmeList) {
        this.filmeList = filmeList;
    }

    public void setFilmes(List<Filme> filmes) {
        this.filmeList = filmes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FilmeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemFilmeBinding binding = ItemFilmeBinding.inflate(layoutInflater, parent, false);
        return new FilmeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmeViewHolder holder, int position) {
        Filme filme = filmeList.get(position);
        holder.bind(filme);
    }

    @Override
    public int getItemCount() {
        return filmeList != null ? filmeList.size() : 0;
    }

    class FilmeViewHolder extends RecyclerView.ViewHolder {
        private final ItemFilmeBinding binding;

        public FilmeViewHolder(@NonNull ItemFilmeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onFilmeClick(filmeList.get(position));
                }
            });
        }

        public void bind(Filme filme) {
            binding.txtTitulo.setText(filme.getTitulo());
            String diretorAno = String.format(Locale.getDefault(), "%s (%d)", filme.getDiretor(), filme.getAno());
            binding.txtDiretorAno.setText(diretorAno);
            binding.txtGenero.setText(filme.getGenero());
            binding.ratingBarIndicator.setRating(filme.getNota());
        }
    }
}