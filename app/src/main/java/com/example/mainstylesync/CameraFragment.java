package com.example.mainstylesync;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.room.Room;


public class CameraFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner spinner1 = view.findViewById(R.id.spinner1);
        Spinner spinner2 = view.findViewById(R.id.spinner2);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),
                R.array.types, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.colors, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);

        Button save = view.findViewById(R.id.button);
        save.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public CameraFragment() {

    }

    @Override
    public void onClick(View view) {
        CheckBox spring;
        CheckBox summer;
        CheckBox winter;
        CheckBox fall;

        CheckBox patterned;

        Spinner clothingType;
        Spinner clothingColor;

        spring = view.getRootView().findViewById(R.id.checkBox);
        summer = view.getRootView().findViewById(R.id.checkBox4);
        fall = view.getRootView().findViewById(R.id.checkBox3);
        winter = view.getRootView().findViewById(R.id.checkBox2);
        patterned = view.getRootView().findViewById(R.id.checkBox5);

        clothingType = view.getRootView().findViewById(R.id.spinner1);
        clothingColor = view.getRootView().findViewById(R.id.spinner2);


        boolean isSpring = spring.isChecked();
        boolean isSummer = summer.isChecked();
        boolean isFall = fall.isChecked();
        boolean isWinter = winter.isChecked();
        boolean isPatterned = patterned.isChecked();

        String type = clothingType.toString();
        String color = clothingColor.toString();

        AppDatabase db = Room.databaseBuilder(view.getContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();

        ItemDao itemDao = db.itemDao();

        itemDao.insertAll(new Item(type, color, isWinter, isSpring, isFall, isSummer, isPatterned));

        Log.d(TAG, "onClick: Added Row");
    }
}