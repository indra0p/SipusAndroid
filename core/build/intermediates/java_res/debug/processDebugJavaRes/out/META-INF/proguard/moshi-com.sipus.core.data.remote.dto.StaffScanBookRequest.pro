-keepnames class com.sipus.core.data.remote.dto.StaffScanBookRequest
-if class com.sipus.core.data.remote.dto.StaffScanBookRequest
-keep class com.sipus.core.data.remote.dto.StaffScanBookRequestJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.StaffScanBookRequest
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.StaffScanBookRequest {
    public synthetic <init>(java.lang.String,java.lang.String,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
