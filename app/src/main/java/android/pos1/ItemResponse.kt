package android.pos1

data class ItemResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<Item>
)

data class Item(
    val id: String,
    val name: String,
    val price: String,
    val uom: String
)
