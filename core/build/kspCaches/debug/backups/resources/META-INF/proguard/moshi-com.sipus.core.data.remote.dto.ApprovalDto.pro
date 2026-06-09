-keepnames class com.sipus.core.data.remote.dto.ApprovalDto
-if class com.sipus.core.data.remote.dto.ApprovalDto
-keep class com.sipus.core.data.remote.dto.ApprovalDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.ApprovalDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.ApprovalDto {
    public synthetic <init>(int,java.lang.Integer,com.sipus.core.data.remote.dto.ApprovalUserDto,com.sipus.core.data.remote.dto.ApprovalBookDto,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.Integer,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
