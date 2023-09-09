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

#---------------------------------1.基本指令区域配置-qy晴雨（请关注知乎Bgwan）-----------------------
-optimizationpasses 5                       # 指定代码的压缩级别
-dontusemixedcaseclassnames                 # 是否使用大小写混合
-dontskipnonpubliclibraryclasses            # 不去忽略非公共的库类
-dontskipnonpubliclibraryclassmembers       # 不去忽略非公共的类成员变量
-dontpreverify                              # 混淆时是否做预校验
-verbose                                    # 混淆时是否记录日志
-ignorewarnings                             # 忽略警告
-dontoptimize                               # 优化不优化输入的类文件
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法
-printmapping proguardMapping.txt               # 打印Mapping文件
-keepattributes *Annotation*,InnerClasses   # 保护注解
-keepattributes Signature                   # 避免混淆泛型 如果混淆报错建议关掉
-keepattributes SourceFile,LineNumberTable  # 抛出异常时保留代码行号


#输出文件记录控制，默认会在build/outputs/mapping/release生成
#dump.txt,mapping.txt,resources.txt,seeds.txt,usage.txt
##记录生成的日志数据,gradle build时在本项目根目录输出##
#apk 包内所有 class 的内部结构
#-dump proguard/class_files.txt
#未混淆的类和成员
-printseeds proguard/seeds.txt
#列出从 apk 中删除的代码
-printusage proguard/unused.txt
#混淆前后的映射
-printmapping proguard/mapping.txt
########记录生成的日志数据，gradle build时 在本项目根目录输出-end######
#移除Log类打印各个等级日志的代码，打正式包的时候可以做为禁log使用，这里可以作为禁止log打印的功能使用，另外的一种实现方案是通过BuildConfig.DEBUG的变量来控制
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int d(...);
    public static int w(...);
    public static int v(...);
    public static int i(...);
}

#---------------------------------2.Android默认保留指令区-qy晴雨（请关注知乎Bgwan）-----------------------
-keep public class * extends android.view.View                      # 保持自定义试图类不被混淆
-keep public class * extends android.app.Fragment                   # 保持哪些类不被混淆
-keep public class * extends android.app.Activity                   # 保持哪些类不被混淆
-keep public class * extends android.app.Application                # 保持哪些类不被混淆
-keep public class * extends android.app.Service                    # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver      # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider        # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper   # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference          # 保持哪些类不被混淆
-keep public class org.apache.http.conn.ssl.SSLSocketFactory        # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService  # 保持哪些类不被混淆
#如果有引用v4,v7-start包可以添加下面内容
-keep class android.support.** {*;}
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.support.v4.app.FragmentActivity
#end v4,v7包可以添加下面内容

#android x
-keep class com.google.android.material.** {*;}
-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**
-dontwarn androidx.**
#android x

-keepclassmembers class * extends android.app.Activity {            # 保持自定义控件类不被混淆
    public void *(android.view.View);
}
-keepclasseswithmembers class * {                                   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {                                   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep public class * extends android.view.View{                     # 保持自定义控件类不被混淆
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class android.support.design.widget.**{                       # 保持自定义控件类不被混淆
    *;
}
-keepclasseswithmembernames class * {                               # 保持native方法不被混淆
    native <methods>;
}
-keepclassmembers enum * {                                          # 保持枚举enum类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {                    # 保持Parcelable不被混淆
    public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {         # 保持Serializable不被混淆
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class * implements java.io.Serializable { *;}                 # 保持Serializable不被混淆
-keep class **.R$* {                                                # 保持资源文件R不被混淆
 *;
}
-keepclassmembers class **.R$* {                                    # 保持资源文件R不被混淆
    public static <fields>;
}
-keepclassmembers class * {                                         # 保持OnXXXEvent不被混淆
    void *(**On*Event);
}
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

#---------------------------------3.webview混淆指令区-qy晴雨（请关注知乎Bgwan）-----------------------
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}




#弹窗框架 Xpopup
-dontwarn com.lxj.xpopup.widget.**
-keep class com.lxj.xpopup.widget.**{*;}

#Bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

#XBanner 图片轮播
-keep class com.stx.xhb.xbanner.**{*;}

#Glide图片加载
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}


#PictureSelector 3.0
-keep class com.luck.picture.lib.** { *; }

# 如果引入了Camerax库请添加混淆
-keep class com.luck.lib.camerax.** { *; }

# 如果引入了Ucrop库请添加混淆
-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }

#网络请求
#okhttp
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**


# Retrolambda
-dontwarn java.lang.invoke.*


# Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod
-keep class org.xz_sale.entity.**{*;}
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }


#TitleBar
-keep class com.hjq.bar.** {*;}

#MVVM快速开发框架
-keep class me.hgj.jetpackmvvm.**{*;}
################ ViewBinding & DataBinding ###############
-keepclassmembers class * implements androidx.viewbinding.ViewBinding {
  public static * inflate(android.view.LayoutInflater);
  public static * inflate(android.view.LayoutInflater, android.view.ViewGroup, boolean);
  public static * bind(android.view.View);
}

#XBanner 图片轮播混淆配置
-keep class com.stx.xhb.xbanner.**{*;}

#AgentWeb 网页浏览混淆
-keep class com.just.agentweb.** {
    *;
}
-dontwarn com.just.agentweb.**
-keepclassmembers class com.just.agentweb.sample.common.AndroidInterface{ *; }

#Serialization Json实例化混淆
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt # core serialization annotations

# kotlinx-serialization-json specific. Add this if you have java.lang.NoClassDefFoundError kotlinx.serialization.json.JsonObjectSerializer
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Change here com.yourcompany.yourpackage
-keep,includedescriptorclasses class com.example.baseapp.**$$serializer { *; } # <-- change package name to your app's
-keepclassmembers class com.example.baseapp.** { # <-- change package name to your app's
    *** Companion;
}
-keepclasseswithmembers class com.example.baseapp.** { # <-- change package name to your app's
    kotlinx.serialization.KSerializer serializer(...);
}

#二维码解析，生成
-dontwarn com.google.zxing.**
-keep class com.google.zxing.**{*;}

#UCrop
-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }






