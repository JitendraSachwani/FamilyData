package com.jitu.familydata;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.adapter.ViewDataAdapter;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.exception.ConversionException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Main2Activity extends AppCompatActivity implements Validator.ValidationListener{

    private Button btn;
    private Toolbar toolbar;
    private RadioGroup radioGroup,gender;
    private RelativeLayout status_extras_layout1, status_extras_layout2;
    @NotEmpty
    private TextInputLayout first_name_layout, middle_name_layout, last_name_layout,  fathers_name_layout, fathers_gaav_layout, mother_name_layout,
            mother_gaav_layout, father_layout, mother_layout, paternal_mama_layout, maternal_mama_layout, spouse_name_layout, qualification_layout, work_layout,
            other_int_layout, mobile_no_layout, email_layout;

    private TextInputLayout dob_layout;
    private EditText first_name, middle_name, last_name, dob, fathers_name, fathers_gaav, mother_name,
            mother_gaav, father, mother, paternal_mama, maternal_mama, spouse_name, qualification, work,
            other_int, mobile_no, email;
    private int family_no,n_o_m;
    private OkHttpClient client;
    private int mYear, mMonth, mDay;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.head_of_family_form);

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
        family_no =-1;
        family_no = getIntent().getExtras().getInt("family_no");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Family #" + family_no + " Head of Family Details:");
        //getActionBar().setIcon(R.drawable.add_ur_icon_here);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn = (Button) findViewById(R.id.btn);
        gender = (RadioGroup) findViewById(R.id.rg_gender);
        radioGroup = (RadioGroup) findViewById(R.id.status_radio_grp);
        status_extras_layout1 = (RelativeLayout) findViewById(R.id.status_extras_layout1);
        status_extras_layout2 = (RelativeLayout) findViewById(R.id.status_extras_layout2);

        first_name_layout = (TextInputLayout) findViewById(R.id.first_name_layout);
        middle_name_layout = (TextInputLayout) findViewById(R.id.middle_name_layout);
        last_name_layout = (TextInputLayout) findViewById(R.id.last_name_layout);
        dob_layout = (TextInputLayout) findViewById(R.id.dob_layout);
        fathers_name_layout = (TextInputLayout) findViewById(R.id.fathers_name_layout);
        fathers_gaav_layout = (TextInputLayout) findViewById(R.id.fathers_gaav_layout);
        mother_name_layout = (TextInputLayout) findViewById(R.id.mother_name_layout);
        mother_gaav_layout = (TextInputLayout) findViewById(R.id.mother_gaav_layout);
        father_layout = (TextInputLayout) findViewById(R.id.father_layout);
        mother_layout = (TextInputLayout) findViewById(R.id.mother_layout);
        paternal_mama_layout = (TextInputLayout) findViewById(R.id.paternal_mama_layout);
        maternal_mama_layout = (TextInputLayout) findViewById(R.id.maternal_mama_layout);
        spouse_name_layout = (TextInputLayout) findViewById(R.id.spouse_name_layout);
        qualification_layout = (TextInputLayout) findViewById(R.id.qualification_layout);
        work_layout = (TextInputLayout) findViewById(R.id.work_layout);
        other_int_layout = (TextInputLayout) findViewById(R.id.other_int_layout);
        mobile_no_layout = (TextInputLayout) findViewById(R.id.mobile_no_layout);
        email_layout = (TextInputLayout) findViewById(R.id.email_layout);

        first_name = (EditText) findViewById(R.id.first_name);
        middle_name = (EditText) findViewById(R.id.middle_name);
        last_name = (EditText) findViewById(R.id.last_name);
        dob = (EditText) findViewById(R.id.dob);
        fathers_name = (EditText) findViewById(R.id.fathers_name);
        fathers_gaav = (EditText) findViewById(R.id.fathers_gaav);
        mother_name = (EditText) findViewById(R.id.mother_name);
        mother_gaav = (EditText) findViewById(R.id.mother_gaav);
        father = (EditText) findViewById(R.id.father);
        mother = (EditText) findViewById(R.id.mother);
        paternal_mama = (EditText) findViewById(R.id.paternal_mama);
        maternal_mama = (EditText) findViewById(R.id.maternal_mama);
        spouse_name = (EditText) findViewById(R.id.spouse_name);
        qualification = (EditText) findViewById(R.id.qualification);
        work = (EditText) findViewById(R.id.work);
        other_int = (EditText) findViewById(R.id.other_int);
        mobile_no = (EditText) findViewById(R.id.mobile_no);
        email = (EditText) findViewById(R.id.email);

        status_extras_layout1.setVisibility(View.GONE);
        status_extras_layout2.setVisibility(View.VISIBLE);
        spouse_name.setText("");


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) findViewById(checkedId);
                String text = checkedRadioButton.getText().toString();

                Log.e("text:", "" + text);

                if (text.equals("Single")) {
                    status_extras_layout1.setVisibility(View.GONE);
                    status_extras_layout2.setVisibility(View.VISIBLE);
                    spouse_name.setText("");

                } else if (text.equals("Married") || text.equals("Widowed")) {
                    status_extras_layout1.setVisibility(View.VISIBLE);
                    status_extras_layout2.setVisibility(View.GONE);
                    father.setText("");
                    mother.setText("");
                    paternal_mama.setText("");
                    maternal_mama.setText("");
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearErrors();
                validator.validate();


            }
        });
        {
            //Date Picker

            final Button pickDate = (Button) findViewById(R.id.pick_date);
            final Calendar myCalendar = Calendar.getInstance();
            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    // myCalendar.add(Calendar.DATE, 0);
                    String myFormat = "yyyy-MM-dd"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    dob.setText(sdf.format(myCalendar.getTime()));
                }


            };

            pickDate.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    // Launch Date Picker Dialog
                    DatePickerDialog dpd = new DatePickerDialog(Main2Activity.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // Display Selected date in textbox

                                    if (year < mYear)
                                        view.updateDate(mYear, mMonth, mDay);

                                    if (monthOfYear < mMonth && year == mYear)
                                        view.updateDate(mYear, mMonth, mDay);

                                    if (dayOfMonth < mDay && year == mYear && monthOfYear == mMonth)
                                        view.updateDate(mYear, mMonth, mDay);

                                    dob.setText(dayOfMonth + "/"
                                            + (monthOfYear + 1) + "/" + year);

                                }
                            }, mYear, mMonth, mDay);
                    //dpd.getDatePicker().setMinDate(System.currentTimeMillis());
                    dpd.show();
                }
            });
        }


    }



    private void sendRequest() {

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("isHead", "1")
                .addFormDataPart("f_name", first_name.getText().toString())
                .addFormDataPart("m_name", middle_name.getText().toString())
                .addFormDataPart("l_name", last_name.getText().toString())
                .addFormDataPart("status", ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString())
                .addFormDataPart("dob", dob.getText().toString())
                .addFormDataPart("gender", ((RadioButton) findViewById(gender.getCheckedRadioButtonId())).getText().toString())
                .addFormDataPart("fathers_name", fathers_name.getText().toString())
                .addFormDataPart("fathers_gaav", fathers_gaav.getText().toString())
                .addFormDataPart("mothers_name", mother_name.getText().toString())
                .addFormDataPart("mothers_gaav", mother_gaav.getText().toString())
                .addFormDataPart("SakhaFather", father.getText().toString())
                .addFormDataPart("SakhaMother", mother.getText().toString())
                .addFormDataPart("SakhaPaternalMama", paternal_mama.getText().toString())
                .addFormDataPart("SakhaMaternalMama", maternal_mama.getText().toString())
                .addFormDataPart("p_name", spouse_name.getText().toString())
                .addFormDataPart("qualification", qualification.getText().toString())
                .addFormDataPart("work", work.getText().toString())
                .addFormDataPart("interests", other_int.getText().toString())
                .addFormDataPart("mobile", mobile_no.getText().toString())
                .addFormDataPart("mail", email.getText().toString())
                .addFormDataPart("familyId", ""+family_no)
                .build();

        Request request = new Request.Builder()
                .url("http://msgt.co.in/vorat/MemberOrHODLogin.php") //Enter Url Here
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
                    Log.e("onResponse: ",resp );
                    //family_no = Integer.parseInt(response.body().string());
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(resp);
                        family_no = obj.getInt("FamilyID");
                        n_o_m = 4;/**to be removed*/

                        //if()//check success response
                        if(obj.getInt("Success")==1)
                        {
                            Intent i = new Intent(Main2Activity.this, Add_members.class)
                                    .putExtra("family_no", family_no)
                                    .putExtra("n_o_m", n_o_m);
                            startActivity(i);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
        }
        });
    }

    private void clearErrors() {
        first_name_layout.setError(null); middle_name_layout.setError(null); last_name_layout.setError(null); dob_layout.setError(null); fathers_name_layout.setError(null); fathers_gaav_layout.setError(null); mother_name_layout.setError(null);
        mother_gaav_layout.setError(null); father_layout.setError(null); mother_layout.setError(null); paternal_mama_layout.setError(null); maternal_mama_layout.setError(null); spouse_name_layout.setError(null); qualification_layout.setError(null); work_layout.setError(null);
        other_int_layout.setError(null); mobile_no_layout.setError(null); email_layout.setError(null);
        first_name_layout.clearFocus(); middle_name_layout.clearFocus(); last_name_layout.clearFocus(); dob_layout.clearFocus(); fathers_name_layout.clearFocus(); fathers_gaav_layout.clearFocus(); mother_name_layout.clearFocus();
        mother_gaav_layout.clearFocus(); father_layout.clearFocus(); mother_layout.clearFocus(); paternal_mama_layout.clearFocus(); maternal_mama_layout.clearFocus(); spouse_name_layout.clearFocus(); qualification_layout.clearFocus(); work_layout.clearFocus();
        other_int_layout.clearFocus(); mobile_no_layout.clearFocus(); email_layout.clearFocus();


    }

    @Override
    public void onValidationSucceeded() {
        //Toast.makeText(this, "Yay! we got it right!", Toast.LENGTH_SHORT).show();
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
}
