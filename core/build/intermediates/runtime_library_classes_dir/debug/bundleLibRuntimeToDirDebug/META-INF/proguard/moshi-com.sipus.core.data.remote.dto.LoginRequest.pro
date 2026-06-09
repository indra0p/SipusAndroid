-keepnames class com.sipus.core.data.remote.dto.LoginRequest
-if class com.sipus.core.data.remote.dto.LoginRequest
-keep class com.sipus.core.data.remote.dto.LoginRequestJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
