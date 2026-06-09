-keepnames class com.sipus.core.data.remote.dto.DashboardDto
-if class com.sipus.core.data.remote.dto.DashboardDto
-keep class com.sipus.core.data.remote.dto.DashboardDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.DashboardDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.DashboardDto {
    public synthetic <init>(int,int,int,int,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
