-keepnames class com.sipus.core.data.remote.dto.LoanRequest
-if class com.sipus.core.data.remote.dto.LoanRequest
-keep class com.sipus.core.data.remote.dto.LoanRequestJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
