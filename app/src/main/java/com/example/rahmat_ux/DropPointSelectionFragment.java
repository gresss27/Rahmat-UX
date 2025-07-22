package com.example.rahmat_ux;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.rahmat_ux.adapter.DropPointAdapter;
import com.example.rahmat_ux.data.DonationFlowRepository;
import com.example.rahmat_ux.databinding.FragmentDropPointSelectionBinding;
import com.example.rahmat_ux.model.DropPoint;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.List;

public class DropPointSelectionFragment extends BottomSheetDialogFragment {

    private FragmentDropPointSelectionBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDropPointSelectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<DropPoint> dropPointList = DonationFlowRepository.getDropPoints();
        DropPointAdapter adapter = new DropPointAdapter(dropPointList, this);
        binding.recyclerViewDropPoints.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewDropPoints.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}