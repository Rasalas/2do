package rasalas.de.twodo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import rasalas.de.twodo.R;

/**
 * Created by Torben on 12.06.2016.
 */
public class NTaskListAdapter extends ArrayAdapter<String>{


    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;
    public NTaskListAdapter(Activity context,
                      String[] web, Integer[] imageId) {
        super(context, R.layout.item_new_task, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.item_new_task, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.item_newTask_primText);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.item_newTask_imageView);
        txtTitle.setText(web[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}

