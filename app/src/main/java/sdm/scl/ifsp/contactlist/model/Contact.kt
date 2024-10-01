package sdm.scl.ifsp.contactlist.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import sdm.scl.ifsp.contactlist.model.Constant.INVALID_CONTACT_ID

@Parcelize
data class Contact(
    var id: Int? = INVALID_CONTACT_ID,
    var name: String = " ",
    var address: String = " ",
    var phone: String = " ",
    var email: String = " "
) : Parcelable
