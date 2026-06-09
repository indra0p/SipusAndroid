-keepnames class com.sipus.core.data.remote.dto.VisitorDto
-if class com.sipus.core.data.remote.dto.VisitorDto
-keep class com.sipus.core.data.remote.dto.VisitorDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.VisitorDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.VisitorDto {
    public synthetic <init>(int,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
