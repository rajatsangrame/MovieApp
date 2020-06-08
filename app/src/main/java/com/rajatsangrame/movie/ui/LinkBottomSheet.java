package com.rajatsangrame.movie.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.rajatsangrame.movie.R;
import com.rajatsangrame.movie.databinding.BottomSheetBinding;

/**
 * Todo: Replace web-view with chrome-tabs
 */
public class LinkBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {

    private BottomSheetBinding binding;
    public static final String TAG = "LinkBottomSheet";
    private String url = "";

    public static LinkBottomSheet newInstance() {
        Bundle args = new Bundle();
        LinkBottomSheet fragment = new LinkBottomSheet();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet, container, false);
        binding.btnClose.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!this.url.isEmpty()) {
            binding.webView.loadUrl(url);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_close) {
            dismiss();
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
