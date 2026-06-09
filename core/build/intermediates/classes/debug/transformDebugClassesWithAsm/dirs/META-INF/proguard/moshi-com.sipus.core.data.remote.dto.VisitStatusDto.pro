-keepnames class com.sipus.core.data.remote.dto.VisitStatusDto
-if class com.sipus.core.data.remote.dto.VisitStatusDto
-keep class com.sipus.core.data.remote.dto.VisitStatusDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.VisitStatusDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.VisitStatusDto {
    public synthetic <init>(boolean,java.lang.String,java.lang.String,int,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
