-keepnames class com.sipus.core.data.remote.dto.NotificationDto
-if class com.sipus.core.data.remote.dto.NotificationDto
-keep class com.sipus.core.data.remote.dto.NotificationDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.NotificationDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.NotificationDto {
    public synthetic <init>(int,java.lang.String,java.lang.String,java.lang.String,boolean,java.lang.String,java.lang.String,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
