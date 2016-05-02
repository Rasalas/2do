package rasalas.de.twodo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import rasalas.de.twodo.R;
import rasalas.de.twodo.model.MenuItem;

/**
 * Created by Rasalas on 02.05.2016.
 */
public class NewTaskAdapter extends RecyclerView.Adapter<NewTaskAdapter.ViewHolder> {
    private List<MenuItem> items;

    public NewTaskAdapter(List<MenuItem> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View todoView = inflater.inflate(R.layout.item_content_main, parent, false);

        return new ViewHolder(todoView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MenuItem item = items.get(position);

        ImageView iV = holder.imageView;
        TextView tvPrim = holder.tvPrim;
        TextView tvSec = holder.tvSec;

        iV.setImageResource(item.getImageId());
        tvPrim.setText(item.getPrimText());
        tvSec.setText(item.getSecText());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView tvPrim;
        public TextView tvSec;

        public ViewHolder(View itemView) {
            super(itemView);

            //imageView = (ImageView) itemView.findViewById(R.id.item_newTask_imageView);
            tvPrim = (TextView) itemView.findViewById(R.id.item_newTask_primText);
            tvSec = (TextView) itemView.findViewById(R.id.item_newTask_secText);

        }
    }
}
