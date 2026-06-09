-keepnames class com.sipus.core.data.remote.dto.ApiResponse
-if class com.sipus.core.data.remote.dto.ApiResponse
-keep class com.sipus.core.data.remote.dto.ApiResponseJsonAdapter {
    public <init>(com.squareup.moshi.Moshi,java.lang.reflect.Type[]);
}
-if class com.sipus.core.data.remote.dto.ApiResponse
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.ApiResponse {
    public synthetic <init>(java.lang.String,java.lang.Object,java.lang.String,java.lang.Integer,java.lang.Integer,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
