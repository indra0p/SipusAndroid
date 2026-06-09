-keepnames class com.sipus.core.data.remote.dto.BookDto
-if class com.sipus.core.data.remote.dto.BookDto
-keep class com.sipus.core.data.remote.dto.BookDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.BookDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.BookDto {
    public synthetic <init>(int,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,int,java.lang.String,java.lang.Boolean,com.sipus.core.data.remote.dto.BookAvailDto,com.sipus.core.data.remote.dto.MyLoanDto,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
