package sdm.scl.ifsp.contactlist.view

sealed interface OnContactClickListener {
    fun onContactClick(position: Int)

    fun onRemoveContactMenuItemClick(position: Int)
    fun onEditContactMenuItemClick(position: Int)

}