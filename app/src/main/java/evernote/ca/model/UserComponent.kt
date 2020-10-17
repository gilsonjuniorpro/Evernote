package evernote.ca.model

import dagger.Component

@Component
interface UserComponent {

    fun getUser() : User

}