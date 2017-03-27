package com.rihab.notificationact;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rihab.notificationact.model.UserModel;
import com.rihab.notificationact.utils.EndPoints;
import com.rihab.notificationact.utils.SessionManager;
import com.rihab.notificationact.views.MainActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Authentification extends AppCompatActivity {

	EditText login;
	EditText password;
	String ch_login ;
	String ch_password;
	String ch_type;
	List<UserModel> userList;


	public static final String Login = "loginKey";
	public static final String Password = "passwordKey";
	public static final String Id = "idKey";

	SessionManager manager;
	SharedPreferences sharedpreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faten_activity_authentification);

		login = (EditText) findViewById(R.id.login);
		password = (EditText) findViewById(R.id.password);

		manager = new SessionManager();


		ActionBar actionBar = getActionBar();
		assert actionBar != null;
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		//-------------------- test de la connexion internet ------------------------
		if (!isOnline()){
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
			alertDialogBuilder.setMessage("Vous n'etes pas connecté à internet! Vérifiez votre connexion ");

			alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Toast.makeText(Authentification.this, "Vous avez besoin d'une connection internet", Toast.LENGTH_LONG).show();
				}
			});

			alertDialogBuilder.setNegativeButton("annuler",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});

			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
		}


	}


	//------------------------------ Se connecter  -----------------------

	public void seConnecter(View v) {
		ch_login = login.getText().toString() ;
		ch_password = password.getText().toString() ;

		if ((!ch_login.equals("")) && (!ch_password.equals("")) ){

			postrequestDataa(EndPoints.URL_AUTHENTIFICATION);
		}else {
			Toast.makeText(getApplicationContext(),"Veuillez remplir tous les champs",Toast.LENGTH_SHORT).show();
		}


	}

	//-------------- envoyer les données de l'utilisateur -------------
	public void postrequestDataa(String uri) {
		StringRequest postRequest = new StringRequest(Request.Method.POST, uri,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						userList = UserJsonParser.parseUser(response);
						if (!userList.isEmpty()){
							Toast.makeText(getApplicationContext(), "Connexion validée", Toast.LENGTH_SHORT).show();
							UserModel user = userList.get(0);
							int ch_userId = user.getUserId();
							String ch_typeUser = user.getTypeUser();

							if (ch_typeUser.equals("étudiant")) {
								Intent i = new Intent(getApplicationContext(),Actu_etudiant.class);
								startActivity(i);
							}
							else if (ch_typeUser.equals("enseignant")) {
								Intent i=new Intent(getApplicationContext(),MainActivity.class);
								startActivity(i);
							}
							else if (ch_typeUser.equals("président club")){}
							else {
								Toast.makeText(getApplicationContext(), "Désolé , Vous n'avez pas le droit d'accéder à notre application !", Toast.LENGTH_SHORT).show();
							}
							manager.setPreferences(Authentification.this, "status", "1");
							String status=manager.getPreferences(Authentification.this,"status");
							sharedpreferences = getSharedPreferences(SessionManager.MyPREFERENCES, Context.MODE_PRIVATE);
							SharedPreferences.Editor editor = sharedpreferences.edit();
							editor.putString(Login, ch_login);
							editor.putString(Password, ch_password);
							editor.putString(Id, String.valueOf(ch_userId));
							editor.putString(ch_type, ch_typeUser);
							editor.commit();

						}else {
							Toast.makeText(getApplicationContext(), "Vérifier votre mot de passe", Toast.LENGTH_SHORT).show();
						}

					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {

				Toast.makeText(getApplicationContext(),
						"connexion annulée", Toast.LENGTH_SHORT).show();
			}
		}) {
			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();
				//params.put("insert","true" );
				params.put("item_name",ch_login );
				params.put("item_password",ch_password );
				return params;
			}
		};
		RequestQueue queue = Volley.newRequestQueue(this);
		queue.add(postRequest);

	}
	//--------------------- Vérification de la connexion internet ------------------
	protected boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		} else {
			return false;
		}
	}
}


