package com.example.displaylist.adapter

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.example.displaylist.model.Country


class CountryDiffUtil(
    private val mOldCountryList: List<Country>,
    private val mNewCountryList: List<Country>
) : DiffUtil.Callback() {

    companion object {
        const val BUNDLE_PAYLOAD_NAME = "name"
        const val BUNDLE_PAYLOAD_REGION = "region"
        const val BUNDLE_PAYLOAD_CAPITAL = "capital"
    }


    override fun getOldListSize(): Int {
        return mOldCountryList.size
    }

    override fun getNewListSize(): Int {
        return mNewCountryList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldCountryList[oldItemPosition].code == mNewCountryList[newItemPosition].code
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldCountry = mOldCountryList[oldItemPosition]
        val newCountry = mNewCountryList[newItemPosition]

        return oldCountry.capital == newCountry.capital &&
                oldCountry.name == newCountry.name &&
//                oldCountry.flag == newCountry.flag &&
                oldCountry.region == newCountry.region
//                oldCountry.currency == newCountry.currency &&
//                oldCountry.language == newCountry.language
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldCountry = mOldCountryList[oldItemPosition]
        val newCountry = mNewCountryList[newItemPosition]
        val diff = Bundle()
        if (newCountry.name != oldCountry.name || newCountry.region != oldCountry.region) {
            diff.putString(BUNDLE_PAYLOAD_NAME, newCountry.name)
            diff.putString(BUNDLE_PAYLOAD_REGION, newCountry.region)
        }
        if (newCountry.capital != oldCountry.capital) {
            diff.putString(BUNDLE_PAYLOAD_CAPITAL, newCountry.capital)
        }
        return if (diff.size() == 0) {
            null
        } else diff
    }
}