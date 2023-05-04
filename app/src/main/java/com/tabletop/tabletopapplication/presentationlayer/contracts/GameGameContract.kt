package com.tabletop.tabletopapplication.presentationlayer.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.tabletop.tabletopapplication.presentationlayer.models.Game

class GameGameContract: ActivityResultContract<Game?, Game?>() {
    override fun createIntent(context: Context, input: Game?): Intent =
        Intent().apply {
            putExtra("Game", input)
        }

    override fun parseResult(resultCode: Int, intent: Intent?): Game? =
        when (resultCode) {
            Activity.RESULT_OK -> intent?.extras?.getParcelable("Game")
            else -> null
        }
}