-keepnames class com.sipus.core.data.remote.dto.ReturnCheckDto
-if class com.sipus.core.data.remote.dto.ReturnCheckDto
-keep class com.sipus.core.data.remote.dto.ReturnCheckDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.ReturnCheckDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.ReturnCheckDto {
    public synthetic <init>(int,java.lang.String,java.lang.String,com.sipus.core.data.remote.dto.PeminjamDto,java.lang.String,java.lang.String,java.lang.String,java.lang.String,int,double,java.util.List,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
