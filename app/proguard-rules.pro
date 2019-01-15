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

#----------------------------------混淆配置-----------------------------------------------
#混淆规则配置
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法
-dontskipnonpubliclibraryclasses # 不忽略非公共的库类
-optimizationpasses 5             # 代码的压缩级别
-dontusemixedcaseclassnames      # 不使用大小写混合
-dontpreverify                    # 不做预校验
-verbose                           # 记录日志
-ignorewarning                    # 忽略警告

#生成日志数据，gradle build时在本项目根目录输出
-dump class_files.txt            #apk包内所有class的内部结构
-printseeds seeds.txt            #未混淆的类和成员
-printusage unused.txt           #打印未被使用的代码
-printmapping mapping.txt        #混淆前后的映射



#----------------------------------默认保留-----------------------------------------------

-keepattributes *Annotation* InnerClasses           #不混淆注解
-keepattributes *JavascriptInterface*               #不混淆JS接口
-keepattributes Signature                           #不混淆泛型
-keepnames class * implements java.io.Serializable   #不混淆Serializable

#不混淆support包中的所有内容
-keep class android.support.** { *; }
-keep public class * extends android.support.**

#不混淆R文件
-keepclassmembers class **.R$* { *;}
#不混淆native方法
-keepclasseswithmembernames class * {
    native <methods>;
}
#不混淆枚举 enum 类
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
# 不混淆Parcelable
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}


#不混淆WebViewClient
-keepclassmembers class * extends android.webkit.WebViewClient {
     public *;
}

#----------------------------------自定义-----------------------------------------------
#Gson用到的bean类
#反射相关类
#Webview的JS接口


#----------------------------------第三方库-----------------------------------------------

#butterknife
-keep class butterknife.** { *; }
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
    @butterknife.* <methods>;
}

#Gson
-keep class com.google.gson.** { *;}
