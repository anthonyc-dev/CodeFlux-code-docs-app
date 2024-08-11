package com.example.codeflux;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import java.util.List;

public class ImgSliderAdapter extends PagerAdapter {
    private Context mContext;
    private List<ImageModel> mImages;

    public ImgSliderAdapter(Context context, List<ImageModel> images) {
        mContext = context;
        mImages = images;
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_image_slider, container, false);

        ImageView imageView = itemView.findViewById(R.id.imageView);
        TextView descriptionTextView = itemView.findViewById(R.id.descriptionTextView);

        ImageModel imageModel = mImages.get(position);
        imageView.setImageResource(imageModel.getImageResource());
        descriptionTextView.setText(imageModel.getDescription());

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}