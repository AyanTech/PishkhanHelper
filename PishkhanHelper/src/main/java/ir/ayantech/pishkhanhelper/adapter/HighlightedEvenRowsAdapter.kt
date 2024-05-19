package ir.ayantech.pishkhanhelper.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import ir.ayantech.pishkhanhelper.R
import ir.ayantech.pishkhanhelper.databinding.RowAyanBillDetailBinding
import ir.ayantech.pishkhanhelper.model.KeyValue
import ir.ayantech.pushsdk.helper.ShareHelper
import ir.ayantech.whygoogle.adapter.CommonAdapter
import ir.ayantech.whygoogle.adapter.CommonViewHolder
import ir.ayantech.whygoogle.adapter.OnItemClickListener
import ir.ayantech.whygoogle.helper.changeVisibility

open class HighlightedEvenRowsAdapter(
    items: List<KeyValue>,
    private val startHighlightFrom: Int = 0,
    private val boldText: List<Int> = listOf(),
    private val noBold: Boolean = false,
    onItemClickListener: OnItemClickListener<KeyValue>? = null
) : CommonAdapter<KeyValue, RowAyanBillDetailBinding>(items, onItemClickListener) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommonViewHolder<KeyValue, RowAyanBillDetailBinding> {
        return super.onCreateViewHolder(parent, viewType).also {
            it.registerClickListener(it.mainView.rowCopyIv) { _ ->
                ShareHelper.copyToClipBoard(parent.context, it.item?.Value)
                Toast.makeText(
                    parent.context,
                    "${it.item?.Key} ${parent.context.resources.getString(R.string.helper_saved_to_clipboard)}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onBindViewHolder(
        holder: CommonViewHolder<KeyValue, RowAyanBillDetailBinding>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)
        holder.mainView.rowCopyIv.changeVisibility(itemsToView[position].canCopy)
        if (!noBold)
            holder.mainView.keyTv.setTypeface(holder.mainView.keyTv.typeface, Typeface.BOLD)
        if (boldText.contains(position)) {
            holder.mainView.valueTv.setTypeface(holder.mainView.valueTv.typeface, Typeface.BOLD)
        } else {
            holder.mainView.valueTv.setTypeface(holder.mainView.valueTv.typeface, Typeface.NORMAL)
        }
        holder.mainView.keyTv.text = itemsToView[position].Key
        holder.mainView.valueTv.text = itemsToView[position].Value
        holder.mainView.valueTv.changeVisibility(!itemsToView[position].Value.isNullOrEmpty())
        if (startHighlightFrom < 0) return
        holder.itemView.setBackgroundResource(
            if ((position + startHighlightFrom) % 2 == 0) R.color.helper_row_key_value_back_even_position
            else R.color.helper_row_key_value_back_odd_position
        )
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> RowAyanBillDetailBinding
        get() = RowAyanBillDetailBinding::inflate
}