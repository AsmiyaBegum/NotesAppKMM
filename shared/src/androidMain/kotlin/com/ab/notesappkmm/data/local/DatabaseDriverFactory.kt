package com.ab.notesappkmm.data.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.ab.notesappkmm.db.NotesDatabase

actual class DatabaseDriverFactory(private val context : Context) {

    actual fun createDriver() : SqlDriver {
        return AndroidSqliteDriver(NotesDatabase.Schema,context,"notes.db" )
    }
}
