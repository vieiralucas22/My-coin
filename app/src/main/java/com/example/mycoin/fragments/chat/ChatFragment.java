package com.example.mycoin.fragments.chat;

import static com.example.mycoin.fragments.chat.MessageAdapter.Message.SENT_BY_BOT;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycoin.R;
import com.example.mycoin.databinding.FragmentChatBinding;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.utils.LogcatUtil;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = LogcatUtil.getTag(ChatFragment.class);

    private FragmentChatBinding mBinding;

    private ChatViewModel mViewModel;
    private List<MessageAdapter.Message> messageList;
    private MessageAdapter mMessageAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentChatBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = getViewModel(ChatViewModel.class);
        messageList = new ArrayList<>();

        mMessageAdapter = new MessageAdapter(messageList);
        mBinding.recyclerView.setAdapter(mMessageAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        mBinding.recyclerView.setLayoutManager(layoutManager);

        initListeners();
        initObservers();
    }

    private void initListeners() {
        mBinding.buttonSend.setOnClickListener(this);
        mBinding.buttonBack.setOnClickListener(this);
    }

    private void initObservers() {
        mViewModel.getLoadMessage().observe(getViewLifecycleOwner(), this::addResponse);
    }

    private void addMessageToChat(String message, String sentBy) {
        messageList.add(new MessageAdapter.Message(message, sentBy));
        mMessageAdapter.notifyDataSetChanged();
        mBinding.recyclerView.smoothScrollToPosition(mMessageAdapter.getItemCount());
    }

    void addResponse(String response) {
        messageList.remove(messageList.size()-1);
        addMessageToChat(response, SENT_BY_BOT);
    }

    private void callAPI(String question) {
        addMessageToChat("Typing...", SENT_BY_BOT);
        mViewModel.handleWithQuestion(question);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_back) {
            backScreen(v);
        } else if (v.getId() == R.id.button_send) {
            String message = mBinding.editMessage.getText().toString();
            mBinding.editMessage.setText("");
            addMessageToChat(message, MessageAdapter.Message.SENT_BY_ME);
            callAPI(message);
        }
    }
}