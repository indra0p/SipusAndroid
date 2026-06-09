-keepnames class com.sipus.core.data.remote.dto.AdminFineAction
-if class com.sipus.core.data.remote.dto.AdminFineAction
-keep class com.sipus.core.data.remote.dto.AdminFineActionJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.AdminFineAction
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.AdminFineAction {
    public synthetic <init>(java.lang.String,java.lang.Integer,java.lang.Integer,java.lang.Integer,java.lang.Integer,java.lang.String,java.lang.Double,java.lang.String,java.lang.String,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
