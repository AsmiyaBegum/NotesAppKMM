package com.ab.notesappkmm.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.ab.notesappkmm.db.NotesDatabase

actual class DatabaseDriverFactory {

    actual fun createDriver() : SqlDriver {
        return NativeSqliteDriver(NotesDatabase.Schema,"notes.db")
    }
}