-keepnames class com.sipus.core.data.remote.dto.PatronProfileDto
-if class com.sipus.core.data.remote.dto.PatronProfileDto
-keep class com.sipus.core.data.remote.dto.PatronProfileDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.PatronProfileDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.PatronProfileDto {
    public synthetic <init>(int,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,int,com.sipus.core.data.remote.dto.PatronStatsDto,java.util.List,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
