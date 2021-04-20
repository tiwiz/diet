package io.rob.diet.settings

import android.app.Application
import androidx.lifecycle.*
import com.firebase.ui.auth.AuthUI
import dagger.hilt.android.lifecycle.HiltViewModel
import io.rob.diet.common.Lce
import io.rob.diet.common.userOrEmpty
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    private val _user = MutableLiveData<Lce<User>>()

    val user : LiveData<Lce<User>>
        get() = _user

    fun loadUserData() {
        _user.postValue(Lce.Loading)
        viewModelScope.launch {
            _user.postValue(Lce.Success(userOrEmpty()))
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