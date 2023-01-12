package com.zht.modulehome.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.base.BaseViewBindingActivity;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.modulehome.databinding.ActivityJetpackBinding;

/**
 * @Date 2023/1/12 14:35
 * @Author zhanghaitao
 * @Description
 */
@Route(path = ARoutePathConstants.Home.JETPACK_ACTIVITY)
public class JetpackActivity extends BaseViewBindingActivity<ActivityJetpackBinding> {

}

//activity *	        访问基于 activity 构建的可组合 API。
//appcompat *	        允许在平台的旧版 API 上访问新 API（很多使用 Material Design）。
//camera *	            构建移动相机应用。
//compose *	            使用描述界面形状和数据依赖项的可组合函数，以编程方式定义界面。
//databinding *	        使用声明性格式将布局中的界面组件绑定到应用中的数据源。
//fragment *	        将您的应用细分为在一个 Activity 中托管的多个独立屏幕。
//hilt *	            扩展了 Dagger Hilt 的功能，以实现 androidx 库中某些类的依赖项注入。
//lifecycle *	        构建生命周期感知型组件，这些组件可以根据 activity 或 fragment 的当前生命周期状态调整行为。
//Material Design       组件*	适用于 Android 的模块化、可自定义 Material Design 界面组件。
//navigation *	        构建和组织应用内界面，处理深层链接以及在屏幕之间导航。
//paging *	            在页面中加载数据，并在 RecyclerView 中呈现。
//room *	            创建、存储和管理由 SQLite 数据库支持的持久性数据。
//test *	            在 Android 中进行测试。
//work *	            调度和执行可延期且基于约束条件的后台任务。
//car-app	            构建 Android Auto 和 Android Automotive OS 导航和地图注点应用。
//core.uwb	            在受支持的设备上实现 UWB（超宽带）。
//slice	                在应用外显示模板化界面元素。
//tv	                为开发者提供 Compose 和 Material Design 功能，以便编写 TV 应用
//tvprovider	        提供 Android TV 频道。
//wear	                打造适用于 Wear OS by Google 谷歌智能手表的应用。
//wear.compose	        编写适用于穿戴式设备的 Jetpack Compose 应用，提供相关功能来支持穿戴式设备以及该类设备专属的尺寸、形状和导航手势。
//wear.tiles	        打造适用于 Wear OS by Google 谷歌智能手表的应用。
//wear.watchface	    打造适用于 Wear OS by Google 谷歌智能手表的应用。
//window	            帮助支持不同的设备类型，例如可折叠设备。
//datastore	            以异步、一致的事务方式存储数据，克服了 SharedPreferences 的一些缺点
//health.connect	    允许开发者读取或写入用户的健康与健身记录。
//paging *	            在页面中加载数据，并在 RecyclerView 中呈现。
//preference	        无需与设备存储空间交互，也不需要管理界面，便能构建交互式设置画面。
//room *	            创建、存储和管理由 SQLite 数据库支持的持久性数据。
//sqlite	            使用本地 SQLite 数据库。如果可能，请改用 Room。
//work *	            调度和执行可延期且基于约束条件的后台任务。
//core                  core.animation 软件包支持多种常用的动画函数。
//dynamicanimation	    使用基于物理特性的动画 API 制作流畅的动画。
//interpolator	        在旧版平台上使用动画插值器。
//palette	            从图片中提取具有代表性的调色板。
//transition	        使用开始和结束布局为界面中的动作添加动画效果。
//vectordrawable	    渲染矢量图形。
//lifecycle *	        构建生命周期感知型组件，这些组件可以根据 activity 或 fragment 的当前生命周期状态调整行为。
//loader	            加载配置更改后继续存在的界面数据。
//camera *	            构建移动相机应用。
//exifinterface	        读取和写入图片文件 EXIF 标记。
//heifwriter	        使用 Android 设备上可用的编解码器，以 HEIF 格式对图像或图像集进行编码。
//media	                与其他应用共享媒体内容和控件。已被 media2 取代。
//media2	            与其他应用共享媒体内容和控件。
//media3 *	            适用于媒体用例的支持库。
//mediarouter	        利用通用界面实现在远程接收端设备上显示和播放媒体内容。
//core	                core.animation 软件包支持多种常用的动画函数。
//drawerlayout	        实现 Material Design 抽屉式导航栏 widget。
//navigation *	        构建和组织应用内界面，处理深层链接以及在屏幕之间导航。
//transition	        使用开始和结束布局为界面中的动作添加动画效果。
//biometric	            通过生物识别特征或设备凭据进行身份验证，以及执行加密操作。
//core	                针对最新的平台功能和 API 调整应用，同时还支持旧设备。
//security	            安全地管理密钥并对文件和 sharedpreferences 进行加密。
//privacysandbox.tools	用于在 Android 中利用 Privacy Sandbox 功能的库
//benchmark	            在 Android Studio 中准确评估代码的性能。
//metrics	            跟踪和报告应用的各种运行时指标
//profileinstaller	    让库能够提前预填充要由 ART 读取的编译轨迹。
//startup	            实现一种在应用启动时初始化组件的简单而高效的方法。
//test *	            在 Android 中进行测试。
//tracing	            将跟踪事件写入系统跟踪缓冲区。
//appcompat *	        允许在平台的旧版 API 上访问新 API（很多使用 Material Design）。
//cardview	            用圆角和阴影实现 Material Design 卡片模式。
//compose *	            使用描述界面形状和数据依赖项的可组合函数，以编程方式定义界面。
//constraintlayout	    使用相对定位灵活地确定 widget 的位置和大小。
//coordinatorlayout	    定位顶层应用 widget，例如 AppBarLayout 和 FloatingActionButton。
//customview	        实现自定义视图。
//databinding *	        使用声明性格式将布局中的界面组件绑定到应用中的数据源。
//draganddrop	        接受来自其他应用或应用内的拖放数据，并显示一致的拖放目标可视效果。
//emoji	                在当前设备和版本更低的设备上显示表情符号。
//fragment *	        将您的应用细分为在一个 activity 中托管的多个独立屏幕。
//graphics	            利用多个 Android 平台版本中的图形工具
//gridlayout	        实现网格布局。
//glance	            使用 Jetpack Compose 样式的 API 构建远程 surface 的布局。
//input	                通过预测未来的 MotionEvent 缩短输入互动的延迟时间
//javascriptengine	    启用您的 Android 应用以评估 JavaScript。
//Material Design 组件*	适用于 Android 的模块化、可自定义 Material Design 界面组件。
//paging *	            在页面中加载数据，并在 RecyclerView 中呈现。
//palette	            从图片中提取具有代表性的调色板。
//recyclerview	        在您的界面中显示大量数据，同时最大限度减少内存用量。
//slice	                在应用外显示模板化界面元素。
//slidingpanelayout	    实现滑动窗格界面模式。
//swiperefreshlayout	实现下拉刷新的界面模式。
//test.uiautomator	    适用于跨应用功能界面测试的框架
//viewpager	            以可滑动的格式显示视图或 Fragment。如果可能，请改用 viewpager2。
//viewpager2	        以可滑动的格式显示视图或 Fragment。
//webkit	            在 Android 5 及更高版本上使用新式 WebView API。
//activity *	        访问基于 activity 构建的可组合 API。
//ads	                获取广告 ID（无论是否通过 Play 服务）。
//annotation	        公开元数据，帮助工具开发者和其他开发者了解您的应用代码。
//appcompat *	        允许在平台的旧版 API 上访问新 API（很多使用 Material Design）。
//appsearch *	        为用户构建自定义应用内搜索功能。
//arch.core	            其他架构依赖项的帮助程序，包括可与 LiveData 配合使用的 JUnit 测试规则。
//asynclayoutinflater	异步膨胀布局以避免界面出现卡顿。
//autofill	            通过扩展提示，提高自动填充的准确性。
//benchmark	            在 Android Studio 中准确评估代码的性能。
//biometric	            通过生物识别特征或设备凭据进行身份验证，以及执行加密操作。
//browser	            在用户的默认浏览器中显示网页。
//car-app	            构建 Android Auto 和 Android Automotive OS 导航和地图注点应用。
//camera *	            构建移动相机应用。
//cardview	            用圆角和阴影实现 Material Design 卡片模式。
//collection	        降低现有和新的小型集合对内存的影响。
//compose *	            使用描述界面形状和数据依赖项的可组合函数，以编程方式定义界面。
//compose.animation	    在 Jetpack Compose 应用中构建动画，丰富用户体验。
//compose.compiler	    借助 Kotlin 编译器插件，转换 @Composable functions（可组合函数）并启用优化功能。
//compose.foundation	使用现成可用的构建块编写 Jetpack Compose 应用，还可扩展 Foundation 以构建您自己的设计系统元素。
//compose.material	    使用现成可用的 Material Design 组件构建 Jetpack Compose UI。这是更高层级的 Compose 入口点，旨在提供与 www.material.io 上描述的组件一致的组件。
//compose.material3	    使用 Material Design 3（下一代 Material Design）组件构建 Jetpack Compose 界面。Material 3 中包括了更新后的主题和组件，以及动态配色等 Material You 个性化功能，旨在与新的 Android 12 视觉风格和系统界面相得益彰。
//compose.runtime	    Compose 编程模型和状态管理的基本构建块，以及 Compose Compiler 插件针对的核心运行时。
//compose.ui	        与设备互动所需的 Compose UI 的基本组件，包括布局、绘图和输入。
//concurrent	        使用协程将任务移出主线程，并充分利用 ListenableFuture。
//constraintlayout	    使用相对定位灵活地确定 widget 的位置和大小。
//contentpager	        在后台线程中加载 ContentProvider 数据并进行分页。
//coordinatorlayout	    定位顶层应用 widget，例如 AppBarLayout 和 FloatingActionButton。
//core	                针对最新的平台功能和 API 调整应用，同时还支持旧设备。
//core.uwb	            在受支持的设备上实现 UWB（超宽带）。
//cursoradapter	        向 ListView widget 提供光标数据。
//customview	        实现自定义视图。
//databinding *	        使用声明性格式将布局中的界面组件绑定到应用中的数据源。
//datastore	            以异步、一致的事务方式存储数据，克服了 SharedPreferences 的一些缺点
//documentfile	        查看文件文档。
//draganddrop	        接受来自其他应用或应用内的拖放数据，并显示一致的拖放目标可视效果。
//drawerlayout	        实现 Material Design 抽屉式导航栏 widget。
//dynamicanimation	    使用基于物理特性的动画 API 制作流畅的动画。
//emoji	                在当前设备和版本更低的设备上显示表情符号。
//emoji2	            在当前设备和版本更低的设备上显示表情符号。
//enterprise	        创建企业专用应用。
//exifinterface	        读取和写入图片文件 EXIF 标记。
//fragment *	        将您的应用细分为在一个 Activity 中托管的多个独立屏幕。
//games	                在您的应用中以原生方式使用 Android Game SDK 来执行复杂的游戏任务，例如帧同步。
//glance	            使用 Jetpack Compose 样式的 API 构建远程 surface 的布局。
//graphics	            利用多个 Android 平台版本中的图形工具
//gridlayout	        实现网格布局。
//health	            通过平台无关的方式构建高性能健康应用。
//health.connect	    允许开发者读取或写入用户的健康与健身记录。
//heifwriter	        使用 Android 设备上可用的编解码器，以 HEIF 格式对图像或图像集进行编码。
//hilt *	            扩展了 Dagger Hilt 的功能，以实现 androidx 库中某些类的依赖项注入。
//input	                通过预测未来的 MotionEvent 缩短输入互动的延迟时间
//interpolator	        在旧版平台上使用动画插值器。
//javascriptengine	    启用您的 Android 应用以评估 JavaScript。
//jetifier	            一款独立工具，可将某个库中对已废弃支持库的依赖项迁移到等效的 AndroidX 依赖项。
//leanback	            使用适合 dpad 的 widget 和模板 fragment 为 Android TV 设备编写应用。
//legacy	            此工件及其类已废弃。从 Android 8 开始，后台检查限制使此类无法再发挥作用。
//lifecycle *	        构建生命周期感知型组件，这些组件可以根据 activity 或 fragment 的当前生命周期状态调整行为。
//loader	            加载配置更改后继续存在的界面数据。
//localbroadcastmanager	此工件及其类已废弃。请改用 LiveData 或响应式流。
//media	                与其他应用共享媒体内容和控件。已被 media2 取代。
//media2	            与其他应用共享媒体内容和控件。
//media3 *	            适用于媒体用例的支持库。
//mediarouter	        利用通用界面实现在远程接收端设备上显示和播放媒体内容。
//multidex	            在搭载 Android 5 之前版本的设备上部署包含多个 dex 文件的应用。
//metrics	            跟踪和报告应用的各种运行时指标
//navigation *	        构建和组织应用内界面，处理深层链接以及在屏幕之间导航。
//paging *	            在页面中加载数据，并在 RecyclerView 中呈现。
//palette	            从图片中提取具有代表性的调色板。
//percentlayout	        此工件及其类已废弃。请改用 ConstraintLayout 和关联布局。
//preference	        无需与设备存储空间交互，也不需要管理界面，便能构建交互式设置画面。
//print	                通过您的应用打印照片、文档、其他图形及图片。
//privacysandbox.tools	用于在 Android 中利用 Privacy Sandbox 功能的库
//profileinstaller	    让库能够提前预填充要由 ART 读取的编译轨迹。
//recommendation	    将内容推送到 Android TV 启动器的主屏幕。
//recyclerview	        在您的界面中显示大量数据，同时最大限度减少内存用量。
//remotecallback	    创建一个封装容器，以便开发者更轻松地提供 PendingIntent。
//resourceinspection	在 Android Studio 的实时布局检查器中显示自定义视图的属性。
//room *	            创建、存储和管理由 SQLite 数据库支持的持久性数据。
//savedstate	        编写可插入组件，这些组件会在进程终止时保存界面状态，并在进程重启时恢复界面状态。
//security	            安全地管理密钥并对文件和 sharedpreferences 进行加密。
//sharetarget	        提供向后兼容性，可以将快捷方式用作直接共享目标。
//slice	                在应用外显示模板化界面元素。
//slidingpanelayout	    实现滑动窗格界面模式。
//startup	            实现一种在应用启动时初始化组件的简单而高效的方法。
//sqlite	            使用本地 SQLite 数据库。如果可能，请改用 Room。
//swiperefreshlayout	实现下拉刷新的界面模式。
//test *	            在 Android 中进行测试。
//test.uiautomator	    适用于跨应用功能界面测试的框架
//textclassifier	    识别文本中的对话、链接、选定内容和其他类似构造内容。
//tracing	            将跟踪事件写入系统跟踪缓冲区。
//transition	        使用开始和结束布局为界面中的动作添加动画效果。
//tv	                为开发者提供 Compose 和 Material Design 功能，以便编写 TV 应用
//tvprovider	        提供 Android TV 频道。
//vectordrawable	    渲染矢量图形。
//versionedparcelable	提供稳定且紧凑的二进制序列化格式，该格式可跨进程传递或安全保留。
//viewpager	            以可滑动的格式显示视图或 Fragment。如果可能，请改用 viewpager2。
//viewpager2	        以可滑动的格式显示视图或 Fragment。
//wear	                打造适用于 Wear OS by Google 谷歌智能手表的应用。
//wear.compose	        编写适用于穿戴式设备的 Jetpack Compose 应用，提供相关功能来支持穿戴式设备以及该类设备专属的尺寸、形状和导航手势。
//wear.tiles	        打造适用于 Wear OS by Google 谷歌智能手表的应用。
//wear.watchface	    打造适用于 Wear OS by Google 谷歌智能手表的应用。
//webkit	            在 Android 5 及更高版本上使用新式 WebView API。
//window	            帮助支持不同的设备类型，例如可折叠设备。
//work *	            调度和执行可延期且基于约束条件的后台任务。
//Material Design 组件*	适用于 Android 的模块化、可自定义 Material Design 界面组件。