# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/sellnews/Android/sdk1/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-keep com.android.support.**{*;}
-dontwarn com.android.support.**;
-keep com.google.firebase.**{*;}
-dontwarn com.google.firebase.**;
-keep com.jakewharton.**{*;}
-dontwarn com.jakewharton.**;
-keep com.tomerrosenfeld.customanalogclockview.**{*;}
-dontwarn com.tomerrosenfeld.customanalogclockview.**;
-keep com.github.k0shk0sh:RetainedDateTimePickers.**{*;}
-dontwarn com.github.k0shk0sh:RetainedDateTimePickers.**;

