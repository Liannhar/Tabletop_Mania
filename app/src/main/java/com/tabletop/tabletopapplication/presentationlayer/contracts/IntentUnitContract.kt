package com.tabletop.tabletopapplication.presentationlayer.contracts

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class IntentUnitContract: ActivityResultContract<Intent, Unit>() {
    override fun createIntent(context: Context, input: Intent): Intent = input
    override fun parseResult(resultCode: Int, intent: Intent?) = Unit
}