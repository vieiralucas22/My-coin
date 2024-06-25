package com.example.mycoin.fragments.classes.allclasses;

import static android.view.LayoutInflater.from;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycoin.R;
import com.example.mycoin.constants.Constants;
import com.example.mycoin.databinding.ClassItemBinding;
import com.example.mycoin.utils.LogcatUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {
    private static final String TAG = LogcatUtil.getTag(ClassAdapter.class);

    private List<ClassItem> mListClasses;

    public ClassAdapter(List<ClassItem> listClasses) {
        mListClasses = listClasses;
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Definir o layout de cada item
        View view = from(parent.getContext())
                .inflate(R.layout.class_item, parent, false);
        //Criar uma viewHolder
        return new ClassViewHolder(ClassItemBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        holder.bind(mListClasses.get(position), position, mListClasses.size());
    }

    @Override
    public int getItemCount() {
        return mListClasses.size();
    }

    public void setItems(List<ClassItem> list) {
        mListClasses = list;
        notifyDataSetChanged();
    }

    public class ClassViewHolder extends RecyclerView.ViewHolder {
        private ClassItemBinding mBinding;

        public ClassViewHolder(ClassItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(ClassItem classItem, int position, int size) {
            mBinding.classTitle.setText(classItem.getTitle());
            mBinding.textSubtitle.setText(classItem.getDescription());
            mBinding.checkboxClassDone.setChecked(classItem.isDone());

            mBinding.getRoot().setOnClickListener(view -> {
                Navigation.findNavController(view)
                        .navigate(R.id.action_classesFragment_to_introductionVideoPlayerFragment);
            });

            mBinding.checkboxClassDone.setOnClickListener(view -> {
            });
        }
    }

    public static class ClassItem {
        private String mTitle;
        private String mDescription;
        private boolean mIsDone;

        public ClassItem() {
        }

        public ClassItem(String mTitle, String mDescription, boolean mIsDone) {
            this.mTitle = mTitle;
            this.mDescription = mDescription;
            this.mIsDone = mIsDone;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String mTitle) {
            this.mTitle = mTitle;
        }

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String mDescription) {
            this.mDescription = mDescription;
        }

        public boolean isDone() {
            return mIsDone;
        }

        public void setIsDone(boolean mIsDone) {
            this.mIsDone = mIsDone;
        }
    }
}
