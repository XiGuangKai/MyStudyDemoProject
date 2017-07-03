package com.example.socketforandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;
    Socket socket = null;
    String message;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.editText1);
        button = (Button)findViewById(R.id.button1);

        message = editText.getText().toString();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run()
                    {
                        try {
                            //创建客户端socket,注意:不能用localhost或127.0.0.1，Android模拟器把自己作为localhost
                            socket = new Socket("10.103.162.189",18888);
                            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter
                                    (socket.getOutputStream())),true);
                            //发送数据
                            out.println(message);

                            Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
                            //接收数据
                            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            String msg = in.readLine();
                            if (null != msg){
                                editText.setText(msg);
                                System.out.println(msg);
                            }
                            else{
                                editText.setText("data error");
                            }
                            out.close();
                            in.close();
                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        finally{
                            try {
                                if (null != socket){
                                    socket.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
        });
    }
}
