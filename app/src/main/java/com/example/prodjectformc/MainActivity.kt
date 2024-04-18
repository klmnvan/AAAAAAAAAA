package com.example.prodjectformc

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prodjectformc.api.PostsRepositoryImpl
import com.example.prodjectformc.api.PostsService
import com.example.prodjectformc.ui.theme.ProdjectForMCTheme
import com.example.prodjectformc.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {
    //Создается переменная сервисов, в котором
    private val service = PostsService.create()
    //Создание провайдера ViewModel
    private val viewModel by viewModels<MainViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(PostsRepositoryImpl(service))
                        as T
            }
        }
    })
    @SuppressLint("ProduceStateDoesNotAssignValue")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProdjectForMCTheme {
                /*val posts = produceState(
                    initialValue = "",
                    producer = {
                        service.sendCode("nesklmnvan@gmail.com")
                    }
                )*/

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Navigation()
                    SendCodeToEmail(viewModel)
                    //Text(text = posts.value + " что-то", fontSize = 14.sp)
                }
            }
        }
    }

    @Composable
    fun SendCodeToEmail(viewModel: MainViewModel) {
        val email = remember { mutableStateOf("") }
        var code by remember { mutableStateOf("") }
        Column(
            Modifier.padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            TextField(value = email.value, onValueChange = { newtext -> email.value = newtext })
            Button(onClick = { viewModel.sendCodeToEmail(email.value) }) {
                Text(text = "Send code to email")
            }
            TextField(value = code, onValueChange = { newtext -> code = newtext })
            Button(onClick = { viewModel.checkCodeEmail(email.value, code) }) {
                Text(text = "Check code")
            }
        }
    }
}
