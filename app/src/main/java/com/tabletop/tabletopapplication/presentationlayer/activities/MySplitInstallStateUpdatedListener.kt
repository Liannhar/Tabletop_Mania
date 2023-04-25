package com.tabletop.tabletopapplication.presentationlayer.activities

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import com.google.android.play.core.splitinstall.SplitInstallSessionState
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.tabletop.tabletopapplication.R

class MySplitInstallStateUpdatedListener(val view:View) : SplitInstallStateUpdatedListener {
    override fun onStateUpdate(state: SplitInstallSessionState) {

        val progressBar = view.findViewById<ProgressBar>(R.id.pb_horizontal)

        val installButton = view.findViewById<CardView>(R.id.card_material__install_button)
        if (state.status() == SplitInstallSessionStatus.FAILED
            && state.errorCode() == SplitInstallErrorCode.SERVICE_DIED
        ) {
            // Retry the request.
            return
        }

        when (state.status()) {
            SplitInstallSessionStatus.DOWNLOADING -> {
                progressBar.isVisible = true
                val totalBytes = state.totalBytesToDownload()
                progressBar.max = totalBytes.toInt()
                val progress = state.bytesDownloaded()
                progressBar.progress = progress.toInt()
            }

            SplitInstallSessionStatus.INSTALLED -> {
                progressBar.isVisible = false
                installButton.setBackgroundResource(R.drawable.baseline_check_24)
                //installButton.setCardBackgroundColor(R.color.green)
                Toast.makeText(
                    view.context,
                    "Module installation finished",
                    Toast.LENGTH_SHORT
                ).show()
            }

            SplitInstallSessionStatus.CANCELED -> {
            }

            SplitInstallSessionStatus.CANCELING -> {
                TODO()
            }

            SplitInstallSessionStatus.DOWNLOADED -> {
                TODO()
            }

            SplitInstallSessionStatus.FAILED -> {
                TODO()
            }

            SplitInstallSessionStatus.INSTALLING -> {
                TODO()
            }

            SplitInstallSessionStatus.PENDING -> {
                TODO()
            }

            SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                TODO()
            }

            SplitInstallSessionStatus.UNKNOWN -> {
                TODO()
            }
        }
    }
}

