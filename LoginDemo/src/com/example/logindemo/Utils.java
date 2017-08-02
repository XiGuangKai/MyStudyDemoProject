package com.example.logindemo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;
import android.util.Log;

public class Utils {

	private static final String FILENAME = "userinfo.json"; // �û������ļ���
	private static final String TAG = "Utils";

	/* �����û���¼��Ϣ�б� */
	public static void saveUserList(Context context, ArrayList<User> users)
			throws Exception {
		/* ���� */
		Log.i(TAG, "���ڱ���");
		Writer writer = null;
		OutputStream out = null;
		JSONArray array = new JSONArray();
		for (User user : users) {
			array.put(user.toJSON());
		}
		try {
			out = context.openFileOutput(FILENAME, Context.MODE_PRIVATE); // ����
			writer = new OutputStreamWriter(out);
			Log.i(TAG, "json��ֵ:" + array.toString());
			writer.write(array.toString());
		} finally {
			if (writer != null)
				writer.close();
		}

	}

	/* ��ȡ�û���¼��Ϣ�б� */
	public static ArrayList<User> getUserList(Context context) {
		/* ���� */
		FileInputStream in = null;
		ArrayList<User> users = new ArrayList<User>();
		try {

			in = context.openFileInput(FILENAME);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			JSONArray jsonArray = new JSONArray();
			String line;
			while ((line = reader.readLine()) != null) {
				jsonString.append(line);
			}
			Log.i(TAG, jsonString.toString());
			jsonArray = (JSONArray) new JSONTokener(jsonString.toString())
					.nextValue(); // ���ַ���ת����JSONArray����
			for (int i = 0; i < jsonArray.length(); i++) {
				User user = new User(jsonArray.getJSONObject(i));
				users.add(user);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return users;
	}
}
