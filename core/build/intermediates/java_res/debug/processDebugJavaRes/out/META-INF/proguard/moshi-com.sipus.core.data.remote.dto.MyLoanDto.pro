-keepnames class com.sipus.core.data.remote.dto.MyLoanDto
-if class com.sipus.core.data.remote.dto.MyLoanDto
-keep class com.sipus.core.data.remote.dto.MyLoanDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
