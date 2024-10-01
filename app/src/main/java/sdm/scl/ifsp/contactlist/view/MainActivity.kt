package sdm.scl.ifsp.contactlist.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import sdm.scl.ifsp.contactlist.R
import sdm.scl.ifsp.contactlist.adapter.ContactRvAdapter
import sdm.scl.ifsp.contactlist.databinding.ActivityMainBinding
import sdm.scl.ifsp.contactlist.model.Constant.EXTRA_CONTACT
import sdm.scl.ifsp.contactlist.model.Constant.EXTRA_VIEW_CONTACT
import sdm.scl.ifsp.contactlist.model.Contact

class MainActivity : AppCompatActivity(), OnContactClickListener {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    //DATA SOURCE
    private val contactList: MutableList<Contact> = mutableListOf()

    private val contactAdapter: ContactRvAdapter by lazy {
        ContactRvAdapter(contactList, this)
    }

    //carl=contact act result launcher
    private lateinit var carl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.toolbarIn.toolbar)
        supportActionBar?.subtitle = "Contatos Lista"

        carl =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val contact = result.data?.getParcelableExtra<Contact>(EXTRA_CONTACT)
                    contact?.also { newOrEditContact ->
                        if (contactList.any { it.id == newOrEditContact.id }) {
                            val position = contactList.indexOfFirst { it.id == newOrEditContact.id }
                            contactList[position] = newOrEditContact
                            contactAdapter.notifyItemChanged(position)
                        } else {
                            contactList.add(newOrEditContact)
                            contactAdapter.notifyItemInserted(contactList.lastIndex)
                            //?
                            //contactAdapter.add(contact.toString())
                        }
                        //contactAdapter.notifyDataSetChanged()
                    }
                }
            }
        fillContacts()

        //amb.contactsLv.adapter = contactAdapter
        amb.contactsRv.adapter = contactAdapter
        amb.contactsRv.layoutManager = LinearLayoutManager(this)
        //registerForContextMenu(amb.contactsLv)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addContactMi -> {
                carl.launch(Intent(this, ContactActivity::class.java))
                true
            }

            else -> {
                false
            }
        }

    }

    override fun onRemoveContactMenuItemClick(position: Int) {
        contactList.removeAt(position)
        contactAdapter.notifyItemRemoved(position)
        //contactAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Contato Removido", Toast.LENGTH_SHORT).show()
    }

    override fun onEditContactMenuItemClick(position: Int) {
        carl.launch(Intent(this, ContactActivity::class.java).apply {
            putExtra(EXTRA_CONTACT, contactList[position])
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        //unregisterForContextMenu(amb.contactsLv)
    }

    override fun onContactClick(position: Int) {
        Intent(this, ContactActivity::class.java).apply {
            putExtra(EXTRA_CONTACT, contactList[position])
            putExtra(EXTRA_VIEW_CONTACT, true)
        }.also { startActivity(it) }

    }

    private fun fillContacts() {
        for (i in 1..15) {//FIZ COM 15 NA AULA FOI 50
            contactList.add(
                Contact(
                    i,
                    "Nome $i",
                    "Endereço $i",
                    "Telefone $i",
                    "Email $i"
                )
            )
        }
    }
}