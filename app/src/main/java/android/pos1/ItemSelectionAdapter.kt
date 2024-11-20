package android.pos1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemSelectionAdapter(
    private val items: List<Item>,
    private val selectedItems: MutableList<Item>
) : RecyclerView.Adapter<ItemSelectionAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemName: TextView = itemView.findViewById(R.id.itemName)
        private val itemPrice: TextView = itemView.findViewById(R.id.itemPrice)
        private val checkbox: CheckBox = itemView.findViewById(R.id.checkbox)

        fun bind(item: Item) {
            itemName.text = item.name
            itemPrice.text = "Rp ${item.price}"

            checkbox.isChecked = selectedItems.contains(item)

            checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if (!selectedItems.contains(item)) {
                        selectedItems.add(item)
                    }
                } else {
                    selectedItems.remove(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_checkbox, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
