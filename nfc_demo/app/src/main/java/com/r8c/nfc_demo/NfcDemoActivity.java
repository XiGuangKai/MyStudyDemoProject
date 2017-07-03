package com.r8c.nfc_demo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NfcDemoActivity extends Activity implements OnClickListener {

	private Tag tagFromIntent;

	// NFC适配器
	private NfcAdapter nfcAdapter = null;
	// 传达意图
	private PendingIntent pi = null;
	// 滤掉组件无法响应和处理的Intent
	private IntentFilter tagDetected = null;
	// 文本控件
	private TextView promt = null;
	// 是否支持NFC功能的标签
	private boolean isNFC_support = false;
	// 读、写、删按钮控件
	private Button readBtn, writeBtn, deleteBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nfc_demo);
		setupViews();
		initNFCData();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (isNFC_support == false) {
			// 如果设备不支持NFC或者NFC功能没开启，就return掉
			return;
		}
		// 开始监听NFC设备是否连接
		startNFC_Listener();

		if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(this.getIntent()
				.getAction())) {
			// 注意这个if中的代码几乎不会进来，因为刚刚在上一行代码开启了监听NFC连接，下一行代码马上就收到了NFC连接的intent，这种几率很小
			// 处理该intent
			processIntent(this.getIntent());
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (isNFC_support == true) {
			// 当前Activity如果不在手机的最前端，就停止NFC设备连接的监听
			stopNFC_Listener();
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		// 当前app正在前端界面运行，这个时候有intent发送过来，那么系统就会调用onNewIntent回调方法，将intent传送过来
		// 我们只需要在这里检验这个intent是否是NFC相关的intent，如果是，就调用处理方法
		if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
			processIntent(intent);
		}
	}

	@Override
	public void onClick(View v) {

		// 点击读按钮后
		if (v.getId() == R.id.read_btn) {
			try {
				String content = read(tagFromIntent);
				if (content != null && !content.equals("")) {
					promt.setText(promt.getText() + "nfc TAG content:\n" + content
							+ "\n");
				} else {
					promt.setText(promt.getText() + "nfc TAG content:\n" + "is null\n");
				}
			} catch (IOException e) {
				promt.setText(promt.getText() + "error:" + e.getMessage() + "\n");
				Log.e("myonclick", "read nfc error", e);
			} catch (FormatException e) {
				promt.setText(promt.getText() + "error:" + e.getMessage() + "\n");
				Log.e("myonclick", "read nfc error", e);
			}
			// 点击写后写入
		} else if (v.getId() == R.id.write_btn) {
			try {
				write(tagFromIntent);
			} catch (IOException e) {
				promt.setText(promt.getText() + "error:" + e.getMessage() + "\n");
				Log.e("myonclick", "wirte nfc error", e);
			} catch (FormatException e) {
				promt.setText(promt.getText() + "error:" + e.getMessage() + "\n");
				Log.e("myonclick", "wirte nfc error", e);
			}
		} else if (v.getId() == R.id.delete_btn) {
			try {
				delete(tagFromIntent);
			} catch (IOException e) {
				promt.setText(promt.getText() + "error:" + e.getMessage() + "\n");
				Log.e("myonclick", "delete nfc error", e);
			} catch (FormatException e) {
				promt.setText(promt.getText() + "error:" + e.getMessage() + "\n");
				Log.e("myonclick", "delete nfc error", e);
			}
		}
	}

	private void setupViews() {
		// 控件的绑定
		promt = (TextView) findViewById(R.id.promt);
		readBtn = (Button) findViewById(R.id.read_btn);
		writeBtn = (Button) findViewById(R.id.write_btn);
		deleteBtn = (Button) findViewById(R.id.delete_btn);
		// 给文本控件赋值初始文本
		promt.setText("waiting RFID tag");
		// 监听读、写、删按钮控件
		readBtn.setOnClickListener(this);
		writeBtn.setOnClickListener(this);
		deleteBtn.setOnClickListener(this);
	}

	private void initNFCData() {
		// 初始化设备支持NFC功能
		isNFC_support = true;
		// 得到默认nfc适配器
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		// 提示信息定义
		String metaInfo = "";
		// 判定设备是否支持NFC或启动NFC
		if (nfcAdapter == null) {
			metaInfo = "The device not support NFC！";
			Toast.makeText(this, metaInfo, Toast.LENGTH_SHORT).show();
			isNFC_support = false;
		}
		if (!nfcAdapter.isEnabled()) {
			metaInfo = "Please open NFC function first";
			Toast.makeText(this, metaInfo, Toast.LENGTH_SHORT).show();
			isNFC_support = false;
		}

		if (isNFC_support == true) {
			init_NFC();
		} else {
			promt.setTextColor(Color.RED);
			promt.setText(metaInfo);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nfc_demo, menu);
		return true;
	}

	// 字符序列转换为16进制字符串
	private String bytesToHexString(byte[] src) {
		return bytesToHexString(src, true);
	}

	private String bytesToHexString(byte[] src, boolean isPrefix) {
		StringBuilder stringBuilder = new StringBuilder();
		if (isPrefix == true) {
			stringBuilder.append("0x");
		}
		if (src == null || src.length <= 0) {
			return null;
		}
		char[] buffer = new char[2];
		for (int i = 0; i < src.length; i++) {
			buffer[0] = Character.toUpperCase(Character.forDigit(
					(src[i] >>> 4) & 0x0F, 16));
			buffer[1] = Character.toUpperCase(Character.forDigit(src[i] & 0x0F,
					16));
			System.out.println(buffer);
			stringBuilder.append(buffer);
		}
		return stringBuilder.toString();
	}

	/**
	 * Parses the NDEF Message from the intent and prints to the TextView
	 */
	public void processIntent(Intent intent) {
		if (isNFC_support == false)
			return;

		// 取出封装在intent中的TAG
		tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

		promt.setTextColor(Color.BLUE);
		String metaInfo = "";
		metaInfo += "Card ID:" + bytesToHexString(tagFromIntent.getId()) + "\n";
		Toast.makeText(this, "find the card", Toast.LENGTH_SHORT).show();

		// Tech List
		String prefix = "android.nfc.tech.";
		String[] techList = tagFromIntent.getTechList();

		//分析NFC卡的类型： Mifare Classic/UltraLight Info
		String CardType = "";
		for (int i = 0; i < techList.length; i++) {
			if (techList[i].equals(NfcA.class.getName())) {
				// 读取TAG
				NfcA mfc = NfcA.get(tagFromIntent);
				try {
					if ("".equals(CardType))
						CardType = "MifareClassic card \n not support NDEF message \n";
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (techList[i].equals(MifareUltralight.class.getName())) {
				MifareUltralight mifareUlTag = MifareUltralight
						.get(tagFromIntent);
				String lightType = "";
				// Type Info
				switch (mifareUlTag.getType()) {
				case MifareUltralight.TYPE_ULTRALIGHT:
					lightType = "Ultralight";
					break;
				case MifareUltralight.TYPE_ULTRALIGHT_C:
					lightType = "Ultralight C";
					break;
				}
				CardType = lightType + "card\n";

				Ndef ndef = Ndef.get(tagFromIntent);
				CardType += "the max size:" + ndef.getMaxSize() + "\n";

			}
		}
		metaInfo += CardType;
		promt.setText(metaInfo);
	}

	// 读取方法
	private String read(Tag tag) throws IOException, FormatException {
		if (tag != null) {
			//解析Tag获取到NDEF实例
			Ndef ndef = Ndef.get(tag);
			//打开连接
			ndef.connect();
			//获取NDEF消息
			NdefMessage message = ndef.getNdefMessage();
			//将消息转换成字节数组
			byte[] data = message.toByteArray();
			//将字节数组转换成字符串
			String str = new String(data, Charset.forName("UTF-8"));
			//关闭连接
			ndef.close();
			return str;
		} else {
			Toast.makeText(NfcDemoActivity.this, "The device nfc card disconnect,please reconnect...",
					Toast.LENGTH_SHORT).show();
		}
		return null;
	}

	// 写入方法
	private void write(Tag tag) throws IOException, FormatException {
		if (tag != null) {
			//新建NdefRecord数组，本例中数组只有一个元素
			NdefRecord[] records = { createRecord() };
			//新建一个NdefMessage实例
			NdefMessage message = new NdefMessage(records);
			// 解析TAG获取到NDEF实例
			Ndef ndef = Ndef.get(tag);
			// 打开连接
			ndef.connect();
			// 写入NDEF信息
			ndef.writeNdefMessage(message);
			// 关闭连接
			ndef.close();
			promt.setText(promt.getText() + "write the data success!" + "\n");
		} else {
			Toast.makeText(NfcDemoActivity.this, "The device nfc card disconnect,please reconnect...",
					Toast.LENGTH_SHORT).show();
		}
	}

	// 删除方法
	private void delete(Tag tag) throws IOException, FormatException {
		if (tag != null) {
			//新建一个里面无任何信息的NdefRecord实例
			NdefRecord nullNdefRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
					new byte[] {}, new byte[] {}, new byte[] {});
			NdefRecord[] records = { nullNdefRecord };
			NdefMessage message = new NdefMessage(records);
			// 解析TAG获取到NDEF实例
			Ndef ndef = Ndef.get(tag);
			// 打开连接
			ndef.connect();
			// 写入信息
			ndef.writeNdefMessage(message);
			// 关闭连接
			ndef.close();
			promt.setText(promt.getText() + "Delete the data success!" + "\n");
		} else {
			Toast.makeText(NfcDemoActivity.this, "The device nfc card disconnect,please reconnect...",
					Toast.LENGTH_SHORT).show();
		}
	}
	
	//返回一个NdefRecord实例
	private NdefRecord createRecord() throws UnsupportedEncodingException {
		//组装字符串，准备好你要写入的信息
		String msg = "BEGIN:VCARD\n" + "VERSION:2.1\n" + "china shandong qingdao\n"
				+ "QUST\n" + "END:VCARD";
		//将字符串转换成字节数组
		byte[] textBytes = msg.getBytes();
		//将字节数组封装到一个NdefRecord实例中去
		NdefRecord textRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
				"text/x-vCard".getBytes(), new byte[] {}, textBytes);
		return textRecord;
	}

	private MediaPlayer ring() throws Exception, IOException {
		// TODO Auto-generated method stub
		Uri alert = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		MediaPlayer player = new MediaPlayer();
		player.setDataSource(this, alert);
		final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		if (audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION) != 0) {
			player.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
			player.setLooping(false);
			player.prepare();
			player.start();
		}
		return player;
	}

	private void startNFC_Listener() {
		// 开始监听NFC设备是否连接，如果连接就发pi意图
		nfcAdapter.enableForegroundDispatch(this, pi,
				new IntentFilter[] { tagDetected }, null);
	}

	private void stopNFC_Listener() {
		// 停止监听NFC设备是否连接
		nfcAdapter.disableForegroundDispatch(this);
	}

	private void init_NFC() {
		// 初始化PendingIntent，当有NFC设备连接上的时候，就交给当前Activity处理
		pi = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
				.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		// 新建IntentFilter，使用的是第二种的过滤机制
		tagDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
		tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
	}

}
