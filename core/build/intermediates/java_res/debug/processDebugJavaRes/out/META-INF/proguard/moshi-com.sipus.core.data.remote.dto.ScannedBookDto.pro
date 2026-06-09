-keepnames class com.sipus.core.data.remote.dto.ScannedBookDto
-if class com.sipus.core.data.remote.dto.ScannedBookDto
-keep class com.sipus.core.data.remote.dto.ScannedBookDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.ScannedBookDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.ScannedBookDto {
    public synthetic <init>(int,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,int,java.lang.String,java.util.List,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
