-keepnames class com.sipus.core.data.remote.dto.PaymentResultDto
-if class com.sipus.core.data.remote.dto.PaymentResultDto
-keep class com.sipus.core.data.remote.dto.PaymentResultDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.PaymentResultDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.PaymentResultDto {
    public synthetic <init>(java.lang.Integer,java.lang.String,java.lang.Double,java.lang.String,java.lang.Double,java.lang.Boolean,java.lang.Double,java.lang.Double,java.lang.Double,java.lang.Double,java.lang.String,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
