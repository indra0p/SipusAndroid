-keepnames class com.sipus.core.data.remote.dto.RequestReturnBody
-if class com.sipus.core.data.remote.dto.RequestReturnBody
-keep class com.sipus.core.data.remote.dto.RequestReturnBodyJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.RequestReturnBody
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.RequestReturnBody {
    public synthetic <init>(java.lang.String,int,java.lang.String,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
