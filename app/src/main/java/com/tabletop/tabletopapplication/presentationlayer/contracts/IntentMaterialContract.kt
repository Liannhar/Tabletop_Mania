package com.tabletop.tabletopapplication.presentationlayer.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.tabletop.tabletopapplication.presentationlayer.models.Material

class IntentMaterialContract: ActivityResultContract<Intent, Material?>() {
    override fun createIntent(context: Context, input: Intent): Intent = input

    override fun parseResult(resultCode: Int, intent: Intent?): Material? =
        when(resultCode) {
            Activity.RESULT_OK -> {
                intent?.extras?.getSerializable("Material") as Material?
            }
            else -> null
        }
}