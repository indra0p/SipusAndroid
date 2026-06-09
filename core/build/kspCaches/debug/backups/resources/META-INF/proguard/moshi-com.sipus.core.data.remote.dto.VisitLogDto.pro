-keepnames class com.sipus.core.data.remote.dto.VisitLogDto
-if class com.sipus.core.data.remote.dto.VisitLogDto
-keep class com.sipus.core.data.remote.dto.VisitLogDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.VisitLogDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.VisitLogDto {
    public synthetic <init>(int,java.lang.String,java.lang.String,java.lang.String,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
