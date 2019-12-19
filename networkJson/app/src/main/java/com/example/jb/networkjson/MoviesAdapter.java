package com.example.jb.networkjson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MoviesAdapter extends ArrayAdapter {
    private Context context;
    private List<MovieList> movieLists;
    private int resource;
    public MoviesAdapter(Context context, int resource, List<MovieList> movieLists) {
        super(context, resource, movieLists);
        this.context = context;
        this.movieLists = movieLists;
        this.resource = resource;
    }
class ViewHolder{
        TextView TextTitle;
        ImageView imgv;
}

    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        View row = convertView;
        final ViewHolder holder;
        if (row == null){
        LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = (View) inflater.inflate(resource,null);
        holder=new ViewHolder();
        holder.TextTitle=row.findViewById(R.id.movie_title);
        holder.imgv=row.findViewById(R.id.movie_poster);
        row.setTag(holder);

        }
        else {
            holder= (ViewHolder) row.getTag();
        }
        MovieList list = movieLists.get(position);
       // View view = LayoutInflater.from(context).inflate(resource,parent,false);
//        TextView movieTitle = view.findViewById(R.id.movie_title);
//        ImageView imageView = view.findViewById(R.id.movie_poster);
        holder.TextTitle.setText(list.getTitle());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+ list.getPoster_path()).into(holder.imgv);
        return row;
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }
}
