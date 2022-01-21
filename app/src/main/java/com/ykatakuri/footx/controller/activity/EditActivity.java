package com.ykatakuri.footx.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ykatakuri.footx.R;
import com.ykatakuri.footx.model.Events;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {

    private String[] field_items = {"Borderouge", "Struxiano", "Argoulets", "Bagatelle"};

    private AutoCompleteTextView autoCompleteTextView;

    private ArrayAdapter<String> adapterItems;

    private Button mUpdateButton, mDeleteButton;
    private TextView mDateTextView, mTimeTextView, mAuthorTextView;
    private EditText mPhoneEditText;
    private RadioGroup mFormatRadioGroup;
    private RadioButton mFormatOptionRadioButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    private ProgressBar mProgressBar;

    private String author, field, phone, date, time, format, eventID;
    private Events event;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        autoCompleteTextView = findViewById(R.id.edit_textview_autocomplete);
        mAuthorTextView = findViewById(R.id.edit_textview_author);
        mPhoneEditText = findViewById(R.id.edit_edittext_phone);
        mDateTextView = findViewById(R.id.edit_textview_datepicker);
        mTimeTextView = findViewById(R.id.edit_textview_timepicker);
        mFormatRadioGroup = findViewById(R.id.edit_radiogroup_format);
        mUpdateButton = findViewById(R.id.edit_button_update);
        mDeleteButton = findViewById(R.id.edit_button_delete);
        mProgressBar = findViewById(R.id.edit_progressbar);

        firebaseDatabase = FirebaseDatabase.getInstance();

        adapterItems = new ArrayAdapter<String>(this, R.layout.field_list_item, field_items);

        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(EditActivity.this, "Votre choix: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        mDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        EditActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;

                mDateTextView.setText(date);
            }
        };

        mTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(EditActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mTimeSetListener,
                        hour,minute, true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                String time = hour + ":" + minute;

                mTimeTextView.setText(time);
            }
        };

        event = getIntent().getParcelableExtra("event");

        if (event != null) {
            autoCompleteTextView.setText(event.getField());
            mPhoneEditText.setText(event.getPhone());
            mDateTextView.setText(event.getDate());
            mTimeTextView.setText(event.getTime());
            //mFormatOptionRadioButton.setChecked(true);
            eventID = event.getId();
        }

        databaseReference = firebaseDatabase.getReference("Events").child(eventID);

        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);

                author = mAuthorTextView.getText().toString();
                field = autoCompleteTextView.getText().toString();
                phone = mPhoneEditText.getText().toString();
                date = mDateTextView.getText().toString();
                time = mTimeTextView.getText().toString();
                eventID = field;

                int selectedOptionId = mFormatRadioGroup.getCheckedRadioButtonId();
                mFormatOptionRadioButton = findViewById(selectedOptionId);
                format = mFormatOptionRadioButton.getText().toString();

                Map<String, Object> map = new HashMap<>();
                map.put("author", author);
                map.put("field", field);
                map.put("phone", phone);
                map.put("date", date);
                map.put("time", time);
                map.put("id", eventID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mProgressBar.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditActivity.this, "Foot mis à jour...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditActivity.this, "Echec de la mise à jour...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEvent();
            }
        });
    }

    private void deleteEvent() {
        databaseReference.removeValue();
        Toast.makeText(this, "Foot Supprimé...", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditActivity.this, MainActivity.class));
    }
}