-keepnames class com.sipus.core.data.remote.dto.ProcessReturnRequest
-if class com.sipus.core.data.remote.dto.ProcessReturnRequest
-keep class com.sipus.core.data.remote.dto.ProcessReturnRequestJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.ProcessReturnRequest
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.ProcessReturnRequest {
    public synthetic <init>(java.lang.String,int,java.lang.String,java.lang.String,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
