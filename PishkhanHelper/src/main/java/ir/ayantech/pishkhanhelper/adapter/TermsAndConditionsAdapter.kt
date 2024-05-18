package ir.ayantech.pishkhanhelper.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import ir.ayantech.pishkhanhelper.databinding.RowTermsAndConditionsBinding
import ir.ayantech.pishkhanhelper.model.KeyValue
import ir.ayantech.whygoogle.adapter.CommonAdapter
import ir.ayantech.whygoogle.adapter.CommonViewHolder

open class TermsAndConditionsAdapter(
    items: List<KeyValue>
) : CommonAdapter<KeyValue, RowTermsAndConditionsBinding>(
    items = items
) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> RowTermsAndConditionsBinding
        get() = RowTermsAndConditionsBinding::inflate

    override fun onBindViewHolder(
        holder: CommonViewHolder<KeyValue, RowTermsAndConditionsBinding>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)
        holder.mainView.ruleTitleTv.text = itemsToView[position].Key
        holder.mainView.ruleDescriptionTv.text = itemsToView[position].Value
    }
}