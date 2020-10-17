package evernote.ca.model

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

data class Note(
    var id: Int = 0,
    var title: String? = null,
    var desc: String? = null,
    var date: String? = null,
    var body: String? = null
) {

    @Inject
    constructor() : this(0, "injected instance")

    val createdDate: String
        get() {
            val locale = Locale("pt", "BR")
            return try {
                val date : Date? = SimpleDateFormat("dd/MM/yyyy", locale).parse(date ?: "")
                return SimpleDateFormat("MMM yyyy", locale).format(date).capitalize()
            } catch (e: ParseException) {
                ""
            }
        }

}