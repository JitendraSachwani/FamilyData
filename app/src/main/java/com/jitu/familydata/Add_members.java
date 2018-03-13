package com.jitu.familydata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Add_members extends AppCompatActivity {
    private RecyclerView recyclerView;
    private member_adapter adapter;
    private List<Member> memberList;
    private LinearLayout addHead;
    private int family_no,n_o_m;
    private String firstName,middleName,lastName;
    private String headName;
    private int headSet=0;
    private TextView head_title;
    private ImageView head_thumbnail;
    private OkHttpClient client;
    private int setMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_members);

        client = new OkHttpClient();
        family_no = getIntent().getExtras().getInt("family_no");
        //n_o_m = getIntent().getExtras().getInt("n_o_m");  /** set from db later**/
        n_o_m = 2;// -1 for HOF

        //sendRequest();

        headName="testHead";
        setMembers= 0;


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Family #"+family_no+" Details");
        //getActionBar().setIcon(R.drawable.add_ur_icon_here);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.member_recycler_view);

        memberList = new ArrayList<>();
        adapter = new member_adapter(this, memberList,family_no,setMembers);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        head_title = (TextView) findViewById(R.id.head_title);
        head_thumbnail = (ImageView) findViewById(R.id.head_thumbnail);

        prepareMembers();

        addHead = (LinearLayout) findViewById(R.id.add_head_layout);

        if(headSet==0)
            addHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), Main2Activity.class)
                            .putExtra("family_no",family_no);
                    startActivity(i);
                    finish();
                }
            });
        else{
            head_title.setText(headName);
        }



    }

    /**
     * Adding few albums for testing
     */
    private void prepareMembers() {

        int[] covers = new int[n_o_m-1];

        for(int i=0;i<n_o_m-1;i++)
            covers[i]=R.drawable.add;

        for(int i=0;i<n_o_m-1;i++) {

            Member a = new Member("Add Member", covers[i]);
            memberList.add(a);

        }
        adapter.notifyDataSetChanged();
    }
    private void sendRequest() {

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("family_no", ""+family_no)
                .build();

        Request request = new Request.Builder()
                .url("http://sehatjaanch.com/st_class/")
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
                    Log.e("test",response.body().string());

                    try {
                        JSONArray a1= new JSONArray(response.body().string());

                        headSet=1;
                        headName="test";
                        setMembers= 1;
                        n_o_m=4;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

}
