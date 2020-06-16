package io.rob.diet.settings

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import io.rob.diet.common.Lce
import io.rob.diet.common.userOrEmpty
import kotlinx.coroutines.launch

class SettingsViewModel @ViewModelInject constructor(application: Application) : AndroidViewModel(application) {

    private val _user = MutableLiveData<Lce<User>>()

    val user : LiveData<Lce<User>>
        get() = _user

    fun loadUserData() {
        _user.postValue(Lce.Loading)
        viewModelScope.launch {
            _user.postValue(Lce.Success(FirebaseAuth.getInstance().userOrEmpty()))
        }
    }

    fun logOut() {
        _user.postValue(Lce.Loading)
        AuthUI.getInstance()
            .signOut(getApplication())
            .addOnCompleteListener {
                _user.postValue(Lce.Success(User.empty))
            }
    }
}