package com.example.logindemo;

import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

public class User {
	private String mId;
	private String mPwd;
	private static final String masterPassword = "FORYOU"; // AES�����㷨������
	private static final String JSON_ID = "user_id";
	private static final String JSON_PWD = "user_pwd";
	private static final String TAG = "User";

	public User(String id, String pwd) {
		this.mId = id;
		this.mPwd = pwd;
	}

	public User(JSONObject json) throws Exception {
		if (json.has(JSON_ID)) {
			String id = json.getString(JSON_ID);
			String pwd = json.getString(JSON_PWD);
			// ���ܺ���
			mId = AESUtils.decrypt(masterPassword, id);
			mPwd = AESUtils.decrypt(masterPassword, pwd);
		}
	}

	public JSONObject toJSON() throws Exception {
		// ʹ��AES�����㷨���ܺ󱣴�
		String id = AESUtils.encrypt(masterPassword, mId);
		String pwd = AESUtils.encrypt(masterPassword, mPwd);
		Log.i(TAG, "���ܺ�:" + id + "  " + pwd);
		JSONObject json = new JSONObject();
		try {
			json.put(JSON_ID, id);
			json.put(JSON_PWD, pwd);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	public String getId() {
		return mId;
	}

	public String getPwd() {
		return mPwd;
	}
}
