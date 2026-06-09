-keepnames class com.sipus.core.data.remote.dto.StaffIssueRequest
-if class com.sipus.core.data.remote.dto.StaffIssueRequest
-keep class com.sipus.core.data.remote.dto.StaffIssueRequestJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.StaffIssueRequest
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.StaffIssueRequest {
    public synthetic <init>(java.lang.String,java.lang.String,int,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
