package com.demo.tracker.viewController;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.tracker.R;
import com.demo.tracker.common.Const;
import com.demo.tracker.model.JourneyEntity;
import com.demo.tracker.model.JourneyModel;
import com.demo.tracker.common.FragmentRouter;
import com.demo.tracker.common.ModelLocator;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;

import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class JourneyFragment extends Fragment {

    @InjectView(R.id.itemCountTextView)
    TextView mItemCountTextView;
    @InjectView(R.id.journeyListView)
    ListView mJourneyListView;

    private final List<JourneyEntity> mJourneyList = new ArrayList<>();
    private JourneyListAdapter mJourneyListAdapter;

    public JourneyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_journey_list, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_journey);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mJourneyListAdapter = new JourneyListAdapter(
                getActivity(),
                R.layout.adapter_journey_list,
                mJourneyList
        );
        mJourneyListView.setAdapter(mJourneyListAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        ((JourneyModel) ModelLocator.get(ModelLocator.Tag.JOURNEY_ITEM)).load();
        updateView();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this); // EventBus
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this); // EventBus
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    // ListView item click
    @SuppressWarnings("unused")
    @OnItemClick(R.id.journeyListView)
    public void onItemClickJourneyListView(int position) {
        Bundle args = new Bundle();
        double[] coordinate = {mJourneyList.get(position).startCoordinate.lat,
                               mJourneyList.get(position).startCoordinate.lng,
                               mJourneyList.get(position).endCoordinate.lat,
                               mJourneyList.get(position).endCoordinate.lng,};
        String route = mJourneyList.get(position).route;
        String[] locality = {mJourneyList.get(position).startLocality,mJourneyList.get(position).endLocality};
        args.putDoubleArray(Const.BundleKey.COORDINATE.toString(), coordinate);
        args.putString(Const.BundleKey.ROUTE.toString(),route);
        args.putStringArray(Const.BundleKey.LOCALITY.toString(),locality);
        FragmentRouter.replace(getFragmentManager(), R.id.frame_container, FragmentRouter.Tag.DETAILS, args, FragmentRouter.Animation.SLIDE_IN_BOTTOM);
    }

    // Notification from EventBus
    @SuppressWarnings("unused")
    public void onEventMainThread(JourneyModel.JourneyLoadedEvent event) {
        if (event.isSuccess()) {
            updateView();
        }
    }

    // Private method to update the display of View
    private void updateView() {

        mItemCountTextView.setText(((JourneyModel) ModelLocator.get(ModelLocator.Tag.JOURNEY_ITEM)).getItemCount() + " journeys");
        mJourneyList.clear();
        mJourneyList.addAll(((JourneyModel) ModelLocator.get(ModelLocator.Tag.JOURNEY_ITEM)).getItemList());
        mJourneyListAdapter.notifyDataSetChanged();
    }
}

