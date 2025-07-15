package com.example.retask

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.retask.data.LocalDatabase
import com.example.retask.data.NoteRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import java.io.IOException
import javax.inject.Inject
import kotlin.jvm.Throws

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class HiltDependencyInjectionTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var noteRepository: NoteRepository

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testNoteRepositoryInjection() {
        assertNotNull("NoteRepository should be injected", noteRepository)
    }

}