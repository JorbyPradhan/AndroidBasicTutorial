package com.example.jb.demoreg;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends ArrayAdapter {
    private Context context;
    private List<User> listData;
    private int resource;
    public ListAdapter(Context context, int resource, List<User> listData) {
        super(context, resource, listData);
        this.context = context;
        this.listData = listData;
        this.resource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final ViewHolder holder;
        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(resource,null);
            holder = new ViewHolder();
            holder.name = row.findViewById(R.id.nameda);
            holder.imageView = row.findViewById(R.id.imgda);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder)row.getTag();
        }
        final User user = listData.get(position);
        holder.name.setText(user.getName());
        byte[] image =user.getImg();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image,0, image.length);
        holder.imageView.setImageBitmap(bitmap);
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Main2Activity.class);
                intent.putExtra("Name",holder.name.getText().toString());
                v.getContext().startActivity(intent);
            }
        });

        return row;
    }
    private class ViewHolder{
        ImageView imageView;
        TextView name;
    }
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    /*@Override
    public Object getItem(int position) {
        return listData.get(position);
    }*/

    @Override
    public long getItemId(int position) {
        return position;
    }
}
