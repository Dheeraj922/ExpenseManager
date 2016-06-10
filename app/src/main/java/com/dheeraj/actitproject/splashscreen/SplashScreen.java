package com.dheeraj.actitproject.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.dheeraj.actitproject.R;
import com.dheeraj.actitproject.userinterface.MainActivity;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT=5000;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_screen_layout);

//        Showing splash screen with a timer of 5 seconds

    /*    new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //This method will be executed when the timer gets over
                //starting the main activity
                Intent intent=new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);

                //closing this activity
                finish();
            }
        },SPLASH_TIME_OUT);*/

        thread=new Thread(){
          @Override
        public void run(){
              try{
                  synchronized(this){
                      //wait for that much amount of time
                      wait(SPLASH_TIME_OUT);
                  }
              }catch (InterruptedException ex){}
              finish();

              //Run next Activity
              Intent intent=new Intent(SplashScreen.this,MainActivity.class);
              startActivity(intent);
          }
        };
        //start the thread

        thread.start();
    }
    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        if (motionEvent.getAction()==MotionEvent.ACTION_DOWN){
            synchronized (thread){
                thread.notifyAll();
            }
        }
        return true;
    }
}
