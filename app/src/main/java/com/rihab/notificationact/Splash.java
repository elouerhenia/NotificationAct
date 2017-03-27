package com.rihab.notificationact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.rihab.notificationact.model.Task;
import com.rihab.notificationact.utils.SessionManager;
import com.rihab.notificationact.views.Authentification;
import com.rihab.notificationact.views.MainActivity;


public class Splash extends AppCompatActivity implements Task.TaskFinishedListener {
		SessionManager manager;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_splash);
			manager=new SessionManager();
			// Find the progress bar
			ProgressBar progressBar = (ProgressBar) findViewById(R.id.activity_splash_progress_bar);
			// Start your loading
			new Task(progressBar, this).execute(""); // Pass in whatever you need a url is just an example we don't use it in this tutorial
		}

		// This is the callback for when your async task has finished
		@Override
		public void onTaskFinished() {
			completeSplash();
		}

	private void completeSplash(){
		startApp();
		finish(); // Don't forget to finish this Splash Activity so the user can't return to it!
	}

	private void startApp() {
		String status=manager.getPreferences(Splash.this,"status");
		String ch_type=manager.getPreferences(Splash.this,"ch_type");
		//manager.setPreferences(Authentification.this, "status", "1");
		Log.d("status", status);
		if (status.equals("1")){
			if (ch_type.equals("étudiant")) {
				Intent i = new Intent(getApplicationContext(),MainActivity.class);
				startActivity(i);
			}
			else if (ch_type.equals("enseignant")) {
				Intent i=new Intent(getApplicationContext(),MainActivity.class);
				startActivity(i);
			}
			else if (ch_type.equals("président club")){
				Intent i=new Intent(getApplicationContext(),MainActivity.class);
				startActivity(i);
			}

		}else{
			Intent i=new Intent(getApplicationContext(),Authentification.class);
			startActivity(i);
		}

		/*Intent intent = new Intent(Splash.this, Authentification.class);
		startActivity(intent);*/
	}
}

