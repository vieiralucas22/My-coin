package com.example.mycoin.fragments.classes.allclasses;

import static android.view.LayoutInflater.from;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycoin.R;
import com.example.mycoin.databinding.ClassItemBinding;
import com.example.mycoin.fragments.classes.videoclasses.VideoPlayerFragmentArgs;
import com.example.mycoin.fragments.classes.videoclasses.VideoPlayerFragmentDirections;
import com.example.mycoin.gateway.repository.ClassRepository;
import com.example.mycoin.utils.LogcatUtil;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {
    private static final String TAG = LogcatUtil.getTag(ClassAdapter.class);

    private final ClassRepository mClassRepository;
    private List<ClassItem> mListClasses;
    private String mModule;
    private boolean mIsAllClassesFragment;
    private ClassesViewModel mClassesViewModel;

    public ClassAdapter(List<ClassItem> listClasses, ClassRepository classRepository,
                        String module, boolean isAllClasses, ClassesViewModel viewModel) {
        mListClasses = listClasses;
        mClassRepository = classRepository;
        mModule = module;
        mIsAllClassesFragment = isAllClasses;
        mClassesViewModel = viewModel;
    }

    public ClassAdapter(List<ClassItem> listClasses, ClassRepository classRepository,
                        String module, boolean isAllClasses) {
        mListClasses = listClasses;
        mClassRepository = classRepository;
        mModule = module;
        mIsAllClassesFragment = isAllClasses;
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
        holder.bind(mListClasses.get(position), position);
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

        public void bind(ClassItem classItem, int position) {
            mBinding.classTitle.setText(classItem.getTitle());
            mBinding.textSubtitle.setText(classItem.getDescription());
            mBinding.checkboxClassDone.setChecked(classItem.isDone());

            if (mIsAllClassesFragment) {
                mBinding.getRoot().setOnClickListener(view -> {
                    NavDirections action = ClassesFragmentDirections.
                            actionClassesFragmentToVideoPlayerFragment()
                            .setClassTitle(classItem.getTitle())
                            .setModule(mModule);

                    Navigation.findNavController(view).navigate(action);
                });

                mBinding.checkboxClassDone.setVisibility(View.VISIBLE);

                mBinding.checkboxClassDone.setOnClickListener(view -> {
                    mClassRepository.updateClassState(position,
                            mBinding.checkboxClassDone.isChecked(), mModule);
                    mClassesViewModel.updatePercentage(mModule);
                });
            } else {
                mBinding.getRoot().setOnClickListener(view -> {

                    NavDirections action = VideoPlayerFragmentDirections
                            .actionVideoPlayerFragmentSelf()
                            .setClassTitle(classItem.getTitle())
                            .setModule(mModule);

                    Navigation.findNavController(view).navigate(action);
                });
            }
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

        @Override
        public String toString() {
            return "ClassItem{" +
                    "mTitle='" + mTitle + '\'' +
                    ", mDescription='" + mDescription + '\'' +
                    ", mIsDone=" + mIsDone +
                    '}';
        }
    }
}
