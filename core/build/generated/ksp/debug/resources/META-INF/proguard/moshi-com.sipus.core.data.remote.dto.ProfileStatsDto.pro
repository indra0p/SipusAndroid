-keepnames class com.sipus.core.data.remote.dto.ProfileStatsDto
-if class com.sipus.core.data.remote.dto.ProfileStatsDto
-keep class com.sipus.core.data.remote.dto.ProfileStatsDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.ProfileStatsDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.ProfileStatsDto {
    public synthetic <init>(int,int,int,double,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
