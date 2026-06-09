-keepnames class com.sipus.core.data.remote.dto.ReturnRequest
-if class com.sipus.core.data.remote.dto.ReturnRequest
-keep class com.sipus.core.data.remote.dto.ReturnRequestJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.ReturnRequest
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.ReturnRequest {
    public synthetic <init>(int,java.lang.String,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
