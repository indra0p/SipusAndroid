-keepnames class com.sipus.core.data.remote.dto.CheckInRequest
-if class com.sipus.core.data.remote.dto.CheckInRequest
-keep class com.sipus.core.data.remote.dto.CheckInRequestJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
