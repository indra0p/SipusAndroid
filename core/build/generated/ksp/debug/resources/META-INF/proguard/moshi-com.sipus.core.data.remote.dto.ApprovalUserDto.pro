-keepnames class com.sipus.core.data.remote.dto.ApprovalUserDto
-if class com.sipus.core.data.remote.dto.ApprovalUserDto
-keep class com.sipus.core.data.remote.dto.ApprovalUserDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.ApprovalUserDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.ApprovalUserDto {
    public synthetic <init>(java.lang.String,java.lang.String,java.lang.String,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
