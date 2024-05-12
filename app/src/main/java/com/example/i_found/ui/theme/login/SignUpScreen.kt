package com.example.i_found.ui.theme.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.i_found.R
import com.example.i_found.navigation.LOGIN_URL
import com.example.i_found.ui.theme.IfoundTheme
import com.example.i_found.ui.theme.Lblue

@Composable
fun SignUpScreen(
    loginViewModel: LoginViewModel? = null,
    navController: NavHostController
    ) {

    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.signUpError != null
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box {

            Image(
                painter = painterResource(id = R.drawable.signup),
                contentDescription = "login",
                modifier = Modifier.size(250.dp)
            )


        }
        Text(
            text = "Sign Up",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,
            color = Lblue
        )

        if (isError) {
            Text(
                text = loginUiState?.signUpError ?: "unknown error",
                color = Color.Red
            )
        }
        Spacer(modifier = Modifier.height(10.dp))


        OutlinedTextField(
            value = loginUiState?.userNameSignUp ?: "",
            onValueChange = { loginViewModel?.onuserNameSignupChange(it) },
            label = { Text(text = "Enter username") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "person"
                )
            },
            isError = isError
        )

        OutlinedTextField(
            value = loginUiState?.passwordSignUp ?: "",
            onValueChange = { loginViewModel?.onPasswordSignUpChange(it) },
            label = { Text(text = "Enter password") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "person"
                )
            },

            isError = isError
        )

        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { loginViewModel?.createUser(context) }) {
            Text(text = "SignUp")


        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Have an account?Login",
            fontWeight = FontWeight.Bold,
            color = Lblue,
            fontSize = 20.sp,
            modifier = Modifier.clickable {
                navController.navigate(LOGIN_URL)

            }
        )


    }

    if (loginUiState?.isLoading == true) {
        CircularProgressIndicator()
    }
    LaunchedEffect(key1 = loginViewModel?.hasUser) {
        if (loginViewModel?.hasUser == true) {

        }

    }

}

@Preview(showSystemUi = true)
@Composable
fun SignUpScreenPreview(){
    IfoundTheme{
        SignUpScreen(navController = rememberNavController())

    }


}
