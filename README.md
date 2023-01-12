# 搭建说明
 
### 1、新建一个Android项目,这个App只是一个空壳工程,只写一个空的Application类

### 2、在整个项目的gradle.properties定义模块的开关：isModule=false（字段可自行定义，开关可以多设置几个来控制不同模块的集成）

**注意：每次更改“isModule”的值后，需要点击 "Sync Project" 按钮**

### 3、创建几个module（可根据自己的项目模块来搭建），选择Android Library即可。

### 4、每个module的build.gradle如下修改：

    if(isModule.toBoolean()){//这里的isModule即使自己的定义的开关，可换成自己的即可
    apply plugin: 'com.android.application'
	}else{
    apply plugin: 'com.android.library'
	}

### 5、 App项目App和各个module之间的关系说明：

#### App项目中的build.gradle：

    dependencies {
	    implementation fileTree(include: ['*.jar'], dir: 'libs')
	
	    implementation project(':modulelib')//整个依赖库的导入，因此必须导入

	    if (!isModule.toBoolean()) {
			//...
	        implementation project(':modulemain')
	        implementation project(':moduleone')
	        implementation project(':moduletwo')
			//...
	    }
	}

也可以写成：


     dependencies {
	    implementation fileTree(include: ['*.jar'], dir: 'libs')
	
	    implementation project(':modulelib')//整个依赖库的导入，因此必须导入

	    //根据项目的gradle.properties来进行控制模块的集成
	    if (!isModuleMain.toBoolean()) {
	        implementation project(':modulemain')
	    }
		if (!isModuleOne.toBoolean()) {
	        implementation project(':moduleone')
	    }
		if (!isModuleTwo.toBoolean()) {
	        implementation project(':moduletwo')
	    }
	}

设置gradle.properties里面isModule的值为true/false来控制话各个模块的运行

	true：交由模块module独立运行测试
	false：合并到App项目里面运行测试

#### 各个模块的module的build.gradle

##### 1、modulelib：负责导入第三方类库、编写基类代码和工具类代码
	dependencies {
	    implementation fileTree(dir: 'libs', include: ['*.jar'])

	    testImplementation 'junit:junit:4.12'
	    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
	
	    api 'com.android.support:appcompat-v7:26+'
	
	    //ARouter
	    api 'com.alibaba:arouter-api:1.3.1'
	
	    //butterknife
	    api 'com.jakewharton:butterknife:8.5.1'
	
	    //ConstraintLayout的引用
	    api 'com.android.support.constraint:constraint-layout:1.1.2'
	}

##### 2、moduleXXX：负责各个模块功能的具体实现
	dependencies {
	    implementation fileTree(dir: 'libs', include: ['*.jar'])
	
	    implementation project(':modulelib')
	    //ARouter
	    annotationProcessor 'com.alibaba:arouter-compiler:1.1.4'
	    //butterknife
	    annotationProcessor 'com.jakewharton:butterknife-compiler:8.5.1'
	
	    //ConstraintLayout的引用
	    annotationProcessor 'com.android.support.constraint:constraint-layout:1.1.2'
	}	


 **注意：** modulelib的引用类库我们使用的是api,而不是implementation，因为在AndroidStudio3.0以后implementation只能在本module有效，在其他module及App里面是无法导入的。

依赖库引入的例子：butterknife的引入

api'com.jakewharton:butterknife:8.8.1'

annotationProcessor'com.jakewharton:butterknife-compiler:8.8.1'

api我们用于modulelib里面引入，annotationProcessor用于需要该库的module引入。

### 6、设置项目App以moduleXXX的Activity为入口（AndroidManifest.xml的修改）

App的AndroidManifest（没有任何Activity）

	<?xml version="1.0" encoding="utf-8"?>
	<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.zht.modlueapp">

	    <application
	        android:name=".AppApplication"//注意Application要写成自己的在App里面定义的
	        android:allowBackup="true"
	        android:icon="@mipmap/ic_launcher"
	        android:label="@string/app_name"
	        android:roundIcon="@mipmap/ic_launcher_round"
	        android:supportsRtl="true"
	        android:theme="@style/AppTheme">
	        
	    </application>

	</manifest>

 
moduleXXX的AndroidManifest:

	<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.zht.modulemain" >

	    <application
	        android:theme="@style/AppTheme">
	
	        <activity android:name=".MainActivity">
	            <intent-filter>
	                <action android:name="android.intent.action.MAIN"/>
	
	                <category android:name="android.intent.category.LAUNCHER"/>
	            </intent-filter>
	        </activity>
	
	        <activity android:name=".XXXActivity"/>
	
	    </application>
    
	</manifest>

### 7、每个module的单独运行调试需要有两套AndroidManifest
在module的build.gradle需要根据isModulexxx来选择不同的AndroidManifest。代码如下：

	sourceSets {
        main {
            if(isModule.toBoolean()){
				//独立运行的AndroidManifest
                manifest.srcFile 'src/main/moduleManifest/AndroidManifest.xml'
            }else{
				//集成的AndroidManifest
                manifest.srcFile 'src/main/AndroidManifest.xml'
                //集成开发模式下排除debug文件夹中的所有Java文件
                java {
                    exclude 'debug/**'
                }
            }
        }
    }

src/main/moduleManifest/AndroidManifest.xml 

	<?xml version="1.0" encoding="utf-8"?>
	<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.zht.modulexxx">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">

        <activity android:name=".activity.xxxMainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

    </application>

	</manifest>

src/main/AndroidManifest.xml

	<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.zht.modulexxx" >

	    <application
	        android:theme="@style/AppTheme">
	
	        <activity android:name=".activity.xxxMainActivity"/>
	
	    </application>

	</manifest>



### 8、 module之间的交互处理交由的阿里的ARouter。

官方链接：[https://github.com/alibaba/ARouter](https://github.com/alibaba/ARouter "https://github.com/alibaba/ARouter")

### 9、开发注意事项

1、在module里面的资源id在switch，case无法使用。解决方案：使用if,else

2、butterknife的使用问题(仅给使用butterknife的建议，目前ButterKnife已停止维护，推荐使用官方的ViewBinding)

在modulelib引用api'com.jakewharton:butterknife:8.5.1'，然后在各个module下面

	annotationProcessor'com.jakewharton:butterknife-compiler:8.5.1'

在butterknife官方文档说明,如果是在module里面使用的话还要在各个module添加

    applyplugin:'com.jakewharton.butterknife'

在modulelib不需要添加，但是使用butterknife最新版8.8.1的时候就会因为这个报错，butterknife最新版兼容性问题，需要调低版本。

module使用butterknife的R资源的问题，butterknife注解使用的资源不能用R，而要用butterknife提供的R2来寻找资源


