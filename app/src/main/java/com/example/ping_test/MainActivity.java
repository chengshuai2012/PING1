package com.example.ping_test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {

	Button btn_ping;
	EditText et_ip, et_count, et_size, et_time;
	String ip,count,size,time;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn_ping = (Button) findViewById(R.id.btn_ping);
		et_ip = (EditText) findViewById(R.id.edit_ip);
		et_count = (EditText) findViewById(R.id.edit_count);
		et_size = (EditText) findViewById(R.id.edit_size);
		et_time = (EditText) findViewById(R.id.edit_time);
		btn_ping.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// String ip = et_ip.getText().toString();
				// String result = PingHost.Ping(ip);
				// tv_show.setText(result);
				// System.out.println("result ============= " + result);
				
				ip=et_ip.getText().toString();
				count=et_count.getText().toString();
				size=et_size.getText().toString();
				time=et_time.getText().toString();
				
				String countCmd = " -c " + count + " ";
				String sizeCmd = " -s " + size + " ";
				String timeCmd = " -i " + time + " ";
				String ip_adress = ip;
				String ping = "ping" + countCmd + timeCmd + sizeCmd + ip_adress;
				
				Intent intent=new Intent();
				intent.setClass(MainActivity.this, PingResult1.class);
				//new一个Bundle对象，并将要传递的数据传入
				Bundle bundle=new Bundle();
				bundle.putString("ping", ping);
				bundle.putString("ip",ip);
				bundle.putString("count",count);
				bundle.putString("size",size);
				bundle.putString("time",time);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

}
