package com.rihab.notificationact.dao;

import com.rihab.notificationact.model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by faten on 23/03/2017.
 */

public class UserJsonParser {
	public static List<UserModel> parseUser(String content){
		try {
			JSONArray ar = new JSONArray(content);
			List<UserModel> userList = new ArrayList<>();

			for (int i=0; i<ar.length();i++){
				JSONObject obj = ar.getJSONObject(i);
				UserModel user = new UserModel();
				user.setUserId(obj.getInt("id_user"));
				user.setTypeUser(obj.getString("type"));


				userList.add(user);

			}
			return userList;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

	}

}
