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

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.geektech.taskapp36.Prefs;
import com.geektech.taskapp36.R;
import com.geektech.taskapp36.databinding.FragmentProfileBinding;

import java.nio.charset.StandardCharsets;


public class ProfileFragment extends Fragment {
    private final int RESULT_GALLERY = 1;
    private FragmentProfileBinding binding;
    private ActivityResultLauncher<String> getImage;

    private Prefs prefs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = new Prefs(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.profileEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
            prefs.saveEdt(binding.profileEditText.getText().toString());  //добавить текст в dit text
            }
        });

        getImage = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            prefs.saveImage(result.toString());//приведение uri в стринг
            //Замена ActivityResult
        });
        binding.profileImage.setOnClickListener(v -> getImage.launch("image/*"));

        binding.profileImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                prefs.deleteImage();
                binding.profileImage.setImageResource(R.drawable.ic_android);
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();  //в prefs сохраняется view  и здесь уже отображает
        Glide.with(requireContext()).load(prefs.getImage()).circleCrop().placeholder(R.drawable.ic_android).into(binding.profileImage); //показ и округление фотки

        if (prefs.getEdt() != null) {
            binding.profileEditText.setText(prefs.getEdt());
        }
    }
}