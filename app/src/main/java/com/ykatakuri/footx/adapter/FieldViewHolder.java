package com.ykatakuri.footx.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ykatakuri.footx.R;

public class FieldViewHolder extends RecyclerView.ViewHolder {
    ImageView mFieldImage;
    TextView mFieldName, mFieldDescription;

    // @itemView: recyclerview_item_layout.xml
    public FieldViewHolder(@NonNull View itemView) {
        super(itemView);

        this.mFieldImage = (ImageView) itemView.findViewById(R.id.field_imageview);
        this.mFieldName = (TextView) itemView.findViewById(R.id.field_textview_name);
        this.mFieldDescription = (TextView) itemView.findViewById(R.id.field_textview_description);
    }
}
