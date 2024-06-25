package com.sinhvien.orderdrinkapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinhvien.orderdrinkapp.R;

public class SplashActivity extends AppCompatActivity { //AppCompatActivity là lớp Cha

    private static int SPLASH_TIMER = 3000; //lưu trữ thời gian hiển thị màn hình Splash 3 giây
    @Override

    //protected cho phép các lớp con truy cập
    //onCreate(Bundle savedInstanceState): Phương thức này nhận một đối tượng Bundle chứa trạng thái đã lưu của hoạt động (nếu có).
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        //tạo đối tượng view
        //tìm kiếm các view trong layout theo id
        ImageView IMGLogo = (ImageView)findViewById(R.id.imgLogo);
        TextView TXTCoffeeshop = (TextView)findViewById(R.id.txtCoffeeshop);
        TextView TXTPowered = (TextView)findViewById(R.id.txtPowered);

        //lấy đối tượng animation
        //AnimationUtils để tải các hiệu ứng hoạt hình từ tài nguyên.
        Animation sideAnim = AnimationUtils.loadAnimation(this,R.anim.side_anim);
        Animation bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        //thiết lập animation cho component
        IMGLogo.setAnimation(sideAnim);
        TXTCoffeeshop.setAnimation(sideAnim);
        TXTPowered.setAnimation(bottomAnim);

        //new Handler().postDelayed(new Runnable() { ... },SPLASH_TIMER);
        // để xử lý việc di chuyển sang màn hình khác sau một khoảng thời gian nhất định.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent là một đối tượng dùng để truyền thông báo giữa các Activity (màn hình) trong ứng dụng Android.
                Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);//sau khi chạy hiệu ứng trong 3 giây thì chuyển sang màn hình Welcome
                startActivity(intent);

                //R.anim.fade_in: sử dụng cho hiệu ứng mờ dần khi xuất hiện (fade in).
                //R.anim.fade_out: sử dụng cho hiệu ứng mờ dần khi biến mất (fade out).
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);//Dòng này thiết lập hiệu ứng chuyển đổi giữa các Activity.
                finish(); //Phương thức này hủy Activity và giải phóng các tài nguyên của nó. Điều này ngăn chặn người dùng quay lại màn hình splash screen bằng nút back.
            }
        },SPLASH_TIMER);
    }
}