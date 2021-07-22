package com.example.teletraderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.zip.Inflater;

public class SymbolAdapter extends RecyclerView.Adapter<SymbolAdapter.SymbolViewHolder> {

    List<Symbol> symbols;
    Symbol currentSymbol;

    public SymbolAdapter(List<Symbol> symbols) {
        this.symbols = symbols;
    }

    @NonNull
    @NotNull
    @Override
    public SymbolViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new SymbolViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SymbolAdapter.SymbolViewHolder holder, int position) {
        currentSymbol = symbols.get(position);
        holder.nameView.setText(currentSymbol.getName());
        if (currentSymbol.getChg() != Double.MIN_VALUE)
            holder.changeView.setText(String.valueOf(currentSymbol.getChg()));
        else holder.changeView.setText("-");
        if (currentSymbol.getLast() != Double.MIN_VALUE)
            holder.lastView.setText(String.valueOf(currentSymbol.getLast()));
        else holder.lastView.setText("-");
    }

    @Override
    public int getItemCount() {
        return symbols.size();
    }

    class SymbolViewHolder extends RecyclerView.ViewHolder {
        private TextView nameView;
        private TextView changeView;
        private TextView lastView;

        public SymbolViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.nameView = itemView.findViewById(R.id.name);
            this.changeView = itemView.findViewById(R.id.change);
            this.lastView = itemView.findViewById(R.id.last);
        }
    }
}
