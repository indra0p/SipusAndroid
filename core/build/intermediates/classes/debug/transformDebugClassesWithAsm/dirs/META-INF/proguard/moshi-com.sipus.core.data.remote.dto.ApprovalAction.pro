-keepnames class com.sipus.core.data.remote.dto.ApprovalAction
-if class com.sipus.core.data.remote.dto.ApprovalAction
-keep class com.sipus.core.data.remote.dto.ApprovalActionJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.ApprovalAction
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.ApprovalAction {
    public synthetic <init>(int,java.lang.String,java.lang.String,java.lang.String,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
