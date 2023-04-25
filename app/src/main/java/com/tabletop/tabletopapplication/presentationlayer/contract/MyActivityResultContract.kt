package com.tabletop.tabletopapplication.presentationlayer.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.tabletop.tabletopapplication.presentationlayer.activities.GameEditActivity

class Contract : ActivityResultContract<String, Int>() {
    override fun createIntent(context: Context, input: String): Intent = Intent(context,GameEditActivity::class.java).apply { putExtra("key",input) }

    override fun parseResult(resultCode: Int, intent: Intent?): Int =
        if (resultCode!=Activity.RESULT_OK) 0
        else intent?.getIntExtra("result",0)!!


}
