package kg.geektech.TaskApp36.interfaces;

import kg.geektech.TaskApp36.models.Task;

public interface OnItemClickListener {
    void onClick(int position);
    void  onLongClick(Task task, int position);
}
