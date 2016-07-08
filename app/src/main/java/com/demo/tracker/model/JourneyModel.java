package com.demo.tracker.model;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import com.demo.tracker.api.ApiManager;

public class JourneyModel {

    private final List<JourneyEntity> mJourneyList = new ArrayList<>();
    private int mItemCount = 0;
    private boolean mIsBusy = false;

    public JourneyModel() {
    }

    public List<JourneyEntity> getItemList() {
        return mJourneyList;
    }

    public int getItemCount() {
        return mItemCount;
    }

    public void load() {
        // Do nothing if busy
        if (mIsBusy) {
            return;
        }
        // Issue the API in asynchronous
        new AsyncTask<Void, Void, List<JourneyEntity>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Busy state true
                mIsBusy = true;
            }

            @Override
            protected List<JourneyEntity> doInBackground(Void... voids) {
                return ApiManager.getJourneyItem();
            }

            @Override
            protected void onPostExecute(List<JourneyEntity> result) {
                super.onPostExecute(result);
                // Get the results update the list (see maintenance)
                mJourneyList.clear();
                mJourneyList.addAll(result);
                // Update the number
                mItemCount = result.size();
                // Releasing the busy state
                mIsBusy = false;
                // It sends a completion notice in EventBus
                EventBus.getDefault().post(new JourneyLoadedEvent(true));
            }
        }.execute();
    }

    //It is possible to throw stuffed a value to the event (in this case, success / failure flag only)
    public static class JourneyLoadedEvent {
        private boolean isSuccess;

        private JourneyLoadedEvent(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public boolean isSuccess() {
            return isSuccess;
        }
    }
}
