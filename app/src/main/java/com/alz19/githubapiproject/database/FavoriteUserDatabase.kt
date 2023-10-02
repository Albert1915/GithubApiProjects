package com.alz19.githubapiproject.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alz19.githubapiproject.dataresponse.dao.FavoriteUserDao
import com.alz19.githubapiproject.dataresponse.data.FavoriteUserModel

@Database(entities = [FavoriteUserModel::class], version = 1)
abstract class FavoriteUserDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteUserDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteUserDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteUserDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteUserDatabase::class.java, "favorite_user"
                    )
                        .build()
                }
            }
            return INSTANCE as FavoriteUserDatabase
        }
    }
}