/*
* DEPENDENCIES
*
* ROOM
* def room_version = "2.4.3"
* implementation "androidx.room:room-runtime:$room_version"
* annotationProcessor "androidx.room:room-compiler:$room_version"
*
* LIVE DATA
* def lifecycle_version = "2.6.0-alpha03"
* implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
*
* */



import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "modelclass")
data class ModelClass(
    @PrimaryKey
    val id: String,
    val name: String?
)


@Dao
interface ModelDao{

    @Query("SELECT * FROM modelclass")
    fun getAll() : LiveData<List<ModelClass>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(models : List<ModelClass>)

}

@Database(entities = [ModelClass::class], version = 1)
abstract class ModelDatabase: RoomDatabase() {
    abstract val modelDao: ModelDao

    companion object {
        @Volatile
        var INSTANCE : ModelDatabase? = null

        fun getDatabase(context: Context): ModelDatabase {
            return INSTANCE ?: synchronized(this){
                val temp = Room
                    .databaseBuilder(context, ModelDatabase::class.java, "models")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = temp
                return temp
            }
        }
    }
}