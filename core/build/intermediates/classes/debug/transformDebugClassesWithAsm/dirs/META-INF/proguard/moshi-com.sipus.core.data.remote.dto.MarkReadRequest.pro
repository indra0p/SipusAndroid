-keepnames class com.sipus.core.data.remote.dto.MarkReadRequest
-if class com.sipus.core.data.remote.dto.MarkReadRequest
-keep class com.sipus.core.data.remote.dto.MarkReadRequestJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.MarkReadRequest
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.MarkReadRequest {
    public synthetic <init>(java.lang.String,java.lang.Integer,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
