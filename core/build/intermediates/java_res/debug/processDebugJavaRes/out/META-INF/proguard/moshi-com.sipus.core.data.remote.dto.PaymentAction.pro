-keepnames class com.sipus.core.data.remote.dto.PaymentAction
-if class com.sipus.core.data.remote.dto.PaymentAction
-keep class com.sipus.core.data.remote.dto.PaymentActionJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.sipus.core.data.remote.dto.PaymentAction
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-keepclassmembers class com.sipus.core.data.remote.dto.PaymentAction {
    public synthetic <init>(java.lang.String,int,java.lang.Double,java.lang.String,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
