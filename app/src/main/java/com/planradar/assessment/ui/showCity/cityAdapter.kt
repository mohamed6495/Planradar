package com.planradar.assessment.ui.showCity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.planradar.assessment.R
import com.planradar.assessment.data.model.City
import kotlinx.android.synthetic.main.item_city.view.*


class cityAdapter: RecyclerView.Adapter<cityAdapter.DefaultDataViewHolder>() {

    inner class DefaultDataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultDataViewHolder {
        return DefaultDataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((City) -> Unit)? = null
    private var onItemClickListenerToHistory: ((String) -> Unit)? = null

    override fun onBindViewHolder(holder: DefaultDataViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.itemView.apply {
            tvCityName.text=data.name+", "+data.countryCode
            setOnClickListener {
                onItemClickListener?.let { it(data) }
            }
            ivHistory.setOnClickListener {
                onItemClickListenerToHistory?.let { it(data.name.toString()) }
            }

        }
    }

    fun setOnItemClickListener(listener: (City) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnItemClickListenerToHistory(listener: (String) -> Unit) {
        onItemClickListenerToHistory = listener
    }

}














