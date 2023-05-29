package com.tabletop.tabletopapplication.presentationlayer.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Timer(
    var startTime: Time = Time(),
    var step: Int = 1
) {

    private var timeLD: MutableLiveData<Time> = MutableLiveData<Time>(Time(startTime))
    private var job: Job = getTimerJob()

    private fun getTimerJob() = CoroutineScope(Dispatchers.Main).launch(start = CoroutineStart.LAZY) {
        while (timeLD.value?.toSeconds() != 0) {
            delay(step * 1000L)
            timeLD.value = timeLD.value!! - step
        }
    }

    fun start() {
        job.takeIf { !job.isActive }?.apply {
            job = getTimerJob()
            job.start()
        }
    }

    fun pause() {
        job.takeIf { job.isActive }?.apply {
            job.cancel()
        }
    }

    fun stop() {
        job.takeIf { job.isActive }?.apply {
            job.cancel()
            timeLD.value = Time(startTime)
        }
    }

    fun observe(owner: LifecycleOwner, observer: Observer<Time>) = timeLD.observe(owner, observer)

}