package com.ykatakuri.footx.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ykatakuri.footx.R;
import com.ykatakuri.footx.model.Field;

import java.util.List;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<FieldViewHolder> {

    private List<Field> mFieldList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public CustomRecyclerViewAdapter(Context context, List<Field> datas ) {
        this.mContext = context;
        this.mFieldList = datas;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public FieldViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View recyclerViewItem = mLayoutInflater.inflate(R.layout.recyclerview_item_layout, parent, false);

        recyclerViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRecyclerItemClick( (RecyclerView)parent, v);
            }
        });
        return new FieldViewHolder(recyclerViewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull FieldViewHolder holder, int position) {
        Field field = this.mFieldList.get(position);

        int imageResId = this.getDrawableResIdByName(field.getImageName());

        holder.mFieldImage.setImageResource(imageResId);
        holder.mFieldName.setText(field.getFieldName());
        holder.mFieldDescription.setText(field.getDescription());
    }

    @Override
    public int getItemCount() {
        return this.mFieldList.size();
    }

    public int getDrawableResIdByName(String resName)  {
        String pkgName = mContext.getPackageName();
        // Return 0 if not found.
        int resID = mContext.getResources().getIdentifier(resName , "drawable", pkgName);
        //Log.i(CreateFootFragment.LOG_TAG, "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }

    // Click on RecyclerView Item.
    public void handleRecyclerItemClick(RecyclerView recyclerView, View itemView) {
        int itemPosition = recyclerView.getChildLayoutPosition(itemView);
        Field field  = this.mFieldList.get(itemPosition);

        Toast.makeText(this.mContext, field.getFieldName(), Toast.LENGTH_LONG).show();
    }
}
