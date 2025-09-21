package com.example.belajardatabase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LaptopAdapter extends RecyclerView.Adapter<LaptopAdapter.ViewHolder> {
    private List<Laptop> laptopList;
    private Context context;
    private OnLaptopClickListener listener;
    private DatabaseHelper db;

    public interface OnLaptopClickListener {
        void onLaptopClick(Laptop laptop);
    }

    public LaptopAdapter(List<Laptop> laptopList, Context context, OnLaptopClickListener listener) {
        this.laptopList = laptopList;
        this.context = context;
        this.listener = listener;
        this.db = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laptop, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Laptop l = laptopList.get(position);
        holder.tvMerkModel.setText(l.getMerk() + " " + l.getModel());
        holder.tvProcessor.setText("Processor: " + l.getProcessor());
        holder.tvRamStorage.setText(l.getRam() + ", " + l.getStorage());
        holder.tvHarga.setText("Rp " + String.format("%.0f", l.getHarga()));

        if (l.getGambar1() != null && l.getGambar1().length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(l.getGambar1(), 0, l.getGambar1().length);
            holder.imgGambar.setImageBitmap(bitmap);
        }

        // Klik normal → edit
        holder.itemView.setOnClickListener(v -> listener.onLaptopClick(l));

        // Long click → menu konteks
        holder.itemView.setOnLongClickListener(v -> {
            PopupMenu popup = new PopupMenu(context, v);
            popup.inflate(R.menu.popup_laptop);
            popup.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.action_update) {
                    listener.onLaptopClick(l);
                    return true;
                } else if (itemId == R.id.action_delete) {
                    db.deleteLaptop(l.getId());
                    laptopList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Data dihapus", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            });
            popup.show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return laptopList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgGambar;
        TextView tvMerkModel, tvProcessor, tvRamStorage, tvHarga;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGambar = itemView.findViewById(R.id.imgItemGambar);
            tvMerkModel = itemView.findViewById(R.id.tvItemMerkModel);
            tvProcessor = itemView.findViewById(R.id.tvItemProcessor);
            tvRamStorage = itemView.findViewById(R.id.tvItemRamStorage);
            tvHarga = itemView.findViewById(R.id.tvItemHarga);
        }
    }
}