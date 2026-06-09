-keepnames class com.sipus.core.data.remote.dto.ApprovalBookDto
-if class com.sipus.core.data.remote.dto.ApprovalBookDto
-keep class com.sipus.core.data.remote.dto.ApprovalBookDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.ApprovalBookDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.ApprovalBookDto {
    public synthetic <init>(java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.Integer,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
