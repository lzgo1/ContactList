package sdm.scl.ifsp.contactlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import sdm.scl.ifsp.contactlist.R
import sdm.scl.ifsp.contactlist.databinding.TileContactBinding
import sdm.scl.ifsp.contactlist.model.Contact
import sdm.scl.ifsp.contactlist.view.OnContactClickListener

class ContactRvAdapter(
    private val contactList: MutableList<Contact>,
    private val onContactClickListener: OnContactClickListener) :

    RecyclerView.Adapter<ContactRvAdapter.ContactViewHolder>() {
    inner class ContactViewHolder(tileContactBinding: TileContactBinding) :
        RecyclerView.ViewHolder(tileContactBinding.root) {
        val nameTv: TextView = tileContactBinding.nameTv
        val emailTv: TextView = tileContactBinding.emailTv
        val phoneTv: TextView = tileContactBinding.phoneTv

        init {
            tileContactBinding.root.apply {
                setOnCreateContextMenuListener { menu, view, contextMenuInfo ->
                    (onContactClickListener as AppCompatActivity).menuInflater.inflate(
                        R.menu.context_menu_main,
                        menu
                    )
                    menu.findItem(R.id.removeContactMi).setOnMenuItemClickListener {
                        onContactClickListener.onRemoveContactMenuItemClick(adapterPosition)
                        true
                    }
                    menu.findItem(R.id.editContactMi).setOnMenuItemClickListener {
                        onContactClickListener.onEditContactMenuItemClick(adapterPosition)
                        true
                    }
                }
            }
        }
    }

    override fun getItemCount() = contactList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TileContactBinding.inflate(LayoutInflater.from(parent.context), parent, false).run {
            ContactViewHolder(this)
        }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        contactList[position].also { contact ->
            with(holder) {
                nameTv.text = contact.name
                emailTv.text = contact.email
                phoneTv.text = contact.phone

                itemView.setOnClickListener {
                    onContactClickListener.onContactClick(position)
                }
            }
        }

    }
}

