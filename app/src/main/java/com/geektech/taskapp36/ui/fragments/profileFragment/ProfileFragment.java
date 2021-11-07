package com.geektech.taskapp36.ui.fragments.profileFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geektech.taskapp36.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {
    private final int RESULT_GALLERY = 1;
    private FragmentProfileBinding binding;
    private ActivityResultLauncher<String> getImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getImage = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            binding.profileImage.setImageURI(result);    //Замена ActivityResult
        });
        binding.profileImage.setOnClickListener(v -> getImage.launch("image/*"));
    }
}