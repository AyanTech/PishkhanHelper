package ir.ayantech.pishkhanhelper.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import ir.ayantech.pishkhanhelper.R
import ir.ayantech.pishkhanhelper.databinding.RowAyanRecyclerViewBinding
import ir.ayantech.pishkhanhelper.model.KeyValue
import ir.ayantech.whygoogle.adapter.CommonAdapter
import ir.ayantech.whygoogle.adapter.CommonViewHolder
import ir.ayantech.whygoogle.adapter.OnItemClickListener
import ir.ayantech.whygoogle.helper.verticalSetup

class HighlightedCascadeKeyValueAdapter(
    items: List<List<KeyValue>>,
    onItemClickListener: OnItemClickListener<List<KeyValue>>? = null
) : CommonAdapter<List<KeyValue>, RowAyanRecyclerViewBinding>(items, onItemClickListener) {

    override fun onBindViewHolder(
        holder: CommonViewHolder<List<KeyValue>, RowAyanRecyclerViewBinding>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)
        holder.mainView.rootRv.verticalSetup()
        holder.mainView.rootRv.adapter =
            HighlightedEvenRowsAdapter(itemsToView[position], -1)
        holder.itemView.setBackgroundResource(
            if ((position) % 2 == 0) R.color.helper_row_key_value_back_even_position
            else R.color.helper_row_key_value_back_odd_position
        )
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> RowAyanRecyclerViewBinding
        get() = RowAyanRecyclerViewBinding::inflate
}