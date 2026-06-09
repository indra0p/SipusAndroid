-keepnames class com.sipus.core.data.remote.dto.EstimasiKondisiDto
-if class com.sipus.core.data.remote.dto.EstimasiKondisiDto
-keep class com.sipus.core.data.remote.dto.EstimasiKondisiDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.EstimasiKondisiDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.EstimasiKondisiDto {
    public synthetic <init>(java.lang.String,double,double,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
