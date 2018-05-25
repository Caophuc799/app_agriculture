package vn.edu.hcmut.agricultureapp.HomeActivity;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vn.edu.hcmut.agricultureapp.R;


public class WeatherAdapter extends ArrayAdapter<ThoiTiet> {

    @NonNull
    Context context;//màn hình sử dụng layout này
    @LayoutRes
    int resource;//layout cho từng dòng muốn hiển thị
    @NonNull
    List<ThoiTiet> objects;//danh sách nguồn dữ liệu muốn hiển thị lên giao diện


    public WeatherAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ThoiTiet> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(this.resource,null);// đối số 1 là màn hình item(xml) truyền vào

        TextView txtDate7= row.findViewById(R.id.txtDate7);
        TextView txtStatus7= row.findViewById(R.id.txtStatus7);
        TextView txtMaxTemp= row.findViewById(R.id.txtMaxTemp);
        TextView txtMinTemp= row.findViewById(R.id.txtMinTemp);
        ImageView imgIcon7=row.findViewById(R.id.imgIcon7);

        ThoiTiet thoiTiet=objects.get(position);
        txtDate7.setText(thoiTiet.Day);
        txtMaxTemp.setText(thoiTiet.MaxTemp+"ºC");
        txtMinTemp.setText(thoiTiet.MinTemp+"ºC");
        txtStatus7.setText(thoiTiet.Status);
        String link= "http://openweathermap.org/img/w/"+thoiTiet.Image+".png";

        Picasso.with(context).load(link).into(imgIcon7);


        return row;
    }


}
