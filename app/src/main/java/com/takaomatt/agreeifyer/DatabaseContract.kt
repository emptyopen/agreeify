package com.takaomatt.agreeifyer

import android.provider.BaseColumns

object DBContract {

    /* Inner class that defines the table contents */
    class UserEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "choices"
            val COLUMN_ID = "id"
            val COLUMN_DESCRIPTION = "column_description"
            val COLUMN_TAGS = "tags"
            val COLUMN_TEAM1 = "team1"
            val COLUMN_TEAM2 = "team2"
        }
    }
}