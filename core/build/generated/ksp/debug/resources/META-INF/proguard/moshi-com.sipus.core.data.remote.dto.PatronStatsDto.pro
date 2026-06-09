-keepnames class com.sipus.core.data.remote.dto.PatronStatsDto
-if class com.sipus.core.data.remote.dto.PatronStatsDto
-keep class com.sipus.core.data.remote.dto.PatronStatsDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.PatronStatsDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.PatronStatsDto {
    public synthetic <init>(int,int,double,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
