package com.demo.tracker.viewController;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.demo.tracker.R;
import com.demo.tracker.model.JourneyEntity;

import java.util.List;

import butterknife.InjectView;
import butterknife.ButterKnife;

public class JourneyListAdapter extends ArrayAdapter<JourneyEntity> {

    private LayoutInflater mLayoutInflater;
    private int mResource;

    //Constructor
    public JourneyListAdapter(Context context, int resource, List<JourneyEntity> objects) {
        super(context, resource, objects);
        mLayoutInflater = LayoutInflater.from(context);
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mLayoutInflater.inflate(mResource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        JourneyEntity journeyEntity = getItem(position);
        viewHolder.mStartLocalityTextView.setText(journeyEntity.startLocality);
        viewHolder.mEndLocalityTextView.setText(journeyEntity.endLocality);
        viewHolder.mStartDateTextView.setText(journeyEntity.startDate.date.substring(0,16));
        viewHolder.mEndDateTextView.setText(journeyEntity.endDate.date.substring(0,16));

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.startLocalityTextView)
        TextView mStartLocalityTextView;
        @InjectView(R.id.endLocalityTextView)
        TextView mEndLocalityTextView;
        @InjectView(R.id.startDate)
        TextView mStartDateTextView;
        @InjectView(R.id.endDate)
        TextView mEndDateTextView;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}