package com.geektech.taskapp36.ui.fragments.taskFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.App;
import com.geektech.taskapp36.R;
import com.geektech.taskapp36.models.Task;

import java.util.List;

public class TaskFragment extends Fragment {

    private EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.taskEditText);

        // для редактирования  , получаем данные по ключю из HomeFragment
        if (getArguments() != null) {
            editText.setText(getArguments().getString("title"));
        }

        view.findViewById(R.id.taskButton).setOnClickListener(v -> {
            save();
            close();
        });
    }


    private void save() {
        String text = editText.getText().toString().trim();
        Task task = new Task(text, System.currentTimeMillis());



        Bundle bundle = new Bundle();
        bundle.putSerializable("task", task);
        getParentFragmentManager().setFragmentResult("rk_task", bundle);
    }

    private void close() {     //Выход из TaskFragment

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();

    }
}