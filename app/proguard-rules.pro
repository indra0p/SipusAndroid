# Add project specific ProGuard rules here.
-keep class com.sipus.core.data.remote.dto.** { *; }
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.moshi.** { *; }
-dontwarn okhttp3.**
-dontwarn retrofit2.**
