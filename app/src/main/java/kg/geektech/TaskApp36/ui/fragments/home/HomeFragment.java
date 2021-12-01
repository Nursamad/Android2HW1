package kg.geektech.TaskApp36.ui.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import kg.App;

import com.geektech.taskapp36.R;
import com.geektech.taskapp36.databinding.FragmentHomeBinding;

import java.util.ArrayList;

import kg.geektech.TaskApp36.interfaces.OnItemClickListener;
import kg.geektech.TaskApp36.models.Task;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private boolean isChanged = false;
    private TaskAdapter adapter;
    private int pos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter();
        adapter.addItems(App.getInstance().getDataBase().taskDao().getAll());
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sort) {
            adapter.setList((ArrayList<Task>) App.getInstance().getDataBase().taskDao().getAllSortedByTitle());
            binding.homeRecycler.setAdapter(adapter);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
            public void onLongClick(Task task, int position) {
                App.getInstance().getDataBase().taskDao().delete(adapter.getItem(position));
                adapter.removeItem(position);

            }
        });

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
//                    App.getInstance().getDataBase().taskDao().update(task);
                } else {
                    // добавление нового элемента
                    adapter.addItem(task);
//                    App.getInstance().getDataBase().taskDao().insert(task);

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


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.option_menu, menu);
    }


    private void close() {     //Выход из TaskFragment

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();

    }
}