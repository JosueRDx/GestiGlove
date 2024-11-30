package com.josuerdx.gestiglove.utils.messages

import android.content.Context
import com.josuerdx.gestiglove.R

/**
 * Para los mensajes de éxito.
 */
object SuccessMessages {
    fun registrationSuccess(context: Context): String =
        context.getString(R.string.mensaje_registro_exitoso)
}