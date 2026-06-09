-keepnames class com.sipus.core.data.remote.dto.PopularBookDto
-if class com.sipus.core.data.remote.dto.PopularBookDto
-keep class com.sipus.core.data.remote.dto.PopularBookDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.PopularBookDto
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.PopularBookDto {
    public synthetic <init>(java.lang.String,int,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
