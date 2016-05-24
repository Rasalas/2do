package rasalas.de.twodo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rasalas.de.twodo.R;
import rasalas.de.twodo.model.Todo;

/**
 * Created by Rasalas on 02.05.2016.
 */
public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<Todo> todos;


    public TodoAdapter(List<Todo> todos) {
        this.todos = todos;
    }

    public void updateTodos(List<Todo> todos) {
        this.todos = todos;
        notifyDataSetChanged();
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
        Todo todo = todos.get(position);

        TextView tvPrim = holder.tvTask;
        TextView tvSec = holder.tvDesciption;

        tvPrim.setText(todo.getTag());
        tvSec.setText(todo.getDescription());
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTask;
        public TextView tvDesciption;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTask = (TextView) itemView.findViewById(R.id.item_main_primText);
            tvDesciption = (TextView) itemView.findViewById(R.id.item_main_secText);
        }
    }
}
