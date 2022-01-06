package com.ykatakuri.footx.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.ykatakuri.footx.R;
import com.ykatakuri.footx.adapter.CustomRecyclerViewAdapter;
import com.ykatakuri.footx.model.Field;

import java.util.ArrayList;
import java.util.List;

public class FieldActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);

        List<Field> fieldList = getListData();
        mRecyclerView = (RecyclerView) this.findViewById(R.id.field_recyclerview);
        mRecyclerView.setAdapter(new CustomRecyclerViewAdapter(this, fieldList));

        // RecyclerView scroll vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private List<Field> getListData() {
        List<Field> list = new ArrayList<Field>();
        Field borderouge = new Field("field", "Borderouge", "Terrain synthétique", "");
        Field struxiano = new Field("field", "Struxiano", "Terrain synthétique", "");

        list.add(borderouge);
        list.add(struxiano);

        return list;
    }
}