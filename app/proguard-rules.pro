-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keepattributes JavascriptInterface
-keepattributes *Annotation*

-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

-dontwarn com.razorpay.**
-keep class com.razorpay.** {*;}

-optimizations !method/inlining/*

-keepclasseswithmembers class * {
  public void onPayment*(...);
}

-keepclassmembers class com.paytm.pgsdk.paytmWebView$PaytmJavaScriptInterface { public *; }