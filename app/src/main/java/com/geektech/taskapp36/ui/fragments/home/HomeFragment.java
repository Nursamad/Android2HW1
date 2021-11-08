package com.geektech.taskapp36.ui.fragments.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.geektech.taskapp36.R;
import com.geektech.taskapp36.databinding.FragmentHomeBinding;
import com.geektech.taskapp36.interfaces.OnItemClickListener;
import com.geektech.taskapp36.models.Task;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private boolean isChanged = false;
    private TaskAdapter adapter;
    private int pos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {


                //обновление элемента
                isChanged = true;

                pos = position;
                Task task = adapter.getItem(position);
                openFragment(task.getText());
            }

            @Override
            public void onLongClick(int position) {
                adapter.removeItem(position);
            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.homeFab.setOnClickListener(v -> {
            openFragment(null);

            // добавление нового элемента
            isChanged = false;

        });
        getParentFragmentManager().setFragmentResultListener("rk_task", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Task task = (Task) result.getSerializable("task");

                //обновление элемента
                if (isChanged) {
                    adapter.updateItem(task, pos);
                } else {
                    // добавление нового элемента
                    adapter.addItem(task);
                }
            }
        });
        initList();
    }

    private void initList() {
        binding.homeRecycler.setAdapter(adapter);
    }


    private void openFragment(String title) {

        // передаем данные по ключю в TaskFragment
        Bundle bundle = new Bundle();
        bundle.putString("title", title);

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.taskFragment, bundle);
    }
}