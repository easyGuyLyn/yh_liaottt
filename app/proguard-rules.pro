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
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\nereus\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
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

#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------
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

#---------------------------------基本指令区----------------------------------
# 设置混淆的压缩比率 0 ~ 7
-optimizationpasses 5
# 混淆后类名都为小写   Aa aA
-dontusemixedcaseclassnames
# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses
#不做预校验的操作
-dontpreverify
# 混淆时不记录日志
-verbose
# 混淆采用的算法.
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#保留代码行号，方便异常信息的追踪
-keepattributes SourceFile,LineNumberTable

#dump文件列出apk包内所有class的内部结构
-dump class_files.txt
#seeds.txt文件列出未混淆的类和成员
-printseeds seeds.txt
#usage.txt文件列出从apk中删除的代码
-printusage unused.txt
#mapping文件列出混淆前后的映射
-printmapping mapping.txt

-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
-keepattributes JavascriptInterface
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

#---------------------------------默认保留区  公共部分---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keep class android.** { *; }

#androidx包使用混淆
-keep class com.google.android.material.** {*;}
-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**
-dontwarn androidx.**



-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}


# ---------------------------------------OkHttp3---------------------------------------------------
-dontwarn okhttp3.**
-dontwarn okio.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

# ---------------------------------------OkHttp3---------------------------------------------------



# ----------------------------------RxJava RxAndroid-----------------------------------------------
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
# ----------------------------------RxJava RxAndroid-----------------------------------------------



#-------------------------------------okhttputils--------------------------------------------------
-dontwarn com.zhy.http.**
-keep class com.zhy.http.**{*;}
#-------------------------------------okhttputils--------------------------------------------------



#----------------------------------Begin: Gson  ---------------------------------------------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**

-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

##-----------------------------------End: Gson ------------------------------------- ----------




#---------------------------------------Rxbus--------------------------------------------------
-keep class com.hwangjr.rxbus.** { *; }
#---------------------------------------Rxbus------------------------------------------------



-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
     public void *(android.webkit.WebView, jav.lang.String);
}


#---------------------------------webview------------------------------------



#移除Log类打印各个等级日志的代码，打正式包的时候可以做为禁log使用，这里可以作为禁止log打印的功能使用，另外的一种实现方案是通过BuildConfig.DEBUG的变量来控制
-assumenosideeffects class android.util.Log {
    public static *** v(...);
    public static *** i(...);
    public static *** d(...);
   public static *** w(...);
   public static *** e(...);
}


#########################################################
#         retrofit                                      #
#                                                       #
-dontnote retrofit2.Platform                            #
-dontnote retrofit2.Platform$IOS$MainThreadExecutor     #
-dontwarn retrofit2.Platform$Java8                      #
-keepattributes Signature                               #
-keepattributes Exceptions                              #
-dontwarn okio.**                                       #
-dontwarn javax.annotation.**                           #
#                                                       #
#########################################################



############################################################################################################
#                                           glide                                                          #
#                                                                                                          #
-keep public class * implements com.bumptech.glide.module.GlideModule                                      #
-keep public class * extends com.bumptech.glide.module.AppGlideModule                                      #
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {                           #
  **[] $VALUES;                                                                                            #
  public *;                                                                                                #
}                                                                                                          #
#                                                                                                          #
# for DexGuard only                                                                                        #
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule                                  #
-keep class com.bumptech.glide.integration.okhttp.OkHttpGlideModule                                       #
#                                                                                                          #
############################################################################################################
##########################com.youth.banner:banner##############################################

# glide 的混淆代码
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
###############################################################################################


#######################################################
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
   @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
   @butterknife.* <methods>;
}

##########################################################
#         org.apache.httpcomponents:httpcore:4.4.4
-keep class android.net.http.** { *; }
-keep class org.apache.http.** { *; }
-dontwarn android.net.http.**
-dontwarn org.apache.http.**



########################
#        腾讯x5
-keep class com.tencent.** { *; }
-dontwarn com.tencent.smtt.export.external.**

-dontskipnonpubliclibraryclassmembers
-dontwarn dalvik.**
# --------------------------------------------------------------------------
# Addidional for x5.sdk classes for apps

#------------------  下方是共性的排除项目         ----------------
# 方法名中含有“JNI”字符的，认定是Java Native Interface方法，自动排除
# 方法名中含有“JRI”字符的，认定是Java Reflection Interface方法，自动排除

-keepclasseswithmembers class * {
    ... *JNI*(...);
}

-keepclasseswithmembernames class * {
	... *JRI*(...);
}

-keep class **JNI* {*;}
#---------------------------------------------------------------------------


##------------------------------------------------------------
-keep class com.alibaba.fastjson.** { *; }
-dontwarn com.alibaba.fastjson.**
##------------------------------------------------------------


-keep public class **.R$*{
   public static final int *;
}

-keep class com.nwf.sports.utils.ssl.**{*;}
-keep class com.nwf.sports.mvp.**{*;}
-keep class com.nwf.sports.net.**{*;}   # 对retrofit框架的封装，不可混淆。

-keep class com.nwf.sports.utils.GameWebSetting{*;}   #------项目中的x5 settings不混淆
#---------------------------------第三方包-------------------------------



#---------------------依赖注入---------------------
-dontwarn javax.annotation.**
-dontwarn javax.inject.**
#---------------------依赖注入----------------------

#忽略警告 不忽略可能打包不成功
-ignorewarnings

#不要压缩(这个必须，因为开启混淆的时候 默认 会把没有被调用的代码 全都排除掉)
-dontshrink


-keep class !cn.wildfire.chat.moment.**,!cn.wildfirechat.moment.**, **{ *; }
-keep class cn.wildfirechat.moment.MomentClient {
    public void init(***);
}






