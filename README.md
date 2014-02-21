MyCommon
========
android library+gradle+混淆

library中project说明:
MyCommonDemo为demo演示代码，library中为common代码。
WdUtil:封装了util类，作为所有project的基础library;
WdFramework:快速开发框架，构建了程序的最基本结构;
WdCommonUI:封装了一些自定义UI;
MyAndroidCommon:功能模块，包含了数据库（基于ormlite）、http（基于android-async-http）、网络图片加载（基于android-universal-image）、eventbus、缓存（基于guava与DiskLruCache）、静默与非静默安装卸载等;
MyFrameworkExt:基于framework.jar而非android-sdk构建，提供了一些非sdk提供而系统framework本身支持的方法;
DownloadProvider-master:基于android源码的下载模块;

参考:
eventbus:
https://github.com/greenrobot/EventBus

android-async-http:
https://github.com/loopj/android-async-http

android-universal-image:
https://github.com/nostra13/Android-Universal-Image-Loader

ormlite
http://ormlite.com/releases/

fastjson
https://github.com/alibaba/fastjson/wiki

DiskLruCache
https://github.com/JakeWharton/DiskLruCache

DownloadProvider
https://github.com/yxl/DownloadProvider


