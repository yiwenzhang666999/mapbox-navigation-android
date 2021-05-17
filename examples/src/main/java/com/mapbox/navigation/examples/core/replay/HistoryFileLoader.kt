package com.mapbox.navigation.examples.core.replay

import android.annotation.SuppressLint
import android.content.Context
import com.mapbox.navigation.core.replay.history.ReplayEventBase
import com.mapbox.navigation.core.replay.history.ReplayHistoryMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class HistoryFileLoader(
    private val replayHistoryMapper: ReplayHistoryMapper = ReplayHistoryMapper()
) {
    @SuppressLint("MissingPermission")
    suspend fun loadReplayHistory(
        context: Context
    ): List<ReplayEventBase> = withContext(Dispatchers.IO) {
        HistoryFilesActivity.selectedHistory?.let {
            replayHistoryMapper.mapToReplayEvents(it)
        } ?: loadDefaultReplayHistory(context)
    }

    /**
     * @param fileName a file in the assets folder
     */
    @SuppressLint("MissingPermission")
    suspend fun loadReplayHistoryFile(context: Context, fileName: String): List<ReplayEventBase> =
        withContext(Dispatchers.IO) {
            val rideHistoryExample = loadHistoryJsonFromAssets(context, fileName)
            replayHistoryMapper.mapToReplayEvents(rideHistoryExample)
    }

    private suspend fun loadDefaultReplayHistory(
        context: Context
    ): List<ReplayEventBase> = withContext(Dispatchers.IO) {
        val fileName = "replay-history-activity.json"
        val rideHistoryExample = loadHistoryJsonFromAssets(context, fileName)
        replayHistoryMapper.mapToReplayEvents(rideHistoryExample)
    }

    private fun loadHistoryJsonFromAssets(context: Context, fileName: String): String {
        return try {
            val inputStream: InputStream = context.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charset.forName("UTF-8"))
        } catch (e: IOException) {
            throw e
        }
    }
}
