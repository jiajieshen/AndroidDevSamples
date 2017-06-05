# NavigationBar
```
# style
    <style name="AppTheme.TranslucentNavigation" parent="AppTheme">
        <item name="android:navigationBarColor">@android:color/transparent</item>
        <item name="android:windowTranslucentNavigation">true</item>
        <item name="android:windowDrawsSystemBarBackgrounds">true</item>
    </style>
```

＃ Toolbar
1. Toolbar 的 elevation 属性没有阴影效果 －－ 外部套布局 AppBarLayout
```
# eg.
<android.support.design.widget.AppBarLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
   <android.support.v7.widget.Toolbar
            android:layout_height="?attr/actionBarSize"
            app:elevation="6dp"
            android:layout_width="match_parent"/>
</android.support.design.widget.AppBarLayout>
```

＃ BottomNavigationView
1. elevation 没效果
2. Snackbar 不能 over BottomNavigationView
3. 与虚拟按键栏交互
