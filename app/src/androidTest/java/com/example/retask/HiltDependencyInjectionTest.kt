package com.example.retask

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.runner.AndroidJUnitRunner
import com.example.retask.data.LocalDatabase
import com.example.retask.data.NoteRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.manipulation.Ordering
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
        // Verify that the noteRepository is not null
        assertNotNull("NoteRepository should be injected", noteRepository)
    }
}

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LocalDatabaseTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var localDatabase: LocalDatabase

    @Before
    fun init() {
        hiltRule.inject()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        localDatabase.close()
    }

    @Test
    fun testLocalDatabaseInjection() {
        // Verify that the localDatabase is not null
        assertNotNull("LocalDatabase should be injected", localDatabase)
    }
}