package moe.yamabu.wsclwyh;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wilddog.client.ChildEventListener;
import com.wilddog.client.DataSnapshot;
import com.wilddog.client.ValueEventListener;
import com.wilddog.client.Wilddog;
import com.wilddog.client.WilddogError;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.tv_username)
    EditText tvUserName;
    @BindView(R.id.tv_content)
    EditText tvContent;

    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Wilddog.setAndroidContext(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(this);
        recyclerView.setAdapter(myAdapter);


        IMAPI.getRoomQuery().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvTitle.setText((CharSequence) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(WilddogError wilddogError) {

            }
        });

        IMAPI.getMessageQuery().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MessageResponse response = (MessageResponse) dataSnapshot.getValue
                        (MessageResponse.class);
                myAdapter.addData(response);
                recyclerView.scrollToPosition(myAdapter.getItemCount() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                MessageResponse response = (MessageResponse) dataSnapshot.getValue
                        (MessageResponse.class);
                myAdapter.addData(response);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(WilddogError wilddogError) {

            }
        });

    }

    @OnClick({R.id.bt_submit})
    void onClick(View v) {
        putData();
    }

    @SuppressLint("ShowToast")
    private void putData() {
        String userName = tvUserName.getText().toString().trim();
        String content = tvContent.getText().toString().trim();

        if ("".equals(userName)) {
            Toast.makeText(getApplicationContext(), "请填写用户名", Toast.LENGTH_SHORT);
            return;
        }
        if ("".equals(content)) {
            Toast.makeText(getApplicationContext(), "请填写内容", Toast.LENGTH_SHORT);
            return;
        }

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userName", userName);
        hashMap.put("content", content);
        hashMap.put("time", System.currentTimeMillis() + "");
        IMAPI.pushMessageQuery().push().setValue(hashMap);

        tvContent.setText("");
    }
}
