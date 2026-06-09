-keepnames class com.sipus.core.data.remote.dto.PatronLoanDto
-if class com.sipus.core.data.remote.dto.PatronLoanDto
-keep class com.sipus.core.data.remote.dto.PatronLoanDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.PatronLoanDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.PatronLoanDto {
    public synthetic <init>(int,java.lang.String,java.lang.String,java.lang.String,java.lang.String,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
