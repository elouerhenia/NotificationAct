package com.rihab.notificationact;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.rihab.notificationact.model.ActualiteModel;

import java.util.List;

/**
 * Created by faten on 23/03/2017.
 */

public class ActualiteAdapter extends RecyclerView.Adapter<ActualiteAdapter.ViewHolder> {

	private Context context;
	private List<ActualiteModel> actualitesList;
	private RequestQueue queue;
	String couleur;



	public static class ViewHolder extends RecyclerView.ViewHolder {
		// each data item is just a string in this case
		public TextView tv_titre,tv_description,tv_auteur,tv_heure,txv;
		public LinearLayout lay1 ,lay2;
		public ViewHolder(View view) {
			super(view);
			tv_titre = (TextView) view.findViewById(R.id.titre);
			tv_description = (TextView) view.findViewById(R.id.contenu);
			tv_heure = (TextView) view.findViewById(R.id.heure);
			tv_auteur = (TextView) view.findViewById(R.id.auteur);
			lay1 = (LinearLayout) view.findViewById(R.id.container_post1);
			lay2 = (LinearLayout) view.findViewById(R.id.container_post2);
			txv = (TextView) view.findViewById(R.id.text_view_post);

		}
	}


	public ActualiteAdapter(Context context, List<ActualiteModel> actualitesList) {
		this.context = context;
		this.actualitesList = actualitesList;
	}
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.faten_item_actu, parent, false);

		return new ViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {
		ActualiteModel actualite = actualitesList.get(position);
		couleur = actualite.getCouleur_actu();
		holder.lay1.setBackgroundColor(Color.parseColor("#"+couleur));
		holder.lay2.setBackgroundColor(Color.parseColor("#"+couleur));
		holder.txv.setBackgroundColor(Color.parseColor("#"+couleur));
		holder.tv_titre.setText(actualite.getTitre_actu());
		holder.tv_description.setText(actualite.getDescription_actu());
		holder.tv_heure.setText(actualite.getHeure_actu());
		holder.tv_auteur.setText(actualite.getAuteurNom_actu()+" "+actualite.getAuteurPrenom_actu());



	}




	// Return the size of your dataset (invoked by the layout manager)
	@Override
	public int getItemCount() {
		return actualitesList.size();
	}
}




