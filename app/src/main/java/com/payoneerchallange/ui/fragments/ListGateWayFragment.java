package com.payoneerchallange.ui.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.payoneerchallange.R;
import com.payoneerchallange.data.ApiResponse;
import com.payoneerchallange.databinding.FragmentListGateWayBinding;
import com.payoneerchallange.ui.adapter.GateWayViewAdapter;
import com.payoneerchallange.ui.viewmodel.GateWayListVm;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * This Fragment will make the api call and display the list of payment gateways
 */
@AndroidEntryPoint
public class ListGateWayFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

  private FragmentListGateWayBinding binding;
  private GateWayViewAdapter adapter;
  private GateWayListVm gateWayListVm;
    public ListGateWayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ListGateWayFragment.
     */
    public static ListGateWayFragment newInstance() {
        return new ListGateWayFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setHasOptionsMenu(true);
        //Add back press callback 
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentListGateWayBinding.inflate(inflater,container,false);
        gateWayListVm = new ViewModelProvider(this).get(GateWayListVm.class);

        setup();
        return binding.getRoot();
    }

    /**
     * Setup()-  Initializes UI components and makes api call
     */
    private void setup(){

        adapter = new GateWayViewAdapter(getActivity(),gateWayListVm.getGatewayList());
        binding.paymentList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.paymentList.setItemAnimator(new DefaultItemAnimator());
        binding.paymentList.setAdapter(adapter);

        binding.shimmerViewContainer.startShimmerAnimation();
        binding.swipeRefresh.setOnRefreshListener(this);
        fetchGateWay();
    }

    /**
     * This function makes the api call to fetch gateways and updates the UI
     */
    private void fetchGateWay(){
        gateWayListVm.listGateways().observe(getActivity(), apiResponse -> {

            binding.shimmerViewContainer.stopShimmerAnimation();
            binding.shimmerViewContainer.setVisibility(View.GONE);
            if(apiResponse.getError()!=null){
                showErrorDialog(apiResponse.getError());
                return;
            }

            if(binding.swipeRefresh.isRefreshing()){
                binding.swipeRefresh.setRefreshing(false);
            }

            gateWayListVm.setGatewayList(apiResponse.getDataMap().getGateways());
            adapter.notifyDataSetChanged();

        });
    }



    public void showErrorDialog(String message){

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        alertBuilder.setMessage(message);
        alertBuilder.setPositiveButton("Close", (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onRefresh() {
        gateWayListVm.getGatewayList().clear();
        binding.swipeRefresh.setRefreshing(true);
        fetchGateWay();
    }
}