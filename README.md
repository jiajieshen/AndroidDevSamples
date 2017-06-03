＃ Toolbar
1. toolbar 需要嵌套在 AppBarLayout 里面才会显示 elevation 的效果
```
<android.support.design.widget.AppBarLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
   <android.support.v7.widget.Toolbar
            android:layout_height="?attr/actionBarSize"
            app:elevation="6dp"
            android:layout_width="match_parent"/>
</android.support.design.widget.AppBarLayout>
```
＃ BottomNavgationView
1. elevation 没效果
2. 与 Snackbar 交互不符合规范
