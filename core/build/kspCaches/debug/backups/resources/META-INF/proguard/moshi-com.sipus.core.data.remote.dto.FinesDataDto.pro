-keepnames class com.sipus.core.data.remote.dto.FinesDataDto
-if class com.sipus.core.data.remote.dto.FinesDataDto
-keep class com.sipus.core.data.remote.dto.FinesDataDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.FinesDataDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.FinesDataDto {
    public synthetic <init>(double,double,double,java.util.List,java.util.List,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
