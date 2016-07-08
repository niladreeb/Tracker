package com.demo.tracker.model;

import android.os.AsyncTask;

import com.demo.tracker.api.ApiManager;
import com.demo.tracker.viewController.TrackMeFragment;

import de.greenrobot.event.EventBus;

public class LocationModel {

    private boolean mIsBusy = false;

    public LocationModel() {
    }

    public void load() {
        // Do nothing if busy
        if (mIsBusy) {
            return;
        }
        // Issue the API in asynchronous
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Busy state true
                mIsBusy = true;
            }
            @Override
            protected Void doInBackground(Void... voids) {
                ApiManager.postLocationItem(TrackMeFragment.mLocationEntity);
                return null;
            }
            @Override
            protected void onPostExecute(Void response) {
                super.onPostExecute(response);
                // Releasing the busy state
                mIsBusy = false;
                // It sends a completion notice in EventBus
                EventBus.getDefault().post(new LocationLoadedEvent(true));
            }

        }.execute();
    }

    //It is possible to throw stuffed a value to the event (in this case, success / failure flag only)
    public static class LocationLoadedEvent {
        private boolean isSuccess;

        private LocationLoadedEvent(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public boolean isSuccess() {
            return isSuccess;
        }
    }
}
