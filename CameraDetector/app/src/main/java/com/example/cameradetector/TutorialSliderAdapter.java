package com.example.cameradetector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cameradetector.R;

import java.util.List;

public class TutorialSliderAdapter extends RecyclerView.Adapter<TutorialSliderAdapter.ViewHolder> {
    private final List<TutorialSlideItem> sliderItems;
    private final Context context;

    public TutorialSliderAdapter(Context context, List<TutorialSlideItem> sliderItems) {
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
        TutorialSlideItem item = sliderItems.get(position);
        holder.customTutorialItem.setDescription(context.getString(item.getDescription()));
        holder.customTutorialItem.setTitleText(context.getString(item.getTitle()));
        holder.customTutorialItem.setDescriptionImage(item.getImageRes());
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CustomTutorialItem customTutorialItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            customTutorialItem = itemView.findViewById(R.id.customTutorialItem);
        }
    }
}
