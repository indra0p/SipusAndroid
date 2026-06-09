-keepnames class com.sipus.core.data.remote.dto.OverdueDto
-if class com.sipus.core.data.remote.dto.OverdueDto
-keep class com.sipus.core.data.remote.dto.OverdueDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.OverdueDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.OverdueDto {
    public synthetic <init>(int,java.lang.String,int,int,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
