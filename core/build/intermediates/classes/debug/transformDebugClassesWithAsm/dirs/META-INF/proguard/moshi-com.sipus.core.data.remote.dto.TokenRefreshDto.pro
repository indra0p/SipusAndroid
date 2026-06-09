-keepnames class com.sipus.core.data.remote.dto.TokenRefreshDto
-if class com.sipus.core.data.remote.dto.TokenRefreshDto
-keep class com.sipus.core.data.remote.dto.TokenRefreshDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.TokenRefreshDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.TokenRefreshDto {
    public synthetic <init>(java.lang.String,java.lang.Integer,com.sipus.core.data.remote.dto.UserDto,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
