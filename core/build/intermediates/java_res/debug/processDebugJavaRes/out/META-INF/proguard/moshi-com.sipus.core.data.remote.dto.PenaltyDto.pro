-keepnames class com.sipus.core.data.remote.dto.PenaltyDto
-if class com.sipus.core.data.remote.dto.PenaltyDto
-keep class com.sipus.core.data.remote.dto.PenaltyDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.PenaltyDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.PenaltyDto {
    public synthetic <init>(int,java.lang.String,double,double,double,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
