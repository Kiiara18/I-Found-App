package com.example.i_found.ui.theme.login

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.i_found.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository = AuthRepository()

) :ViewModel() {
    val currentUser = repository.currentUser

    val hasUser: Boolean
        get() = repository.hasUser()

    var loginUiState by mutableStateOf(LoginUiState())
        private set

    fun onUserNameChange(userName: String){
        loginUiState = loginUiState.copy(userName = userName)
    }
    fun onPasswordChange(password: String){
        loginUiState = loginUiState.copy(password = password)
    }
    fun onuserNameSignupChange(userName: String){
        loginUiState = loginUiState.copy(userNameSignUp = userName)
    }
    fun onPasswordSignUpChange(password: String){
        loginUiState = loginUiState.copy(passwordSignUp = password)
    }
    fun onconfirmPasswordChange(password: String){
        loginUiState = loginUiState.copy(confirmpasswordSignUp =password)
    }

    private fun validateLoginForm()=
        loginUiState.userName.isBlank() &&
                loginUiState.password.isNotBlank()

    private fun validateSignUpForm()=
        loginUiState.userNameSignUp.isNotBlank() &&
                loginUiState.passwordSignUp.isNotBlank() &&
                loginUiState.confirmpasswordSignUp.isNotBlank()



    fun createUser(context: Context) = viewModelScope.launch {
        try {

            loginUiState = loginUiState.copy(isLoading = true)


            loginUiState = loginUiState.copy(signUpError = null)
            repository.createUser(
                loginUiState.userNameSignUp,
                loginUiState.passwordSignUp
            ){isSuccessful ->
                if (isSuccessful){
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    loginUiState = loginUiState.copy(isSuccess = true )
                } else{
                    Toast.makeText(context, "Failed login", Toast.LENGTH_SHORT).show()
                    loginUiState = loginUiState.copy(isSuccess = false )

                }

            }


        }catch(e:Exception){
            loginUiState=loginUiState.copy(signUpError = e.localizedMessage)
            e.printStackTrace()


        }finally {
            loginUiState=loginUiState.copy(isLoading = false)
        }

    }

    fun loginUser(context: Context) = viewModelScope.launch {
        try {

            loginUiState = loginUiState.copy(isLoading = true)
            loginUiState = loginUiState.copy(loginError = null)
            repository.login(
                loginUiState.userName,
                loginUiState.password
            ){isSuccessful ->
                if (isSuccessful){
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    loginUiState = loginUiState.copy(isSuccess = true )
                } else{
                    Toast.makeText(context, "Failed login", Toast.LENGTH_SHORT).show()
                    loginUiState = loginUiState.copy(isSuccess = false )

                }

            }


        }catch(e:Exception){
            loginUiState=loginUiState.copy(loginError = e.localizedMessage)
            e.printStackTrace()


        }finally {
            loginUiState=loginUiState.copy(isLoading = false)
        }

    }









}

data class LoginUiState(
    val userName:String ="",
    val password:String = "",
    val userNameSignUp:String = "",
    val passwordSignUp:String = "",
    val confirmpasswordSignUp:String = "",
    val isLoading:Boolean = false,
    val isSuccess:Boolean = false,
    val signUpError:String? = null,
    val loginError:String? = null,



    )