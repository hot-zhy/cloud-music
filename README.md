# VaeMusic

## 简介

​		本项目基于Android和SpringBoot开发，使用okHttp+Retrofit实现音乐、动态等数据的网络请求，使用安卓自带数据库sqlite保存音乐播放列表数据，实现了音乐播放、动态发布、登录、注册等多项功能，致力于打造一个简洁但是美观、功能完备的音乐播放类APP和允许许嵩粉丝发表帖子进行交流的社交类APP。

​		服务端代码访问：https://github.com/Accepted1226/cloud-music-server

  	注意：我们部署的服务器已经到期，不再维护，用户需要在自己电脑上配置好后端才能正常运行客户端代码。

## 环境

- Android Studio Chipmunk | 2021.2.1 Patch 2
- Gradle 7.3.3
- Compile Sdk version 32

## 运行

​		拉取本仓库到本地，运行到Android Studio，点击运行等待依赖安装完成即可。

## 技术栈

1. ViewPager+Fragment+Tab
2. OkHttp+Retrofit+Rxjava
3. EventBus
4. SharedPreferences
5. permissiondispatcher动态权限处理
6. QUMI沉浸式状态栏
7. flycoTabLayout
8. sqlite

## 项目功能

​		本项目基于Android和SpringBoot开发，致力于打造一个简洁但是美观、功能完备的音乐播放类APP和允许许嵩粉丝发表帖子进行交流的社交类APP。

该项目实现了：

- 用户登录注册及个人信息维护；
- 获取服务器音乐资源并在线播放；
- 歌曲的停止、播放、拖拽进度、上/下一首切换；
- 音量调节；
- 播放模式切换；
- 黑胶唱片旋转播放；
- 发布多媒体动态；
- 获取动态列表；
- 内嵌h5访问开发团队网页；
- 转至拨号拨打客服电话。

## 项目难点

​	**1.** **黑胶唱片相关布局**

​		黑胶唱片布局实现较为困难，自定义View存放相关黑胶唱片组件，该View的xml文件中使用相对布局。

​	**2.** **黑胶唱片相关功能**

​		控制黑胶唱片的旋转、音乐进度条的拖拽、音乐播放模式的切换、音乐暂停时拖拽进度条后继续自动播放。

​	**3.** **音乐播放相关功能**

​		新建**音乐播放管理器**来控制音乐的播放、暂停、播放列表的设置、删除、恢复、获取上一首和下一首音乐、更改循环模式、音乐进度的拖拽、删除播放列表管理器中的某首音乐等事件，通过这样一个管理器来控制和协调音乐播放的相关功能是我们的难点。

​	**4.** **迷你音乐播放控制器相关功能**

​		 创建一个迷你音乐播放控制器放在首页，点击可以进到相应的音乐播放详情界面，使用EventBus监听音乐列表的的改变，当音乐播放列表没有音乐时隐藏播放控制器。

​	**5.** **其他**

​		① 使用**SharedPreferences**偏好配置类实现保存最后一次播放音乐的id，当用户关闭应用（杀死进程）重新打开时，播放的是上次关闭时播放的最后一首音乐；同时在偏好配置中保存用户的登录状态，id，session等信息以实现登录后不退出登录且session不过期一直保持登录状态；

​		② 使用**EventBus**将音乐播放列表（不是首页的音乐列表，是点击音乐播放界面右下角的列表按钮弹出的列表dialog中的音乐播放列表）的改变的事件通知给外界，改变的事件包括删除某首音乐，删除所有音乐，如果没有音乐播放列表（即当前没有音乐在播放），则隐藏首页的迷你音乐播放器；

​		③ 设置**音乐播放管理器MusicPlayerManager**和**音乐列表管理器MusicListManager**统一管理音乐播放相关事件和音乐列表改变相关事件；

​		④ 进行**okHttp+Retrofit**的封装方便后续请求网络接口以获取数据的调用；

​		⑤ 设置不同的xml进行**夜间模式**的切换；

## 实现效果
见《实验报告》和《简易使用文档》
