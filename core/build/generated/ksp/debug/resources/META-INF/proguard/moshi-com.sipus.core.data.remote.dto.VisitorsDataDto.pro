-keepnames class com.sipus.core.data.remote.dto.VisitorsDataDto
-if class com.sipus.core.data.remote.dto.VisitorsDataDto
-keep class com.sipus.core.data.remote.dto.VisitorsDataDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.VisitorsDataDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.VisitorsDataDto {
    public synthetic <init>(int,int,int,java.util.List,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
