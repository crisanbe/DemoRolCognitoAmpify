package com.uen.democognitoauthamplify.component

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.amplifyframework.core.Amplify
import com.uen.democognitoauthamplify.util.Utils

fun authenticateUser(context: Context, onAuthResult: (Boolean) -> Unit) {
    val deviceId = Utils.getDeviceIdentifier(context)
    val password = "Metro1723#@$" // Contraseña predeterminada

    Amplify.Auth.fetchAuthSession(
        { session ->
            if (session.isSignedIn) {
                Log.i("Authenticator", "Usuario ya autenticado.")
                onAuthResult(true) // Usuario ya autenticado
            } else {
                Amplify.Auth.signIn(
                    deviceId,//868033050670357
                    password,
                    { result ->
                        if (result.isSignedIn) {
                            Log.i("Authenticator", "Autenticación exitosa.")
                            onAuthResult(true)
                        } else {
                            Log.e("Authenticator", "Autenticación incompleta.")
                            showErrorAndExit("Error de autenticación: incompleta.", context)
                            onAuthResult(false)
                        }
                    },
                    { error ->
                        Log.e("Authenticator", "Error en la autenticación: ${error.message}")
                        showErrorAndExit("Error de autenticación: ${error.localizedMessage}", context)
                        onAuthResult(false)
                    }
                )
            }
        },
        { error ->
            Log.e("Authenticator", "Error al verificar la sesión: ${error.message}")
            showErrorAndExit("Error al verificar la sesión: ${error.localizedMessage}", context)
            onAuthResult(false)
        }
    )
}


private fun showErrorAndExit(message: String, context: Context) {
    Handler(Looper.getMainLooper()).post {
        android.app.AlertDialog.Builder(context)
            .setTitle("Error de autenticación")
            .setMessage(message)
            .setPositiveButton("Salir") { _, _ ->
                // Finalizar la aplicación
                if (context is Activity) {
                    context.finish()
                }
            }
            .setCancelable(false)
            .show()
    }
}