-keepnames class com.sipus.core.data.remote.dto.AuthData
-if class com.sipus.core.data.remote.dto.AuthData
-keep class com.sipus.core.data.remote.dto.AuthDataJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
