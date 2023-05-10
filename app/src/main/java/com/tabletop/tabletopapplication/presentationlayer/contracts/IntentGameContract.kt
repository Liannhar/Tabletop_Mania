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
                intent?.extras?.getSerializable("Game") as Game?
            }
            else -> null
        }
}
