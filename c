[1mdiff --git a/.idea/misc.xml b/.idea/misc.xml[m
[1mindex 7158618..cca2cda 100644[m
[1m--- a/.idea/misc.xml[m
[1m+++ b/.idea/misc.xml[m
[36m@@ -37,7 +37,7 @@[m
     <ConfirmationsSetting value="0" id="Add" />[m
     <ConfirmationsSetting value="0" id="Remove" />[m
   </component>[m
[31m-  <component name="ProjectRootManager" version="2" languageLevel="JDK_1_7" default="true" assert-keyword="true" jdk-15="true" project-jdk-name="1.8" project-jdk-type="JavaSDK">[m
[32m+[m[32m  <component name="ProjectRootManager" version="2" languageLevel="JDK_1_8" default="true" assert-keyword="true" jdk-15="true" project-jdk-name="1.8" project-jdk-type="JavaSDK">[m
     <output url="file://$PROJECT_DIR$/build/classes" />[m
   </component>[m
   <component name="ProjectType">[m
[1mdiff --git a/app/build.gradle b/app/build.gradle[m
[1mindex 9a99bab..a4f07e4 100644[m
[1m--- a/app/build.gradle[m
[1m+++ b/app/build.gradle[m
[36m@@ -25,5 +25,6 @@[m [mdependencies {[m
         exclude group: 'com.android.support', module: 'support-annotations'[m
     })[m
     compile 'com.android.support:appcompat-v7:24.2.1'[m
[32m+[m[32m    compile 'com.google.android.gms:play-services:9.8.0'[m
     testCompile 'junit:junit:4.12'[m
 }[m
[1mdiff --git a/app/src/main/AndroidManifest.xml b/app/src/main/AndroidManifest.xml[m
[1mindex c7cb1a5..69dcbc0 100644[m
[1m--- a/app/src/main/AndroidManifest.xml[m
[1m+++ b/app/src/main/AndroidManifest.xml[m
[36m@@ -2,6 +2,13 @@[m
 <manifest xmlns:android="http://schemas.android.com/apk/res/android"[m
     package="com.maclaudio.knightgo">[m
 [m
[32m+[m[32m    <!--[m
[32m+[m[32m         The ACCESS_COARSE/FINE_LOCATION permissions are not required to use[m
[32m+[m[32m         Google Maps Android API v2, but you must specify either coarse or fine[m
[32m+[m[32m         location permissions for the 'MyLocation' functionality.[m[41m [m
[32m+[m[32m    -->[m
[32m+[m[32m    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />[m
[32m+[m
     <application[m
         android:allowBackup="true"[m
         android:icon="@mipmap/ic_launcher"[m
[36m@@ -15,6 +22,21 @@[m
                 <category android:name="android.intent.category.LAUNCHER" />[m
             </intent-filter>[m
         </activity>[m
[32m+[m[32m        <!--[m
[32m+[m[32m             The API key for Google Maps-based APIs is defined as a string resource.[m
[32m+[m[32m             (See the file "res/values/google_maps_api.xml").[m
[32m+[m[32m             Note that the API key is linked to the encryption key used to sign the APK.[m
[32m+[m[32m             You need a different API key for each encryption key, including the release key that is used to[m
[32m+[m[32m             sign the APK for publishing.[m
[32m+[m[32m             You can define the keys for the debug and release targets in src/debug/ and src/release/.[m[41m [m
[32m+[m[32m        -->[m
[32m+[m[32m        <meta-data[m
[32m+[m[32m            android:name="com.google.android.geo.API_KEY"[m
[32m+[m[32m            android:value="@string/google_maps_key" />[m
[32m+[m
[32m+[m[32m        <activity[m
[32m+[m[32m            android:name=".MapsActivity"[m
[32m+[m[32m            android:label="@string/title_activity_maps"></activity>[m
     </application>[m
 [m
 </manifest>[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/res/layout/activity_mainmenu.xml b/app/src/main/res/layout/activity_mainmenu.xml[m
[1mindex 200bb8d..460922b 100644[m
[1m--- a/app/src/main/res/layout/activity_mainmenu.xml[m
[1m+++ b/app/src/main/res/layout/activity_mainmenu.xml[m
[36m@@ -8,7 +8,8 @@[m
     android:paddingLeft="@dimen/activity_horizontal_margin"[m
     android:paddingRight="@dimen/activity_horizontal_margin"[m
     android:paddingTop="@dimen/activity_vertical_margin"[m
[31m-    tools:context="com.maclaudio.knightgo.mainmenu">[m
[32m+[m[32m    tools:context="com.maclaudio.knightgo.mainmenu"[m
[32m+[m[32m    android:background="@android:color/background_dark">[m
 [m
     <Button[m
         android:text="Battle"[m
[36m@@ -49,5 +50,6 @@[m
         android:layout_centerHorizontal="true"[m
         android:layout_marginTop="66dp"[m
         android:textStyle="normal|bold"[m
[31m-        android:textSize="36sp" />[m
[32m+[m[32m        android:textSize="36sp"[m
[32m+[m[32m        android:textColor="@android:color/white" />[m
 </RelativeLayout>[m
[1mdiff --git a/app/src/main/res/values/strings.xml b/app/src/main/res/values/strings.xml[m
[1mindex 30a5e8c..cf92bdd 100644[m
[1m--- a/app/src/main/res/values/strings.xml[m
[1m+++ b/app/src/main/res/values/strings.xml[m
[36m@@ -1,3 +1,4 @@[m
 <resources>[m
     <string name="app_name">knightgo</string>[m
[32m+[m[32m    <string name="title_activity_maps">Map</string>[m
 </resources>[m
warning: LF will be replaced by CRLF in .idea/misc.xml.
The file will have its original line endings in your working directory.
