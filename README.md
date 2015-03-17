关于拖动选择时间点的控件
使用时请声名 xmlns:step="http://schemas.android.com/apk/res/com.example.stepseek"
    <com.example.stepseek.view.StepSeekBar
        android:id="@+id/bar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerHorizontal="true"
        step:line_color="@color/bgcolor"
        step:line_height="5dp"
        step:line_width="300dp"
        step:move_point_color="@color/point_select"
        step:move_point_r="7dp"
        step:normal_point_color="@color/point_normal"
        step:normal_point_r="5dp"
        step:split="4"
        step:text_color="@color/text_color"
        step:text_marginTop="10dp"
        step:text_size="18sp"
        step:text_splitDes="不设置#10分钟#20分钟#30分钟#60分钟" />
