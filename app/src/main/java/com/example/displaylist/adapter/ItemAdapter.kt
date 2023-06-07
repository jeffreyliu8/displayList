package com.example.displaylist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.displaylist.R
import com.example.displaylist.databinding.ListItemCountryBinding
import com.example.displaylist.model.Country


class ItemAdapter(private val listener: (Country) -> Unit) :
    RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    private var mList: List<Country> = emptyList()

    fun updateList(list: List<Country>) {
        mList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCountryBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) =
        holder.bind(mList[position], listener)

    inner class MyViewHolder(private val binding: ListItemCountryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Country, listener: (Country) -> Unit) = with(itemView) {
            binding.nameRegionTextView.text =
                context.getString(R.string.country_name_region, item.name, item.region)
            binding.codeTextView.text = item.code
            binding.capitalTextView.text = item.capital

            setOnClickListener {
                listener(item)
            }
        }
    }
}
