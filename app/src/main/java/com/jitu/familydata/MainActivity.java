package com.jitu.familydata;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.adapter.ViewDataAdapter;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.exception.ConversionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements Validator.ValidationListener {

    @NotEmpty
    private TextInputLayout family_name_layout, HOF_layout, no_of_members_layout, res_flat_no_layout,
            res_floor_layout, res_wing_layout, res_building_layout,
            res_road_layout, res_landmark_layout,
            res_pin_layout,res_tel_layout, comm_flat_no_layout, comm_floor_layout,
            comm_wing_layout, comm_building_layout, comm_road_layout, comm_landmark_layout,
            comm_pin_layout,comm_tel_layout,comm_name_layout;

    private TextInputLayout res_area_layout, res_city_layout, res_state_layout,
            comm_area_layout, comm_city_layout, comm_state_layout;
    private Button btn;
    private OkHttpClient client;

    private int family_no,n_o_m;

    private String familyName;
    private AutoCompleteTextView area;
    private RadioGroup photos_by;
    private EditText family_name, HOF, no_of_members, res_flat_no, res_floor, res_wing, res_building,
            res_road, res_landmark,  res_pin,res_tel,comm_name , comm_flat_no, comm_floor,
            comm_wing, comm_building, comm_road, comm_landmark, comm_pin,comm_tel;
    private Spinner res_area, res_city, res_state, comm_area, comm_city, comm_state;
    private JSONObject spinnersData;
    private ArrayList<String> sList,cList,aList;
    private ArrayList<String> comm_sList,comm_cList,comm_aList;
    private ArrayAdapter<String> stateadapter,cityadapter,areaadapter;
    private ArrayAdapter<String> comm_stateadapter,comm_cityadapter,comm_areaadapter;


    private Validator validator;
    private JSONObject cities;
    private JSONArray areas;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.family_form);

        client = new OkHttpClient();


        validator = new Validator(this);
        validator.setValidationListener(this);
        validator.registerAdapter(TextInputLayout.class,
                new ViewDataAdapter<TextInputLayout, String>() {
                    @Override
                    public String getData(TextInputLayout flet) throws ConversionException {
                        return flet.getEditText().getText().toString();
                    }
                }
        );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Family Form");
        //getActionBar().setIcon(R.drawable.add_ur_icon_here);

        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*area = (AutoCompleteTextView) findViewById(R.id.area);
        String[] areas = {};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, areas);
        area.setAdapter(adapter);*/


        family_name_layout = (TextInputLayout) findViewById(R.id.family_name_layout);
        family_name_layout = (TextInputLayout) findViewById(R.id.family_name_layout);
        HOF_layout = (TextInputLayout) findViewById(R.id.HOF_layout);
        no_of_members_layout = (TextInputLayout) findViewById(R.id.member_no_layout);
        res_flat_no_layout = (TextInputLayout) findViewById(R.id.res_flat_no_layout);
        res_floor_layout = (TextInputLayout) findViewById(R.id.res_floor_layout);
        res_wing_layout = (TextInputLayout) findViewById(R.id.res_wing_layout);
        res_building_layout = (TextInputLayout) findViewById(R.id.res_building_layout);
        res_road_layout = (TextInputLayout) findViewById(R.id.res_road_layout);
        res_landmark_layout = (TextInputLayout) findViewById(R.id.res_landmark_layout);
        res_area_layout = (TextInputLayout) findViewById(R.id.res_area_layout);
        res_city_layout = (TextInputLayout) findViewById(R.id.res_city_layout);
        res_state_layout = (TextInputLayout) findViewById(R.id.res_state_layout);
        res_pin_layout = (TextInputLayout) findViewById(R.id.res_pin_layout);
        res_tel_layout = (TextInputLayout) findViewById(R.id.res_tel_layout);
        comm_name_layout = (TextInputLayout) findViewById(R.id.comm_name_layout);
        comm_flat_no_layout = (TextInputLayout) findViewById(R.id.comm_flat_no_layout);
        comm_floor_layout = (TextInputLayout) findViewById(R.id.comm_floor_layout);
        comm_wing_layout = (TextInputLayout) findViewById(R.id.comm_wing_layout);
        comm_building_layout = (TextInputLayout) findViewById(R.id.comm_building_layout);
        comm_road_layout = (TextInputLayout) findViewById(R.id.comm_road_layout);
        comm_landmark_layout = (TextInputLayout) findViewById(R.id.comm_landmark_layout);
        comm_area_layout = (TextInputLayout) findViewById(R.id.comm_area_layout);
        comm_city_layout = (TextInputLayout) findViewById(R.id.comm_city_layout);
        comm_state_layout = (TextInputLayout) findViewById(R.id.comm_state_layout);
        comm_pin_layout = (TextInputLayout) findViewById(R.id.comm_pin_layout);
        comm_tel_layout = (TextInputLayout) findViewById(R.id.comm_tel_layout);

        family_name = (EditText) findViewById(R.id.family_name);
        HOF = (EditText) findViewById(R.id.HOF);
        no_of_members = (EditText) findViewById(R.id.no_of_members);

        photos_by = (RadioGroup) findViewById(R.id.photos_by);
        res_flat_no = (EditText) findViewById(R.id.res_flat_no);
        res_floor = (EditText) findViewById(R.id.res_floor);
        res_wing = (EditText) findViewById(R.id.res_wing);
        res_building = (EditText) findViewById(R.id.res_building);
        res_road = (EditText) findViewById(R.id.res_road);
        res_landmark = (EditText) findViewById(R.id.res_landmark);

        res_area = (Spinner) findViewById(R.id.res_area);
        res_city = (Spinner) findViewById(R.id.res_city);
        res_state = (Spinner) findViewById(R.id.res_state);

        res_pin = (EditText) findViewById(R.id.res_pin);
        res_tel = (EditText) findViewById(R.id.res_tel);
        comm_name = (EditText) findViewById(R.id.comm_name);
        comm_flat_no = (EditText) findViewById(R.id.comm_flat_no);
        comm_floor = (EditText) findViewById(R.id.comm_floor);
        comm_wing = (EditText) findViewById(R.id.comm_wing);
        comm_building = (EditText) findViewById(R.id.comm_building);
        comm_road = (EditText) findViewById(R.id.comm_road);
        comm_landmark = (EditText) findViewById(R.id.comm_landmark);

        comm_area = (Spinner) findViewById(R.id.comm_area);
        comm_city = (Spinner) findViewById(R.id.comm_city);
        comm_state = (Spinner) findViewById(R.id.comm_state);

        comm_pin = (EditText) findViewById(R.id.comm_pin);
        comm_tel = (EditText) findViewById(R.id.comm_tel);

        btn = (Button) findViewById(R.id.btn);

        populatespinners();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearErrors();
                //validateForm();
                validator.validate();

            }
        });




    }

    private void populatespinners() {
        Request request = new Request.Builder()
                .url("http://msgt.co.in/vorat/getAreasCitesStates.php")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure: ","Error Loading Spinners");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.e("onResponse: ",res );
                try {
                    spinnersData = new JSONObject(res);
                    sList = new ArrayList<String>();
                    comm_sList = new ArrayList<String>();

                    Iterator<?> keys = spinnersData.keys();
                    while( keys.hasNext() ) {
                        String state = (String)keys.next();
                        if ( spinnersData.get(state) instanceof JSONObject ) {
                            sList.add(state);
                            comm_sList.add(state);
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            stateadapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,sList);
                            comm_stateadapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,comm_sList);
                            res_state.setAdapter(stateadapter);
                            res_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    cList = new ArrayList<String>();
                                    try {
                                        Log.e("onStateSelected: ", String.valueOf(cities));
                                        cities = spinnersData.getJSONObject((String) parent.getItemAtPosition(position));
                                        Iterator<?> keys = cities.keys();
                                    while( keys.hasNext() ) {
                                        String city = (String)keys.next();
                                        if ( spinnersData.getJSONObject((String) parent.getItemAtPosition(position)).get(city) instanceof JSONArray) {
                                                cList.add(city);
                                        }
                                    }
                                        cityadapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,cList);
                                        res_city.setAdapter(cityadapter);
                                        res_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                aList = new ArrayList<String>();
                                                try {
                                                    areas = cities.getJSONArray((String) parent.getItemAtPosition(position));
                                                    Log.e("onCitySelected: ",""+areas);

                                                    for (int i = 0; i < areas.length(); i++) {
                                                        aList.add(areas.getString(i));
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                areaadapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,aList);
                                                res_area.setAdapter(areaadapter);
                                            }
                                            @Override
                                            public void onNothingSelected(AdapterView<?> parent) {
                                            }
                                        });
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                            comm_state.setAdapter(comm_stateadapter);
                            comm_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    comm_cList = new ArrayList<String>();
                                    try {
                                        Log.e("onStateSelected: ", String.valueOf(cities));
                                        cities = spinnersData.getJSONObject((String) parent.getItemAtPosition(position));
                                        Iterator<?> keys = cities.keys();
                                        while( keys.hasNext() ) {
                                            String city = (String)keys.next();
                                            if ( spinnersData.getJSONObject((String) parent.getItemAtPosition(position)).get(city) instanceof JSONArray) {
                                                comm_cList.add(city);
                                            }
                                        }
                                        comm_cityadapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,comm_cList);
                                        comm_city.setAdapter(comm_cityadapter);
                                        comm_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                comm_aList = new ArrayList<String>();
                                                try {
                                                    areas = cities.getJSONArray((String) parent.getItemAtPosition(position));
                                                    Log.e("onCitySelected: ",""+areas);

                                                    for (int i = 0; i < areas.length(); i++) {
                                                        comm_aList.add(areas.getString(i));
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                comm_areaadapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,comm_aList);
                                                comm_area.setAdapter(comm_areaadapter);
                                            }
                                            @Override
                                            public void onNothingSelected(AdapterView<?> parent) {
                                            }
                                        });
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                        }
                    });




                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    private void clearErrors() {

        family_name_layout.setError(null); HOF_layout.setError(null); no_of_members_layout.setError(null); res_flat_no_layout.setError(null);
        res_floor_layout.setError(null); res_wing_layout.setError(null); res_building_layout.setError(null);
        res_road_layout.setError(null); res_landmark_layout.setError(null); res_area_layout.setError(null); res_city_layout.setError(null); res_state_layout.setError(null);
        res_pin_layout.setError(null);res_tel_layout.setError(null);comm_name_layout.setError(null); comm_flat_no_layout.setError(null); comm_floor_layout.setError(null);
        comm_wing_layout.setError(null); comm_building_layout.setError(null); comm_road_layout.setError(null); comm_landmark_layout.setError(null);
        comm_area_layout.setError(null); comm_city_layout.setError(null); comm_state_layout.setError(null); comm_pin_layout.setError(null);comm_tel_layout.setError(null);
        family_name_layout.clearFocus(); HOF_layout.clearFocus(); no_of_members_layout.clearFocus(); res_flat_no_layout.clearFocus();
        res_floor_layout.clearFocus(); res_wing_layout.clearFocus(); res_building_layout.clearFocus();
        res_road_layout.clearFocus(); res_landmark_layout.clearFocus(); res_area_layout.clearFocus(); res_city_layout.clearFocus(); res_state_layout.clearFocus();
        res_pin_layout.clearFocus();res_tel_layout.clearFocus();comm_name_layout.clearFocus(); comm_flat_no_layout.clearFocus(); comm_floor_layout.clearFocus();
        comm_wing_layout.clearFocus(); comm_building_layout.clearFocus(); comm_road_layout.clearFocus(); comm_landmark_layout.clearFocus();
        comm_area_layout.clearFocus(); comm_city_layout.clearFocus(); comm_state_layout.clearFocus(); comm_pin_layout.clearFocus();comm_tel_layout.clearFocus();

    }

    @Override
    public void onValidationSucceeded() {
        //Toast.makeText(this, "Yay! validation successful :)", Toast.LENGTH_SHORT).show();
        sendRequest();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        Toast.makeText(this, "Please Enter All Fields", Toast.LENGTH_LONG).show();
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof TextInputLayout) {
                ((TextInputLayout) view).setError(message);
                requestFocus(view);

            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }

    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void sendRequest() {

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("fam_name", family_name.getText().toString())
                .addFormDataPart("hof", HOF.getText().toString())
                .addFormDataPart("noOfMembers", no_of_members.getText().toString())
                //.addFormDataPart("areaChoser", area.getText().toString())
                .addFormDataPart("photosBy", ((RadioButton) findViewById(photos_by.getCheckedRadioButtonId())).getText().toString())
                .addFormDataPart("resFlatNo", res_flat_no.getText().toString())
                .addFormDataPart("resFloor", res_floor.getText().toString())
                .addFormDataPart("resWing", res_wing.getText().toString())
                .addFormDataPart("resBuilding", res_building.getText().toString())
                .addFormDataPart("resRoad", res_road.getText().toString())
                .addFormDataPart("resLandmark", res_landmark.getText().toString())
                .addFormDataPart("resArea", res_area.getSelectedItem().toString())
                .addFormDataPart("ResCityChoser", res_city.getSelectedItem().toString())
                .addFormDataPart("ResStateChoser", res_state.getSelectedItem().toString())
                .addFormDataPart("ResPin", res_pin.getText().toString())
                .addFormDataPart("ResTel", res_tel.getText().toString())
                .addFormDataPart("commName", comm_name.getText().toString())
                .addFormDataPart("commFlatNo", comm_flat_no.getText().toString())
                .addFormDataPart("commFloor", comm_floor.getText().toString())
                .addFormDataPart("commWing", comm_wing.getText().toString())
                .addFormDataPart("commBuilding", comm_building.getText().toString())
                .addFormDataPart("commRoad", comm_road.getText().toString())
                .addFormDataPart("commLandmark", comm_landmark.getText().toString())
                .addFormDataPart("commArea", comm_area.getSelectedItem().toString())
                .addFormDataPart("CommcityChoser", comm_city.getSelectedItem().toString())
                .addFormDataPart("CommStateChoser", comm_state.getSelectedItem().toString())
                .addFormDataPart("CommPIN", comm_pin.getText().toString())
                .addFormDataPart("CommTel", comm_tel.getText().toString())
                .build();

        Request request = new Request.Builder()
                .url("http://msgt.co.in/vorat/FamilyLogin.php")
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    // do something wih the result
                    String resp = response.body().string();
                    Log.e("Response => ", resp);

                    try {
                        JSONObject obj = new JSONObject(resp);
                        family_no = obj.getInt("FamilyID");
                        n_o_m = obj.getInt("NoOfMembers");

                        if(obj.getInt("Success")==1)
                        //if()//check success response
                        {
                            Intent i = new Intent(MainActivity.this, Add_members.class)
                                    .putExtra("family_no", family_no)
                                    .putExtra("n_o_m", n_o_m);
                            startActivity(i);
                            finish();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //family_no = Integer.parseInt(response.body().string());
                }
            }
        });
    }

   /* *//**
     * Validating form
     *//*
    private void validateForm() {
        if (!validateName()) {
            return;
        }

       *//* if (!validateEmail()) {
            return;
        }*//*


        Toast.makeText(getApplicationContext(), "Family Added!", Toast.LENGTH_SHORT).show();
    }

    private boolean validateName() {
        if (family_name.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(family_name);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    *//*private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }*//*

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.family_name:
                    validateName();
                    break;
//                case R.id.input_area:
//                    validateEmail();
//                    break;

            }
        }
    }

    */
}
