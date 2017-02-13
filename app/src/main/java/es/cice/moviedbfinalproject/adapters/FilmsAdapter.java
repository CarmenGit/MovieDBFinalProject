package es.cice.moviedbfinalproject.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import es.cice.moviedbfinalproject.R;

/**
 * Created by cice on 13/2/17.
 */

public class FilmsAdapter extends ArrayAdapter<String> {
    private static final String TAG="ArrayAdapter";
        private Context ctx;
        private List<String> data;

        public FilmsAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            ctx=context;
            data=objects;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater=LayoutInflater.from(ctx);
            View row=inflater.inflate(R.layout.film_row,null);
            //ImageView carIV= (ImageView) row.findViewById(R.id.carThumbIV);
            TextView tituloTV= (TextView) row.findViewById(R.id.tituloTV);
            //carIV.setImageResource(data.get(position).getMiniatura());
            tituloTV.setText(data.get(position)) ;
            Log.d(TAG, data.get(position));
            return row;
        }
    }


