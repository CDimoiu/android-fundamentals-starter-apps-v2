package com.example.android.materialme

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import java.util.*


class SportsAdapter(private val context: Context, private val sportsData: ArrayList<Sport>) : RecyclerView.Adapter<SportsAdapter.ViewHolder>() {

	override fun onCreateViewHolder(
			parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val currentSport = sportsData[position]

		holder.bindTo(currentSport)
	}

	override fun getItemCount() = sportsData.size


	inner class ViewHolder
	(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

		private val titleText: TextView = itemView.findViewById(R.id.title)
		private val infoText: TextView = itemView.findViewById(R.id.subTitle)
		private val sportsImage: ImageView = itemView.findViewById(R.id.sportsImage)

		init {
			itemView.setOnClickListener(this)
		}

		fun bindTo(currentSport: Sport) {
			titleText.text = currentSport.title
			infoText.text = currentSport.info
			Glide.with(context).load(currentSport.imageResource).into(sportsImage)
		}

		override fun onClick(view: View) {
			val currentSport = sportsData[adapterPosition]
			val detailIntent = Intent(context, DetailActivity::class.java)
			detailIntent.putExtra("title", currentSport.title)
			detailIntent.putExtra("image_resource", currentSport.imageResource)
			context.startActivity(detailIntent)
		}
	}
}
