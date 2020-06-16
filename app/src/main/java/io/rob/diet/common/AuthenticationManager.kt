package io.rob.diet.common

import javax.inject.Inject


class AuthenticationManager @Inject constructor(){

    fun isUserAuthenticated() : Boolean = user() != null
}