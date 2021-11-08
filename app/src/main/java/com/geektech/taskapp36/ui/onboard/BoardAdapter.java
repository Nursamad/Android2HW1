package com.geektech.taskapp36.ui.onboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.taskapp36.R;
import com.geektech.taskapp36.databinding.ItemBoardBinding;


public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {
    private ItemBoardBinding binding;
    private final String[] titles = new String[]{"Welcome!", "Our teacher", "Geektech"};
    private final String[] description = new String[]{"Android 2", "Daniyar", "IT school"};
    private final int[] img = new int[]{R.raw.lottie, R.raw.lottie2, R.raw.lottie3};


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemBoardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
/*
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);
        return new ViewHolder(view);
*/
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(position);
        if (position == 2) {
            binding.boardBtn.setVisibility(View.VISIBLE);
        } else {
            binding.boardBtn.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        /*
                private TextView textTitle;
                private TextView desc;
                private ImageView imgs;
                private Button btn;
        */
        public ViewHolder(@NonNull ItemBoardBinding itemView) {
            super(itemView.getRoot());
/*
            textTitle = itemView.findViewById(R.id.itemBoardTv);
            desc = itemView.findViewById(R.id.itemBoardDes);
            imgs = itemView.findViewById(R.id.itemBoardIv);
            btn = itemView.findViewById(R.id.boardBtn);
*/

            binding.boardBtn.setOnClickListener(v -> {
                Navigation.findNavController(itemView.getRoot()).navigate(R.id.navigation_home);
            });
        }

        public void onBind(int position) {
            binding.itemBoardIv.setAnimation(img[position]);
            binding.itemBoardTv.setText(titles[position]);
            binding.itemBoardDes.setText(description[position]);
        }
    }
}
