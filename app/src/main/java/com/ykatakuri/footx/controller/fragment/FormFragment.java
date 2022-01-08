package com.ykatakuri.footx.controller.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ykatakuri.footx.R;
import com.ykatakuri.footx.databinding.FragmentFormBinding;
import com.ykatakuri.footx.model.Events;

import java.util.Calendar;

public class FormFragment extends Fragment {

    String[] field_items = {"Borderouge", "Struxiano", "Argoulets", "Bagatelle"};

    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> adapterItems;

    private Button mCreateButton;
    private TextView mDateTextView, mTimeTextView;
    private EditText mPhoneEditText;
    private RadioGroup mFormatRadioGroup;
    private RadioButton mFormatOptionRadioButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;

    private NavigationBarView mNavigationBarView;

    FragmentFormBinding binding;

    String field, phone, date, time, format;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_form, container, false);

        autoCompleteTextView = view.findViewById(R.id.form_textview_autocomplete);
        mPhoneEditText = view.findViewById(R.id.form_edittext_phone);
        mDateTextView = view.findViewById(R.id.form_textview_datepicker);
        mTimeTextView = view.findViewById(R.id.form_textview_timepicker);
        mFormatRadioGroup = view.findViewById(R.id.form_radiogroup_format);
        mCreateButton = view.findViewById(R.id.form_button_create);

        adapterItems = new ArrayAdapter<String>(this.getContext(), R.layout.field_list_item, field_items);

        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getContext(), "Item: " + item, Toast.LENGTH_SHORT).show();
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
                        getContext(),
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

                TimePickerDialog dialog = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
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

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                field = autoCompleteTextView.getText().toString();
                phone = mPhoneEditText.getText().toString();
                date = mDateTextView.getText().toString();
                time = mTimeTextView.getText().toString();

                int selectedOptionId = mFormatRadioGroup.getCheckedRadioButtonId();
                mFormatOptionRadioButton = view.findViewById(selectedOptionId);
                format = mFormatOptionRadioButton.getText().toString();

                if (!field.isEmpty() && !phone.isEmpty() && !date.isEmpty() && !time.isEmpty() && !format.isEmpty()) {

                    Events events = new Events(field, phone, date, time, format);
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference("Events");
                    databaseReference.child(field).setValue(events).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(getContext(), "Foot créé", Toast.LENGTH_SHORT).show();

                        }
                    });

                }


            }
        });

        return view;
    }
}