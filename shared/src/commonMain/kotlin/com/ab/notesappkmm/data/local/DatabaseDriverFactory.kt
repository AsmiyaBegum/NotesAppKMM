package com.ab.notesappkmm.data.local

import app.cash.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {

    fun createDriver() : SqlDriver
}
