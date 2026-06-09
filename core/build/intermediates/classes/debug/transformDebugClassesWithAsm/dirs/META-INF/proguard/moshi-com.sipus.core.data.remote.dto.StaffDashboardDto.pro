-keepnames class com.sipus.core.data.remote.dto.StaffDashboardDto
-if class com.sipus.core.data.remote.dto.StaffDashboardDto
-keep class com.sipus.core.data.remote.dto.StaffDashboardDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.StaffDashboardDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.StaffDashboardDto {
    public synthetic <init>(int,int,int,int,int,int,int,int,int,int,double,java.util.List,java.lang.String,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
