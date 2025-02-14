package com.example.covidslotbooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.embersoft.expandabletextview.ExpandableTextView;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<item> items;
    private Context context;
    onClick onClick;

    public Adapter(ArrayList<item> items,onClick onClick) {
        this.items = items;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,
                parent,false);
        ViewHolder viewHolder = new ViewHolder(view,onClick);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, final int position) {

        final item item = items.get(position);
        holder.titleTextView.setText(item.getTitle());
        holder.descTextView.setText(item.getDescription());
        holder.descTextView.setOnStateChangeListener(new ExpandableTextView.OnStateChangeListener() {
            @Override
            public void onStateChange(boolean isShrink) {
                item contentItem = items.get(position);
                contentItem.setShrink(isShrink);
                items.set(position, contentItem);
            }
        });
        holder.descTextView.setText(item.getDescription());
        holder.descTextView.resetState(item.isShrink());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        ExpandableTextView descTextView;
        TextView titleTextView;
        onClick onClick;
        public ViewHolder(@NonNull View itemView , onClick onClick) {
            super(itemView);
            descTextView = itemView.findViewById(R.id.descTextView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
        }

        @Override
        public void onClick(View v) {
        onClick.onClick(getAdapterPosition());
        }
    }
    public interface onClick{
        void onClick(int position);
    }
}
