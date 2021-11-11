package com.geektech.taskapp36.ui.fragments.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.taskapp36.R;
import com.geektech.taskapp36.databinding.FragmentHomeBinding;
import com.geektech.taskapp36.databinding.ItemBoardBinding;
import com.geektech.taskapp36.databinding.ItemListBinding;
import com.geektech.taskapp36.interfaces.OnItemClickListener;
import com.geektech.taskapp36.models.Task;

import java.util.ArrayList;
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

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(Task task) {
        list.add(task);
        notifyItemInserted(list.indexOf(task));
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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
        list.addAll(tasks);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull ItemListBinding itemView) {
            super(itemView.getRoot());

            binding.itemTv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onLongClick(getAdapterPosition());
                    return true;
                }
            });
        }

        public void onBind(Task task) {
            binding.itemTv.setText(task.getText());

            binding.itemTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(getAdapterPosition());
                }
            });
        }
    }
}
