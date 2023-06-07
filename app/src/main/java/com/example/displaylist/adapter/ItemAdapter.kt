package com.example.displaylist.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.displaylist.R
import com.example.displaylist.adapter.CountryDiffUtil.Companion.BUNDLE_PAYLOAD_CAPITAL
import com.example.displaylist.adapter.CountryDiffUtil.Companion.BUNDLE_PAYLOAD_NAME
import com.example.displaylist.adapter.CountryDiffUtil.Companion.BUNDLE_PAYLOAD_REGION
import com.example.displaylist.databinding.ListItemCountryBinding
import com.example.displaylist.model.Country


class ItemAdapter(private val listener: (Country) -> Unit) :
    RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    private var mList = mutableListOf<Country>()

    fun updateList(newCounties: List<Country>) {
        val diffCallback = CountryDiffUtil(mList, newCounties)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.mList.clear()
        this.mList.addAll(newCounties)
        diffResult.dispatchUpdatesTo(this)
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

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        } else {
            val o = payloads[0] as Bundle
            for (key in o.keySet()) {
                if (key == BUNDLE_PAYLOAD_CAPITAL) {
                    o.getString(key)?.let {
                        holder.bindCapital(it)
                    }
                }
                if (key == BUNDLE_PAYLOAD_NAME) { // also handle BUNDLE_PAYLOAD_REGION
                    val newName = o.getString(BUNDLE_PAYLOAD_NAME)
                    val newRegion = o.getString(BUNDLE_PAYLOAD_REGION)
                    holder.bindNameAndRegion(newName.orEmpty(), newRegion.orEmpty())
                }
            }
        }
    }

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

        fun bindNameAndRegion(name: String, region: String) = with(itemView) {
            binding.nameRegionTextView.text =
                context.getString(R.string.country_name_region, name, region)
        }

        fun bindCapital(capital: String) {
            binding.capitalTextView.text = capital
        }
    }
}
