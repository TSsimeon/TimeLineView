# TimeLineView

### 使用方法
  * 版本基于`x.y.z`版本发布
  * 在根目录 build.gradle

        allprojects {
            repositories {
              ...
              maven { url 'https://jitpack.io' }
            }
        }


  * 在app目录依赖

        dependencies {
            implementation 'com.github.TSsimeon:TimeLineView:x.y.z'
        }


### 功能
 * 基于android x RecyclerView.ItemDecoration
 * 统一设置item大小和图标
 * 单独设置每个item指示器大小
 * 单独设置每个item指示器图标
 * 全部用法可以查看app demo

### 效果
<img src="https://github.com/TSsimeon/TimeLineView/blob/master/img/img1.jpg" width="320" height="480"/><img src="https://github.com/TSsimeon/TimeLineView/blob/master/img/img2.jpg"  width="320" height="480"/>