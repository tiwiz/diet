package io.rob.diet.storage

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test


class DietDatabaseTest {

    private val context = InstrumentationRegistry.getInstrumentation().context
    private val testDbName = "test_db.db"

    @Test
    fun verify_prepopulated_database_is_created_properly() {
        DietDatabase.buildDataBase(context, testDbName)
    }
}