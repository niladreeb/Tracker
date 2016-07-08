package com.demo.tracker;

import android.app.Application;

import com.demo.tracker.common.FragmentRouter;
import com.demo.tracker.common.ModelLocator;
import com.demo.tracker.model.JourneyModel;
import com.demo.tracker.model.LocationModel;
import com.demo.tracker.viewController.JourneyDetailsFragment;
import com.demo.tracker.viewController.JourneyFragment;
import com.demo.tracker.viewController.TrackMeFragment;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Create a reference of each model to ModelManager
        ModelLocator.register(ModelLocator.Tag.JOURNEY_ITEM, new JourneyModel());
        ModelLocator.register(ModelLocator.Tag.LOCATION_ITEM, new LocationModel());

        // Register the class name of each Fragment to FragmentManager
        FragmentRouter.register(FragmentRouter.Tag.LIST, JourneyFragment.class);
        FragmentRouter.register(FragmentRouter.Tag.DETAILS, JourneyDetailsFragment.class);
        FragmentRouter.register(FragmentRouter.Tag.HOME, TrackMeFragment.class);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
