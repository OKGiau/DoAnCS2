package com.sinhvien.orderdrinkapp.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sinhvien.orderdrinkapp.DAO.BanAnDAO;
import com.sinhvien.orderdrinkapp.DAO.NhanVienDAO;
import com.sinhvien.orderdrinkapp.DTO.DonDatDTO;
import com.sinhvien.orderdrinkapp.DTO.LoaiMonDTO;
import com.sinhvien.orderdrinkapp.DTO.NhanVienDTO;
import com.sinhvien.orderdrinkapp.R;

import org.w3c.dom.Text;

import java.util.List;

//Lớp: AdaptorRecycleViewStatistic mở rộng RecyclerView.Adapter, cung cấp dữ liệu và quản lý chế độ xem.
public class AdapterRecycleViewStatistic extends RecyclerView.Adapter<AdapterRecycleViewStatistic.ViewHolder>{

    Context context;
    int layout; //tệp tài nguyên bố cục
    List<DonDatDTO> donDatDTOList; //Danh sách DonDatDTO các đối tượng chứa dữ liệu cho từng thống kê đơn
    NhanVienDAO nhanVienDAO;
    BanAnDAO banAnDAO;

    //AdapterRecycleViewStatistic: khởi tạo bộ điều hợp với thông tin cần thiết để đưa vào chế độ xem tái chế dữ liệu thống kê đơn hàng
    //gọi khi tạo một phiên bản mới của lớp bộ điều hợp này.
    public AdapterRecycleViewStatistic(Context context, int layout, List<DonDatDTO> donDatDTOList){

        this.context =context; //Lưu trữ ngữ cảnh được truyền cho hàm tạo.
        this.layout = layout; //Lưu trữ ID tài nguyên bố cục.
        this.donDatDTOList = donDatDTOList; //Lưu trữ tham chiếu đến danh sách các DonDatDTOđối tượng
        nhanVienDAO = new NhanVienDAO(context);
        banAnDAO = new BanAnDAO(context);
    }


    @Override
    //onCreateViewHolder tạo các đối tượng ViewHolder
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRecycleViewStatistic.ViewHolder holder, int position) {
        DonDatDTO donDatDTO = donDatDTOList.get(position);
        holder.txt_customstatistic_MaDon.setText("Mã đơn: "+donDatDTO.getMaDonDat());
        holder.txt_customstatistic_NgayDat.setText(donDatDTO.getNgayDat());
        if(donDatDTO.getTongTien().equals("0"))
        {
            holder.txt_customstatistic_TongTien.setVisibility(View.INVISIBLE);
        }else {
            holder.txt_customstatistic_TongTien.setVisibility(View.VISIBLE);
        }

        if (donDatDTO.getTinhTrang().equals("true"))
        {
            holder.txt_customstatistic_TrangThai.setText("Đã thanh toán");
        }else {
            holder.txt_customstatistic_TrangThai.setText("Chưa thanh toán");
        }
        NhanVienDTO nhanVienDTO = nhanVienDAO.LayNVTheoMa(donDatDTO.getMaNV());
        holder.txt_customstatistic_TenNV.setText(nhanVienDTO.getHOTENNV());
        holder.txt_customstatistic_BanDat.setText(banAnDAO.LayTenBanTheoMa(donDatDTO.getMaBan()));
    }

    @Override
    public int getItemCount() {
        return donDatDTOList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_customstatistic_MaDon, txt_customstatistic_NgayDat, txt_customstatistic_TenNV,
                txt_customstatistic_BanDat, txt_customstatistic_TongTien,txt_customstatistic_TrangThai;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txt_customstatistic_MaDon = itemView.findViewById(R.id.txt_customstatistic_MaDon);
            txt_customstatistic_NgayDat = itemView.findViewById(R.id.txt_customstatistic_NgayDat);
            txt_customstatistic_TenNV = itemView.findViewById(R.id.txt_customstatistic_TenNV);
            txt_customstatistic_BanDat = itemView.findViewById(R.id.txt_customstatistic_BanDat);
            txt_customstatistic_TongTien = itemView.findViewById(R.id.txt_customstatistic_TongTien);
            txt_customstatistic_TrangThai = itemView.findViewById(R.id.txt_customstatistic_TrangThai);
        }
    }
}
