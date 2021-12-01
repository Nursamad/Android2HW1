package kg.geektech.TaskApp36.ui.fragments.taskFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.geektech.taskapp36.R;
import com.geektech.taskapp36.databinding.FragmentTaskBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import kg.App;
import kg.geektech.TaskApp36.models.Task;

public class TaskFragment extends Fragment {

    private EditText editText;
    private FragmentTaskBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.taskEditText);
        hideProgress();

        if (getArguments() != null) {
            binding.taskEditText.setText(getArguments().getString("title"));
        }
        binding.taskButton.setOnClickListener(v -> {
            showProgress();
            save();

        });
    }


    private void save() {
        binding.taskButton.setVisibility(View.GONE);

        String text = binding.taskEditText.getText().toString().trim();
        Task task = new Task(text, System.currentTimeMillis());

        if (task == null) {
            saveToFirestore(task);
            App.getInstance().getDataBase().taskDao().insert(task);
        } else {
            App.getInstance().getDataBase().taskDao().update(task);
            updateToFireStore(task);
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable("task", task);
        getParentFragmentManager().setFragmentResult("rk_task", bundle);
    }

    private  void updateToFireStore(Task task){
        FirebaseFirestore.getInstance().collection("titles").document().update("text" ,  task.getText()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                close();
            }
        });
    }


    private void saveToFirestore(Task task) {

        FirebaseFirestore.getInstance().collection("titles").add(task).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {

            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentReference> t) {
                if (t.isSuccessful()) {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                    hideProgress();
                    close();
                } else {
                    Log.e("error", "onComplete: " + t.getException().getLocalizedMessage());
                }
            }
        });
    }

    private void showProgress() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.taskButton.setVisibility(View.GONE);
    }

    private void hideProgress() {
        binding.progressBar.setVisibility(View.GONE);
        binding.taskButton.setVisibility(View.VISIBLE);
    }


    private void close() {     //Выход из TaskFragment

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();

    }
}