package sdm.scl.ifsp.contactlist.view


import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import sdm.scl.ifsp.contactlist.databinding.ActivityContactBinding
import sdm.scl.ifsp.contactlist.model.Constant.EXTRA_CONTACT
import sdm.scl.ifsp.contactlist.model.Constant.EXTRA_VIEW_CONTACT
import sdm.scl.ifsp.contactlist.model.Contact

class ContactActivity : AppCompatActivity() {
    //act contact binding
    private val acb: ActivityContactBinding by lazy {
        ActivityContactBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(acb.root)

        setSupportActionBar(acb.toolbarIn.toolbar)
        supportActionBar?.subtitle = "Contatos Detalhes"

        val receivedContact = intent.getParcelableExtra<Contact>(EXTRA_CONTACT)
        receivedContact?.let { received ->
            val viewContact = intent.getBooleanExtra(EXTRA_VIEW_CONTACT, false)
            with(acb) {
                if (viewContact) {
                    nameEt.isEnabled = false
                    addressEt.isEnabled = false
                    phoneEt.isEnabled = false
                    emailEt.isEnabled = false
                    saveBt.visibility = GONE
                }
                nameEt.setText(received.name)
                addressEt.setText(received.address)
                phoneEt.setText(received.phone)
                emailEt.setText(received.email)
            }

        }

        with(acb) {
            saveBt.setOnClickListener {
                val contact = Contact(
                    id = receivedContact?.id ?: hashCode(),
                    name = nameEt.text.toString(),
                    address = addressEt.text.toString(),
                    phone = phoneEt.text.toString(),
                    email = emailEt.text.toString()
                )
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_CONTACT, contact)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}