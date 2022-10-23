/* DEPENDENCIES

Moshi
implementation 'com.squareup.moshi:moshi-kotlin:1.13.0'

Retrofit with Moshi Converter
implementation 'com.squareup.retrofit2:converter-moshi:2.9.0'

*/


import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.http.GET


private const val URL = "www.example.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(URL)
    .build()

interface ApiServices {

    @GET("endpoint")
    suspend fun getThings(): String
}

object Api {
    val retrofitService: ApiServices by lazy { retrofit.create(ApiServices::class.java) }
}