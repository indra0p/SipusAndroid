-keepnames class com.sipus.core.data.remote.dto.ExtendRequest
-if class com.sipus.core.data.remote.dto.ExtendRequest
-keep class com.sipus.core.data.remote.dto.ExtendRequestJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
