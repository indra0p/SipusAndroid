-keepnames class com.sipus.core.data.remote.dto.PenaltyRuleDto
-if class com.sipus.core.data.remote.dto.PenaltyRuleDto
-keep class com.sipus.core.data.remote.dto.PenaltyRuleDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.PenaltyRuleDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.PenaltyRuleDto {
    public synthetic <init>(int,java.lang.String,java.lang.String,double,java.lang.String,java.lang.String,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
