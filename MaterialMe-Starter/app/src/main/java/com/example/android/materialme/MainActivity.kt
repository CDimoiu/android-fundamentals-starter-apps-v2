package com.example.android.materialme

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {


	private var sportsData: ArrayList<Sport>? = null
	private var sportsAdapter: SportsAdapter? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		sportsData = ArrayList()
		sportsAdapter = SportsAdapter(this, sportsData!!)
		recyclerView.apply {
			layoutManager = LinearLayoutManager(this@MainActivity)
			adapter = sportsAdapter
		}

		initializeData()

		val helper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or
				ItemTouchHelper.DOWN or ItemTouchHelper.UP, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
			override fun onMove(recyclerView: RecyclerView,
			                    viewHolder: RecyclerView.ViewHolder,
			                    target: RecyclerView.ViewHolder): Boolean {

				val from = viewHolder.adapterPosition
				val to = target.adapterPosition
				Collections.swap(sportsData, from, to)
				sportsAdapter?.notifyItemMoved(from, to)
				return true
			}

			override fun onSwiped(viewHolder: RecyclerView.ViewHolder,
			                      direction: Int) {

				if (sportsData != null && sportsAdapter != null) {
					sportsData!!.removeAt(viewHolder.adapterPosition)
					sportsAdapter!!.notifyItemRemoved(viewHolder.adapterPosition)
				}
			}
		})

		helper.attachToRecyclerView(recyclerView)
	}

	private fun initializeData() {

		val sportsList = resources
				.getStringArray(R.array.sports_titles)
		val sportsInfo = resources
				.getStringArray(R.array.sports_info)

		sportsData?.clear()

		val sportsImageResources = resources.obtainTypedArray(R.array.sports_images)

		for (i in sportsList.indices) {
			sportsData?.add(Sport(sportsList[i], sportsInfo[i], sportsImageResources.getResourceId(i, 0)))
		}

		sportsImageResources.recycle()

		sportsAdapter?.notifyDataSetChanged()
	}

	fun resetSports(view: View) {
		initializeData()
	}

}
