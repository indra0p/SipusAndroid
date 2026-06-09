-keepnames class com.sipus.core.data.remote.dto.ProfileDto
-if class com.sipus.core.data.remote.dto.ProfileDto
-keep class com.sipus.core.data.remote.dto.ProfileDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.ProfileDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.ProfileDto {
    public synthetic <init>(int,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.Integer,java.lang.String,com.sipus.core.data.remote.dto.ProfileStatsDto,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
