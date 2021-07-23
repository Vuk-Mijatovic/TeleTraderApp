package com.example.teletraderapp;

import android.content.Context;
import android.graphics.Color;
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
    private  OnItemClickListener clickListener;

    public SymbolAdapter(List<Symbol> symbols, OnItemClickListener clickListener) {
        this.symbols = symbols;
        this.clickListener = clickListener;
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
        if (currentSymbol.getChg() != Double.MIN_VALUE) {
            holder.changeView.setText(String.format("%.2f", (currentSymbol.getChg())) + "%");
            if (currentSymbol.getChg() > 0) {
                holder.changeView.setTextColor(Color.parseColor("#1faa00"));
                holder.changeView.setText("+" + String.format("%.2f", (currentSymbol.getChg())) + "%");
            }
            else if (currentSymbol.getChg() < 0) holder.changeView.setTextColor(Color.RED);
            else holder.changeView.setTextColor(Color.WHITE);
        }
        else {
            holder.changeView.setText("-");
            holder.changeView.setTextColor(Color.WHITE);
        }
        if (currentSymbol.getLast() != Double.MIN_VALUE)
            holder.lastView.setText(String.format("%.2f",(currentSymbol.getLast())));
        else holder.lastView.setText("-");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(currentSymbol);
            }
        });
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

    public interface OnItemClickListener {
        void onItemClick(Symbol item);
    }
}
