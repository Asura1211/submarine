package com.example.submarine.usercon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.submarine.R;
import com.example.submarine.StartActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {


    private EditText edtName;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtName = findViewById(R.id.edtName);
        edtPassword = findViewById(R.id.edtPassword);
    }

    public void login(View view) {
        String name = edtName.getText().toString();
        String password = edtPassword.getText().toString();
        if(name.equals("") || name.isEmpty()){
            Toast.makeText(this, "用户名为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.equals("") ||password.isEmpty()){
            Toast.makeText(this, "密码为空", Toast.LENGTH_SHORT).show();
            return;
        }
        /*Connection con = null;*/
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        /*try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/test?user=root＆password=root&useUnicode=true&characterEncoding=8859_1";
            con = DriverManager.getConnection(url);
            if(!con.isClosed()) {
                Statement statement = con.createStatement();
                String sql = "select * from user";
                ResultSet rs = statement.executeQuery(sql);
                String strCount = null;
                while(rs.next()){
                    strCount = rs.getString("*");
                }
                while(true) {
                    System.out.println(strCount);
                }

            }


        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }*/
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 反复尝试连接，直到连接成功后退出循环
                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(100);  // 每隔0.1秒尝试连接
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // 2.设置好IP/端口/数据库名/用户名/密码等必要的连接信息
                    String ip = "47.100.183.78";
                    int port = 3306;
                    String dbName = "gm";
                    String url = "jdbc:mysql://" + ip + ":" + port
                            + "/" + dbName; // 构建连接mysql的字符串
                    String user = "root";
                    String password = "2001rooeROOE";
                    ResultSet rs = null;
                    String nm = null;
                    String ps = null;
                    String edName = edtName.getText().toString();
                    String edPw = edtPassword.getText().toString();

                    // 3.连接JDBC
                    try {

                        Connection conn = DriverManager.getConnection(url, user, password);
                        if(!conn.isClosed()) {
                            Statement statement = conn.createStatement();
                            String sql = "select * from user where name = '"+ edName + "'";
                            rs = statement.executeQuery(sql);
                            String strCount = null;
                            while(rs.next()){
                                nm = rs.getString("name");
                                ps = rs.getString("password");
                            }
                            if(nm == null || nm.equals("") || nm.isEmpty()){
                                Looper.prepare();
                                Toast.makeText(LoginActivity.this, "错", Toast.LENGTH_SHORT).show();
                                Looper.loop();

                            }else if(!edPw.equals(ps)){
                                Looper.prepare();
                                Toast.makeText(LoginActivity.this, "错", Toast.LENGTH_SHORT).show();
                                Looper.loop();

                            }
                            break;
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        });
        thread.start();


    }
}