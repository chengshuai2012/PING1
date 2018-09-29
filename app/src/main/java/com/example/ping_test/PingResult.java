package com.example.ping_test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class PingResult extends Activity {
	TextView tv_show;
	String lost = "";// 丢包
	String delay = "";// 延迟
	String ip_adress = "";// ip地址
	String countCmd = "";// ping -c
	String sizeCmd = "", timeCmd = "";// ping -s ;ping -i
	String result = "";
	private static final String tag = "TAG";// Log标志
	int status = -1;// 状态
	String ping = "";
	Myhandler handler=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pingresult);
		tv_show = (TextView) findViewById(R.id.tv_show);
		Intent intent2 = this.getIntent();
		Bundle bundle2 = intent2.getExtras();
		ping = bundle2.getString("ping");
		Log.i(tag, "====MainThread====:" + Thread.currentThread().getId());
		HandlerThread thread = new HandlerThread("PingTest");
		thread.start();
		handler = new Myhandler(thread.getLooper());
		// @Override
		// public void handleMessage(Message msg) {
		// // TODO Auto-generated method stub
		// super.handleMessage(msg);
		// switch (msg.what) {
		// case 10:
		// String resultmsg = (String) msg.obj;
		// tv_show.append(msg.what+"");
		// Log.i(tag, "====handlerThread====:" +
		// Thread.currentThread().getId());
		// Log.i(tag, "====resultmsg====:" + msg.what);
		// Log.i(tag, "====resultmsg====:" + resultmsg);
		// break;
		// default:
		// break;
		// }
		// }
		// };
		
		handler.post(r);
	}
	class Myhandler extends Handler {
		public Myhandler() {

		}

		public Myhandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 10:
				String resultmsg = (String) msg.obj;
				//tv_show.append(msg.what + "");
				Log.i(tag, "====handlerThread====:"
						+ Thread.currentThread().getId());
				Log.i(tag, "====resultmsg====:" + msg.what);
				Log.i(tag, "====resultmsg====:" + resultmsg);
				break;
			default:
				break;
			}
		}
	};

	Runnable r = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			delay = "";
			lost = "";

			Process process = null;
			BufferedReader successReader = null;
			BufferedReader errorReader = null;

			DataOutputStream dos = null;
			try {
				// 闃诲澶勭悊
				process = Runtime.getRuntime().exec(ping);
				dos = new DataOutputStream(process.getOutputStream());
				Log.i(tag, "====receive====:");
				String command = "ping" + countCmd + timeCmd + sizeCmd
						+ ip_adress;

				dos.write(command.getBytes());
				dos.writeBytes("\n");
				dos.flush();
				dos.writeBytes("exit\n");
				dos.flush();

				status = process.waitFor();
				// success
				successReader = new BufferedReader(new InputStreamReader(
						process.getInputStream()));
				// error
				errorReader = new BufferedReader(new InputStreamReader(
						process.getErrorStream()));

				String lineStr;

				while ((lineStr = successReader.readLine()) != null) {

					Log.i(tag, "====receive====:" + lineStr);
					result = result + lineStr + "\n";
					if (lineStr.contains("packet loss")) {
						Log.i(tag, "=====Message=====" + lineStr.toString());
						int i = lineStr.indexOf("received");
						int j = lineStr.indexOf("%");
						Log.i(tag,
								"====丢包率====:"
										+ lineStr.substring(i + 10, j + 1));//
						lost = lineStr.substring(i + 10, j + 1);
					}
					if (lineStr.contains("avg")) {
						int i = lineStr.indexOf("/", 20);
						int j = lineStr.indexOf(".", i);
						Log.i(tag,
								"====平均时延:===="
										+ lineStr.substring(i + 1, j));
						delay = lineStr.substring(i + 1, j);
						delay = delay + "ms";
					}
					// tv_show.setText("丢包率:" + lost.toString() + "\n" +
					// "平均时延:"
					// + delay.toString() + "\n" + "IP地址:");// +
					// getNetIpAddress()
					// + getLocalIPAdress() + "\n" + "MAC地址:" +
					// getLocalMacAddress() + getGateWay());
				}
				// tv_show.setText(result);
				Message msg = handler.obtainMessage();

				msg.obj = result;
				msg.what = 10;
				msg.sendToTarget();
				while ((lineStr = errorReader.readLine()) != null) {
					Log.i(tag, "==error======" + lineStr);
					// tv_show.setText(lineStr);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (dos != null) {
						dos.close();
					}
					if (successReader != null) {
						successReader.close();
					}
					if (errorReader != null) {
						errorReader.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (process != null) {
					process.destroy();
				}

			}

		}//run函数大括号到这
	};
}
