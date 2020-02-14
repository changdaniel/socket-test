package com.example.sockettest;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

//    ClientSocket socket;
    private Socket socket;
    private final int SERVERPORT = 8088;
    private final String SERVER_IP = "3.134.84.232";
//    private final String SERVER_IP = "10.0.2.2"; //Works with emulator and local computer
//    private final String SERVER_IP = "127.0.0.1";

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch (requestCode) {
//            case REQUEST_CODE: {
//                if (grantResults.length > 0) {
//                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
//
//                    }
//                }
//            }
//        }
//    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        new Thread(new ClientThread()).start();
    }

    public void onClick(View view) {
        try {


            EditText et = (EditText) findViewById(R.id.EditText01);
            final String str = et.getText().toString();
            final PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())),
                    true);
            System.out.println(str);

            new Thread(new Runnable() {
                public void run() {
                    try {
                        out.write(str);

                        out.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i(TAG, "SendDataToNetwork: Message send failed. Caught an exception");
                    }
                }
            }).start();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ClientThread implements Runnable {

        @Override
        public void run() {

            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

                socket = new Socket(serverAddr, SERVERPORT);

            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException     e1) {
                e1.printStackTrace();
            }

        }

    }


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        Button btnCamera = (Button) findViewById(R.id.btnCamera);
//        socket = new ClientSocket();
//
//        btnCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view){
//
//
//                Resources res = getResources();
////                String mDrawableName = "detroittour";
////                int resID = res.getIdentifier(mDrawableName,"drawable", getPackageName());
//                Drawable drawable = ResourcesCompat.getDrawable(res, R.drawable.detroittour, null);
//                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
//
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
//
//                try{
//                    System.out.println(byteArray);
//                    socket.sendBytes(byteArray);
//                }
//                catch(Exception  e){
//                    Log.v(TAG, e.getMessage());
//                }
//            }
//        });
//    }


}
