# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#不压缩输入的类文件
-dontshrink
#指定代码的压缩级别
-optimizationpasses 5
#包明不混合大小写
-dontusemixedcaseclassnames
#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
#优化  不优化输入的类文件
-dontoptimize
#预校验
-dontpreverify
#混淆时是否记录日志
 -verbose
# 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#保护注解
-keepattributes *Annotation*
#忽略警告
-ignorewarnings

-keep public class * extends android.app.Activity      # 保持哪些类不被混淆
-keep public class * extends android.app.Application   # 保持哪些类不被混淆
-keep public class * extends android.app.Service       # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
-keep public class *

# linkkit API
-keep class com.aliyun.alink.**{*;}
-keep class com.aliyun.linksdk.**{*;}
-dontwarn com.aliyun.**
-dontwarn com.alibaba.**
-dontwarn com.alipay.**
-dontwarn com.ut.**

# keep native method
-keepclasseswithmembernames class * {
    native <methods>;
}

# keep netty
-keepattributes Signature,InnerClasses
-keepclasseswithmembers class io.netty.** {
    *;
}
-keepnames class io.netty.** {
    *;
}
-dontwarn io.netty.**
-dontwarn sun.**

# keep mqtt
-keep public class org.eclipse.paho.**{*;}

# keep fastjson
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.**{*;}

# keep gson
-keep class com.google.gson.** { *;}

# keep network core
-keep class com.http.**{*;}
-keep class org.mozilla.**{*;}

# keep okhttp
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.mozilla.**

-keep class okio.**{*;}
-keep class okhttp3.**{*;}
-keep class org.apache.commons.codec.**{*;}

-keep class com.aliyun.alink.devicesdk.demo.FileProvider{*;}
-keep class android.support.**{*;}
-keep class android.os.**{*;}
