package com.mapbox.navigation.ui.voice.api

import android.media.AudioManager
import com.mapbox.navigation.ui.voice.options.PlayerAttributes
import com.mapbox.navigation.ui.voice.options.VoiceInstructionsPlayerOptions
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test

class PreOreoAudioFocusDelegateTest {

    @Test
    fun `pre oreo audio focus delegate request focus`() {
        val mockedAudioManager: AudioManager = mockk(relaxed = true)
        val mockedVoiceInstructionsPlayerOptions: VoiceInstructionsPlayerOptions = mockk()
        every {
            mockedVoiceInstructionsPlayerOptions.focusGain
        } returns AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK
        every {
            mockedVoiceInstructionsPlayerOptions.playerAttributes
        } returns PlayerAttributes.PreOreoAttributes()

        val preOreoAudioFocusDelegate = PreOreoAudioFocusDelegate(
            mockedAudioManager,
            mockedVoiceInstructionsPlayerOptions
        )

        preOreoAudioFocusDelegate.requestFocus()

        verify(exactly = 1) {
            mockedAudioManager.requestAudioFocus(
                null,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK
            )
        }
    }

    @Test
    fun `pre oreo audio delegate returns true when audio focus is granted`() {
        val mockedAudioManager = mockk<AudioManager>(relaxed = true)
        val mockedVoiceInstructionsPlayerOptions: VoiceInstructionsPlayerOptions = mockk()
        every {
            mockedVoiceInstructionsPlayerOptions.focusGain
        } returns AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK
        every {
            mockedVoiceInstructionsPlayerOptions.playerAttributes
        } returns PlayerAttributes.PreOreoAttributes()
        every {
            mockedAudioManager.requestAudioFocus(
                null,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK
            )
        } returns AudioManager.AUDIOFOCUS_REQUEST_GRANTED

        val preOreoAudioFocusDelegate = PreOreoAudioFocusDelegate(
            mockedAudioManager,
            mockedVoiceInstructionsPlayerOptions
        )

        assertEquals(
            true,
            preOreoAudioFocusDelegate.requestFocus(),
        )

        verify(exactly = 1) {
            mockedAudioManager.requestAudioFocus(
                null,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK
            )
        }
    }

    @Test
    fun `pre oreo audio delegate returns false when audio focus is failed`() {
        val mockedAudioManager = mockk<AudioManager>(relaxed = true)
        val mockedVoiceInstructionsPlayerOptions: VoiceInstructionsPlayerOptions = mockk()
        every {
            mockedVoiceInstructionsPlayerOptions.focusGain
        } returns AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK
        every {
            mockedVoiceInstructionsPlayerOptions.playerAttributes
        } returns PlayerAttributes.PreOreoAttributes()
        every {
            mockedAudioManager.requestAudioFocus(
                null,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK
            )
        } returns AudioManager.AUDIOFOCUS_REQUEST_FAILED

        val preOreoAudioFocusDelegate = PreOreoAudioFocusDelegate(
            mockedAudioManager,
            mockedVoiceInstructionsPlayerOptions
        )

        assertEquals(
            preOreoAudioFocusDelegate.requestFocus(),
            false
        )

        verify(exactly = 1) {
            mockedAudioManager.requestAudioFocus(
                null,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK
            )
        }
    }

    @Test
    fun `pre oreo audio delegate returns true when audio focus is delayed`() {
        val mockedAudioManager = mockk<AudioManager>(relaxed = true)
        val mockedVoiceInstructionsPlayerOptions: VoiceInstructionsPlayerOptions = mockk()
        every {
            mockedVoiceInstructionsPlayerOptions.focusGain
        } returns AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK
        every {
            mockedVoiceInstructionsPlayerOptions.playerAttributes
        } returns PlayerAttributes.PreOreoAttributes()

        every {
            mockedAudioManager.requestAudioFocus(
                null,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK
            )
        } returns AudioManager.AUDIOFOCUS_REQUEST_DELAYED

        val preOreoAudioFocusDelegate = PreOreoAudioFocusDelegate(
            mockedAudioManager,
            mockedVoiceInstructionsPlayerOptions
        )

        assertEquals(
            preOreoAudioFocusDelegate.requestFocus(),
            true
        )

        verify(exactly = 1) {
            mockedAudioManager.requestAudioFocus(
                null,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK
            )
        }
    }

    @Test
    fun `pre oreo audio focus delegate abandon focus`() {
        val mockedAudioManager: AudioManager = mockk(relaxed = true)
        val preOreoAudioFocusDelegate = PreOreoAudioFocusDelegate(mockedAudioManager, mockk())

        preOreoAudioFocusDelegate.abandonFocus()

        verify(exactly = 1) {
            mockedAudioManager.abandonAudioFocus(null)
        }
    }

    @Test
    fun `pre oreo audio focus delegate abandon focus returns true when focus is granted`() {
        val mockedAudioManager: AudioManager = mockk(relaxed = true)

        every {
            mockedAudioManager.abandonAudioFocus(null)
        } returns AudioManager.AUDIOFOCUS_REQUEST_GRANTED

        val preOreoAudioFocusDelegate = PreOreoAudioFocusDelegate(mockedAudioManager, mockk())

        assertEquals(
            preOreoAudioFocusDelegate.abandonFocus(),
            true
        )
        verify(exactly = 1) {
            mockedAudioManager.abandonAudioFocus(null)
        }
    }

    @Test
    fun `pre oreo audio focus delegate abandon focus returns false when focus is failed`() {
        val mockedAudioManager: AudioManager = mockk(relaxed = true)

        every {
            mockedAudioManager.abandonAudioFocus(null)
        } returns AudioManager.AUDIOFOCUS_REQUEST_FAILED

        val preOreoAudioFocusDelegate = PreOreoAudioFocusDelegate(mockedAudioManager, mockk())

        assertEquals(
            preOreoAudioFocusDelegate.abandonFocus(),
            false
        )
        verify(exactly = 1) {
            mockedAudioManager.abandonAudioFocus(null)
        }
    }
}
