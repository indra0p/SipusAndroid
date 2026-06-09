-keepnames class com.sipus.core.data.remote.dto.CheckInResultDto
-if class com.sipus.core.data.remote.dto.CheckInResultDto
-keep class com.sipus.core.data.remote.dto.CheckInResultDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.CheckInResultDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.CheckInResultDto {
    public synthetic <init>(com.sipus.core.data.remote.dto.UserDto,java.lang.String,java.lang.String,boolean,int,java.lang.String,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
