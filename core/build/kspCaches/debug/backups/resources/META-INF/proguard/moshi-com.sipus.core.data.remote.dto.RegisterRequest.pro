-keepnames class com.sipus.core.data.remote.dto.RegisterRequest
-if class com.sipus.core.data.remote.dto.RegisterRequest
-keep class com.sipus.core.data.remote.dto.RegisterRequestJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
