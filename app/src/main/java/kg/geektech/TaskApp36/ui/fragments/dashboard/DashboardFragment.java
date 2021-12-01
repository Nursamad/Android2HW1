package kg.geektech.TaskApp36.ui.fragments.dashboard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.geektech.taskapp36.databinding.FragmentDashboardBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

import kg.geektech.TaskApp36.interfaces.OnItemClickListener;
import kg.geektech.TaskApp36.models.Task;
import kg.geektech.TaskApp36.ui.fragments.home.TaskAdapter;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    private TaskAdapter adapter;

    private BroadcastReceiver broadcastReceiver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        registerNetworkBroadcastReceiver();

        adapter = new TaskAdapter();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void onLongClick(Task task, int position) {
                FirebaseFirestore.getInstance().collection("titles").document(task.getDocId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                        adapter.deleteItem(position);
                        Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.dashRecycler.setAdapter(adapter);
        getData();
        getBroadCastR();
    }

    private void getData() {
        FirebaseFirestore.getInstance().collection("titles").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshots) {

                List<Task> list = new ArrayList<>();
                for (DocumentSnapshot snapshot : snapshots) {
                    Task task = snapshot.toObject(Task.class);
                    task.setDocId(snapshot.getId());
                    list.add(task);
                }
//                List<Task> list = snapshots.toObjects(Task.class);
                adapter.addItems(list);
            }
        });
    }

    //    private void registerNetworkBroadcastReceiver() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            getActivity().registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
//
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            getActivity().registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
//
//        }
//    }
    private void getBroadCastR() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    if (isOnline(context)) {
                        Toast.makeText(context, "Network Connected", Toast.LENGTH_SHORT).show();
                        dialog(true);
                    } else {
                        Toast.makeText(context, "No Network Connection", Toast.LENGTH_SHORT).show();
                        dialog(false);
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        };
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private void unregisterNetwork() {
        try {
            getActivity().unregisterReceiver(broadcastReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void dialog(boolean s) {
        if (s) {
            binding.tvInet.setVisibility(View.GONE);
        } else {
            binding.dashRecycler.setVisibility(View.GONE);
            binding.tvInet.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregisterNetwork();
        binding = null;
    }

}