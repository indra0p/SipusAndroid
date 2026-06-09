-keepnames class com.sipus.core.data.remote.dto.BookAvailDto
-if class com.sipus.core.data.remote.dto.BookAvailDto
-keep class com.sipus.core.data.remote.dto.BookAvailDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.BookAvailDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.BookAvailDto {
    public synthetic <init>(boolean,int,int,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
