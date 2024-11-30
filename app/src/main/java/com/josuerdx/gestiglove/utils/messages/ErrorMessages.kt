package com.josuerdx.gestiglove.utils.messages

import android.content.Context
import com.josuerdx.gestiglove.R

/**
 * Para los mensajes de error.
 */
object ErrorMessages {
    fun invalidCredentials(context: Context): String =
        context.getString(R.string.error_credenciales_invalidas)

    fun shortPassword(context: Context): String =
        context.getString(R.string.error_contrasena_corta)

    fun invalidEmail(context: Context): String =
        context.getString(R.string.error_correo_invalido)

    fun emailInUse(context: Context): String =
        context.getString(R.string.error_correo_en_uso)

    fun genericError(context: Context): String =
        context.getString(R.string.error_generico)
}