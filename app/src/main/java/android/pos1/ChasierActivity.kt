package android.pos1

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChasierActivity : AppCompatActivity() {

    private lateinit var btnPilihBarang: Button
    private lateinit var recyclerViewSelectedItems: RecyclerView
    private val selectedItems = mutableListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chasier)

        btnPilihBarang = findViewById(R.id.btnPilihBarang)
        recyclerViewSelectedItems = findViewById(R.id.recyclerViewSelectedItems)
        recyclerViewSelectedItems.layoutManager = LinearLayoutManager(this)

        btnPilihBarang.setOnClickListener {
            showItemModal()
        }

        updateSelectedItems()
    }

    private fun showItemModal() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.modal_items, null)
        val recyclerViewItems: RecyclerView = view.findViewById(R.id.recyclerViewItems)
        val btnConfirm: Button = view.findViewById(R.id.btnConfirm)

        recyclerViewItems.layoutManager = LinearLayoutManager(this)

        fetchItems { items ->
            val adapter = ItemSelectionAdapter(items, selectedItems)
            recyclerViewItems.adapter = adapter
        }

        btnConfirm.setOnClickListener {
            updateSelectedItems()
            dialog.dismiss()
        }

        dialog.setContentView(view)
        dialog.show()
    }

    private fun fetchItems(onItemsFetched: (List<Item>) -> Unit) {
        RetrofitClient.instance.getItems().enqueue(object : Callback<ItemResponse> {
            override fun onResponse(call: Call<ItemResponse>, response: Response<ItemResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    val items = response.body()?.data ?: emptyList()
                    onItemsFetched(items)
                } else {
                    Toast.makeText(this@ChasierActivity, "Failed to load items", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ItemResponse>, t: Throwable) {
                Toast.makeText(this@ChasierActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateSelectedItems() {
        recyclerViewSelectedItems.adapter = SelectedItemAdapter(selectedItems)
    }
}
