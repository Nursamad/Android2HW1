package kg.geektech.TaskApp36.ui.fragments.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.taskapp36.R;
import com.geektech.taskapp36.databinding.ItemListBinding;
import kg.geektech.TaskApp36.interfaces.OnItemClickListener;
import kg.geektech.TaskApp36.models.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private ArrayList<Task> list;
    private OnItemClickListener onItemClickListener;
    private ItemListBinding binding;

    public TaskAdapter() {
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(list.get(position));

//зебра recycler view
        if (position % 2 == 0){
            binding.itemTv.setBackgroundColor(binding.getRoot().getResources().getColor(R.color.orange, null));
        } else {
            binding.itemTv.setBackgroundColor(binding.getRoot().getResources().getColor(R.color.blue, null));
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setList(ArrayList<Task> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void addItem(Task task) {
        list.add(0,task);
        notifyItemInserted(list.indexOf(task));
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    //получить элемент из списка по позиции
    public Task getItem(int position){
        return list.get(position);
    }
    //обновить данные элемента по позиции

    public void updateItem(Task task, int pos) {
        list.set(pos , task);
        notifyItemChanged(pos);
    }
    public void addItems(List<Task> tasks) {
        list.clear();
        list.addAll(tasks);
        notifyDataSetChanged();
    }

    public void fromAToZ() {
        Collections.sort(list, (t1, t2) -> t1.getText().compareTo(t2.getText()));
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void deleteItem(int position) {
        this.list.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull ItemListBinding itemView) {
            super(itemView.getRoot());


        }

        public void onBind(Task task) {
            binding.itemTv.setText(task.getText());

            binding.itemTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(getAdapterPosition());
                }
            });

            binding.itemTv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onLongClick(task,getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
