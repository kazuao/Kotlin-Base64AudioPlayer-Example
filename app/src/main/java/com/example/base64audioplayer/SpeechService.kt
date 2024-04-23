package com.example.base64audioplayer

import android.content.Context
import android.media.MediaPlayer
import android.util.Base64
import java.io.File
import java.io.FileOutputStream

interface SpeechService {
    fun speech(audioContent: String?)
    fun increase()
    fun decrease()
    fun reset()
}

class SpeechServiceImpl(
    private val context: Context,
) : SpeechService {
    private var mediaPlayer: MediaPlayer? = null

    private var contents: MutableList<String> = mutableListOf()
    private var isPlaying: Boolean = false

    override fun speech(audioContent: String?) {
        if (audioContent == null) return
        contents.add(audioContent)
        play()
    }

    override fun increase() {
        mediaPlayer?.setVolume(1f, 1f)
    }

    override fun decrease() {
        mediaPlayer?.setVolume(0.05f, 0.05f)
    }

    override fun reset() {
        stop(mediaPlayer, null)
    }

    private fun play() {
        if (isPlaying) return
        isPlaying = true

        val content = contents.firstOrNull() ?: return
        contents.removeFirst()

        val byteArray = decodeBase64ToBytes(content)
        start(byteArray)
    }

    private fun decodeBase64ToBytes(base64: String): ByteArray {
        return Base64.decode(base64, Base64.DEFAULT)
    }

    private fun start(byte: ByteArray) {
        val tempFile = createTempFileFromBytes(byte)
        mediaPlayer = MediaPlayer()

        mediaPlayer?.apply {
            setDataSource(tempFile.path)
            prepare()
            start()

            setOnCompletionListener {
                stop(it, tempFile)

                if (contents.isNotEmpty()) {
                    play()
                }
            }

            setOnErrorListener { mp, _, _ ->
                stop(mp, tempFile)
                true
            }
        }
    }

    private fun stop(mediaPlayer: MediaPlayer?, tempFile: File?) {
        mediaPlayer?.release()
        tempFile?.delete()
    }

    private fun createTempFileFromBytes(byte: ByteArray): File {
        val tempFile = File.createTempFile("tempAudio", ".mp3", context.cacheDir)
        FileOutputStream(tempFile).use { fos ->
            fos.write(byte)
        }
        return tempFile
    }
}