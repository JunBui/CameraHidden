package com.example.cameradetector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TrickSliderAdapter extends RecyclerView.Adapter<TrickSliderAdapter.ViewHolder> {
    private final List<TricksSlideItem> sliderItems;
    private final Context context;

    public TrickSliderAdapter(Context context, List<TricksSlideItem> sliderItems) {
        this.context = context;
        this.sliderItems = sliderItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ui_item_slider, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TricksSlideItem item = sliderItems.get(position);
        holder.customTutorialItem.setDescription(context.getString(item.getDescription()));
        holder.customTutorialItem.setOrderText((position+1)+".");
        if (item.getImageRes() == 0) {
            holder.customTutorialItem.setDescriptionImage(0); // Set ảnh rỗng
            holder.customTutorialItem.getDescriptionImage().setVisibility(View.GONE);
        } else {
            holder.customTutorialItem.setDescriptionImage(item.getImageRes());
            holder.customTutorialItem.getDescriptionImage().setVisibility(View.VISIBLE);
        }
        item.getImageAndTitles();


        ImageAndTitleAdapter adapter = new ImageAndTitleAdapter(context,item.getImageAndTitles());

        holder.customTutorialItem.recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        holder.customTutorialItem.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CustomTrickItem customTutorialItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            customTutorialItem = itemView.findViewById(R.id.customTrickItem);
        }
    }
}
