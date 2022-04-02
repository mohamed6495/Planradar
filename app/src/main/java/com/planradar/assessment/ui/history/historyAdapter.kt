package com.planradar.assessment.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.planradar.assessment.R
import com.planradar.assessment.data.model.WeatherData
import com.planradar.assessment.utils.Constants
import com.planradar.assessment.utils.Constants.Companion.WEATHER_ICON_SIZE_FORMAT
import kotlinx.android.synthetic.main.item_weather_history.view.*
import kotlin.math.roundToInt


class historyAdapter: RecyclerView.Adapter<historyAdapter.DefaultDataViewHolder>() {

    inner class DefaultDataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<WeatherData>() {
        override fun areItemsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultDataViewHolder {
        return DefaultDataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_weather_history, parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((WeatherData) -> Unit)? = null

    override fun onBindViewHolder(holder: DefaultDataViewHolder, position: Int) {
        val data = differ.currentList[position]
        val weather = data.weather[0]
        holder.itemView.apply {
            ("${weather.description}, ${ fromKelvinToCelsius(data.main.temp!!)} C")
                .also { tvDescriptionTemp.text = it }
            tvDateTime.text = data.dateTime
            Glide.with(context)
                .load("${Constants.ICON_URL}${weather.icon}${WEATHER_ICON_SIZE_FORMAT}")
                .into(ivWeatherIcon)



        }
    }



    private fun fromKelvinToCelsius(k: Double): Double {
        return ((k - 273.15) * 100).roundToInt() / 100.0
    }

}














