import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface AttractionApi {
    @Headers("Accept: application/json")
    @GET("Attractions/All?categoryIds=12&page=1") // 这里使用实际的 Swagger 端点
    fun getAttractions(): Call<AttractionResponse>
}