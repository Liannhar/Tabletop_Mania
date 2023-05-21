package com.tabletop.tabletopapplication.presentationlayer.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.google.gson.GsonBuilder
import com.tabletop.tabletopapplication.presentationlayer.models.Material

class IntentListMaterialsContract: ActivityResultContract<Intent, List<Material>>() {
    override fun createIntent(context: Context, input: Intent): Intent = input

    override fun parseResult(resultCode: Int, intent: Intent?): List<Material> =
        when(resultCode) {
            Activity.RESULT_OK -> {
                val gson = GsonBuilder().create()
                val result = arrayListOf<Material>()

                intent?.extras?.getStringArrayList("Materials")?.forEach {
                    result.add(gson.fromJson(it, Material::class.java))
                }

                result
            }
            else -> listOf()
        }
}