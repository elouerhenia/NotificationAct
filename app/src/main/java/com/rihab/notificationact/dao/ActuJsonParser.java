package com.rihab.notificationact.dao;

import com.rihab.notificationact.model.ActualiteModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by faten on 23/03/2017.
 */

public class ActuJsonParser {
	public static List<ActualiteModel> parseActu(String content){
		try {
			JSONArray ar1 = new JSONArray(content);
			List<ActualiteModel> actualiteList = new ArrayList<>();
			JSONArray ar = new JSONArray(content);
			for (int i=0; i<ar.length();i++){
				JSONObject obj = ar.getJSONObject(i);
				ActualiteModel actualite = new ActualiteModel();

				actualite.setId_actu(obj.getInt("id_actu"));
				actualite.setTitre_actu(obj.getString("titre"));
				actualite.setDescription_actu(obj.getString("description"));
				actualite.setDate_actu(obj.getString("date_publication"));
				actualite.setHeure_actu(obj.getString("heure_publication"));
				actualite.setCouleur_actu(obj.getString("couleur_actu"));
				actualite.setAuteurNom_actu(obj.getString("nom"));
				actualite.setAuteurPrenom_actu(obj.getString("prenom"));
				actualite.setType_actu(obj.getString("titre"));




				actualiteList.add(actualite);



			}
			return actualiteList;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

	}
}
