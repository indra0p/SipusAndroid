-keepnames class com.sipus.core.data.remote.dto.LoanDto
-if class com.sipus.core.data.remote.dto.LoanDto
-keep class com.sipus.core.data.remote.dto.LoanDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.LoanDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.LoanDto {
    public synthetic <init>(int,java.lang.Integer,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.Integer,java.lang.Integer,java.lang.Integer,java.lang.Integer,java.lang.String,java.lang.Integer,java.lang.String,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
