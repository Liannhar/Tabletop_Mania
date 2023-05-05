package com.tabletop.tabletopapplication.presentationlayer.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import com.tabletop.tabletopapplication.presentationlayer.activities.GameEditActivity
import com.tabletop.tabletopapplication.presentationlayer.models.Game

class IntentGameContract : ActivityResultContract<Intent, Game?>() {

    override fun createIntent(context: Context, input: Intent): Intent = input

    override fun parseResult(resultCode: Int, intent: Intent?): Game? =
        when (resultCode) {
            Activity.RESULT_OK -> {
                val game = intent?.extras?.getParcelable<Game>("Game")
                Log.i("ASD", "---------")
                Log.i("ASD", game?.id.toString())
                Log.i("ASD", game?.name.toString())
                Log.i("ASD", game?.description.toString())
                Log.i("ASD", game?.image.toString())
                game
            }
            else -> null
        }
}
