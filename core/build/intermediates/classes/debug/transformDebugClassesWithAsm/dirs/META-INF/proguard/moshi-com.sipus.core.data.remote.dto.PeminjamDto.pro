-keepnames class com.sipus.core.data.remote.dto.PeminjamDto
-if class com.sipus.core.data.remote.dto.PeminjamDto
-keep class com.sipus.core.data.remote.dto.PeminjamDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.PeminjamDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.PeminjamDto {
    public synthetic <init>(java.lang.String,java.lang.String,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
