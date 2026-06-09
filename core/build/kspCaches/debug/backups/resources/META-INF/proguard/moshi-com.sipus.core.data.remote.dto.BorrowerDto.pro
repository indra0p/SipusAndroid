-keepnames class com.sipus.core.data.remote.dto.BorrowerDto
-if class com.sipus.core.data.remote.dto.BorrowerDto
-keep class com.sipus.core.data.remote.dto.BorrowerDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.BorrowerDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.BorrowerDto {
    public synthetic <init>(int,java.lang.String,java.lang.String,java.lang.String,java.lang.String,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
