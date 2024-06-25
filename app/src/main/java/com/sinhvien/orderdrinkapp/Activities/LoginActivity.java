package com.sinhvien.orderdrinkapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.sinhvien.orderdrinkapp.DAO.NhanVienDAO;
import com.sinhvien.orderdrinkapp.R;

public class LoginActivity extends AppCompatActivity {
    Button BTN_login_DangNhap, BTN_login_DangKy;
    TextInputLayout TXTL_login_TenDN, TXTL_login_MatKhau;
    NhanVienDAO nhanVienDAO;

    @Override

//liên kết các thành phần được định nghĩa trong
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);//liên kết các thành phần được định nghĩa trong login_layout

        //thuộc tính view
        TXTL_login_TenDN = (TextInputLayout)findViewById(R.id.txtl_login_TenDN);
        TXTL_login_MatKhau = (TextInputLayout)findViewById(R.id.txtl_login_MatKhau);
        BTN_login_DangNhap = (Button)findViewById(R.id.btn_login_DangNhap);
        BTN_login_DangKy = (Button)findViewById(R.id.btn_login_DangKy);

//Khởi tạo đối tượng NhanVienDAO để truy cập database nhân viên.
        nhanVienDAO = new NhanVienDAO(this);//khởi tạo kết nối csdl

//Thiết lập sự kiện click cho nút BTN_login_DangNhap.
        BTN_login_DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//Gọi các hàm validateUserName() và validatePassWord() để kiểm tra tính hợp lệ của tên đăng nhập và mật khẩu (không được để trống).
                if(!validateUserName() | !validatePassWord()){
                    return;
                }

                String tenDN = TXTL_login_TenDN.getEditText().getText().toString();
                String matKhau = TXTL_login_MatKhau.getEditText().getText().toString();

                int ktra = nhanVienDAO.KiemTraDN(tenDN,matKhau);//kiểm tra thông tin đăng nhập trong database nhân viên.

                int maquyen = nhanVienDAO.LayQuyenNV(ktra);
                if(ktra != 0)//Nếu kiểm tra thành công (trả về id nhân viên), thực hiện các bước sau:
                {
                    // lưu mã quyền vào shareprefer
                    SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putInt("maquyen",maquyen);
                    editor.commit();

//gửi dữ liệu user qua trang chủ (HomeActivity).
                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);

//Put extra data vào Intent để truyền tên đăng nhập và id nhân viên sang trang chủ.
                    intent.putExtra("tendn",TXTL_login_TenDN.getEditText().getText().toString());
                    intent.putExtra("manv",ktra);

                    startActivity(intent);//chuyển sang trang chủ.
                }else//Nếu kiểm tra thất bại (trả về 0), hiển thị thông báo "Đăng nhập thất bại!" bằng Toast.
                {
                    Toast.makeText(getApplicationContext(),"Đăng nhập thất bại!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //Hàm quay lại màn hình chính
    public void backFromLogin(View view)//Xử lý sự kiện click quay lại trang chào (WelcomeActivity)
    {
//Khởi tạo một đối tượng Intent mới với mục tiêu là WelcomeActivity.
        Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);

        //tạo animation cho thành phần
        Pair[] pairs = new Pair[1];//Khởi tạo một mảng Pair để lưu trữ các View tham gia hiệu ứng chuyển cảnh.

//transition_login là tên tùy chỉnh cho hiệu ứng chuyển cảnh này.
        pairs[0] = new Pair<View, String>(findViewById(R.id.layoutLogin),"transition_login");

//Kiểm tra xem phiên bản Android có lớn hơn hoặc bằng LOLLIPOP (Android 5.0) không.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);

            startActivity(intent,options.toBundle());//Bắt đầu Activity mới (WelcomeActivity) với hiệu ứng chuyển cảnh được thiết lập trong options.
        }
        else {
            startActivity(intent);//Bắt đầu Activity mới (WelcomeActivity) mà không có hiệu ứng chuyển cảnh.
        }
    }

    //Hàm chuyển qua trang đăng ký
    public void callRegisterFromLogin(View view)//Xử lý sự kiện click chuyển sang trang đăng ký (RegisterActivity) với hiệu ứng chuyển đổi trang.
    {
        Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    //validateUserName();validatePassWord() :Hàm kiểm tra tính hợp lệ của tên đăng nhập và mật khẩu (bắt buộc nhập, không được để trống).
    private boolean validateUserName()
    {
//phương thức getText() để lấy nội dung người dùng nhập, .toSring(): chuyển nội dung thành chuỗi
//Sử dụng .trim() để loại bỏ khoảng trắng đầu và cuối chuỗi.
        String val = TXTL_login_TenDN.getEditText().getText().toString().trim();

        if(val.isEmpty())//Kiểm tra xem chuỗi val (tên đăng nhập) có rỗng hay không (isEmpty()).
            //true (nghĩa là tên đăng nhập trống).
        {
            //Lấy chuỗi thông báo lỗi từ resource string (not_empty)
            TXTL_login_TenDN.setError(getResources().getString(R.string.not_empty));

            return false;//Báo hiệu tên đăng nhập không hợp lệ

        }else //false (nghĩa là tên đăng nhập không trống).
        {
            ////Xóa thông báo lỗi
            TXTL_login_TenDN.setError(null); //Thiết lập thông báo lỗi thành null

            TXTL_login_TenDN.setErrorEnabled(false);//Vô hiệu hóa hiển thị thông báo lỗi (đề phòng trường hợp lỗi trước đó chưa được xử lý).

            return true;//Báo hiệu tên đăng nhập hợp lệ
        }
    }

    private boolean validatePassWord()
    {
        String val = TXTL_login_MatKhau.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_login_MatKhau.setError(getResources().getString(R.string.not_empty));
            return false;
        }else{
            TXTL_login_MatKhau.setError(null);
            TXTL_login_MatKhau.setErrorEnabled(false);
            return true;
        }
    }
    //endregion
}