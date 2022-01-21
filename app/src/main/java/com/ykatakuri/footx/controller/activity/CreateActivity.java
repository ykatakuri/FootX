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

public class CreateActivity extends AppCompatActivity {

    private String[] field_items = {"Borderouge", "Struxiano", "Argoulets", "Bagatelle"};

    private AutoCompleteTextView autoCompleteTextView;

    private ArrayAdapter<String> adapterItems;

    private Button mCreateButton;
    private TextView mDateTextView, mTimeTextView, mAuthorTextView;
    private EditText mPhoneEditText;
    private RadioGroup mFormatRadioGroup;
    private RadioButton mFormatOptionRadioButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    private ProgressBar mProgressBar;

    private String author, field, phone, date, time, format, eventID;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        autoCompleteTextView = findViewById(R.id.create_textview_autocomplete);
        mAuthorTextView = findViewById(R.id.create_textview_author);
        mPhoneEditText = findViewById(R.id.create_edittext_phone);
        mDateTextView = findViewById(R.id.create_textview_datepicker);
        mTimeTextView = findViewById(R.id.create_textview_timepicker);
        mFormatRadioGroup = findViewById(R.id.create_radiogroup_format);
        mCreateButton = findViewById(R.id.create_button_create);
        mProgressBar = findViewById(R.id.create_progressbar);

        adapterItems = new ArrayAdapter<String>(this, R.layout.field_list_item, field_items);

        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(CreateActivity.this, "Votre choix: " + item, Toast.LENGTH_SHORT).show();
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
                        CreateActivity.this,
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

                TimePickerDialog dialog = new TimePickerDialog(CreateActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
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

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Events");

        mCreateButton.setOnClickListener(new View.OnClickListener() {
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

                if (!author.isEmpty() && !field.isEmpty() && !phone.isEmpty() && !date.isEmpty() && !time.isEmpty() && !format.isEmpty()) {

                    Events event = new Events(author, field, phone, date, time, format, eventID);

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(eventID).setValue(event);
                            Toast.makeText(CreateActivity.this, "Foot créé !", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CreateActivity.this, MainActivity.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(CreateActivity.this, "Echec lors de la création...", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
}