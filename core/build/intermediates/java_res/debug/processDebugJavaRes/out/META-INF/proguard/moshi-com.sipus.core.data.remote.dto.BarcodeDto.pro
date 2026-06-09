-keepnames class com.sipus.core.data.remote.dto.BarcodeDto
-if class com.sipus.core.data.remote.dto.BarcodeDto
-keep class com.sipus.core.data.remote.dto.BarcodeDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
