package rachman.forniandi.artbookapp.room

import androidx.room.Database

@Database(entities= [Art::class],version = 1)
abstract class ArtDatabase {
    abstract fun artDao():ArtDao
}