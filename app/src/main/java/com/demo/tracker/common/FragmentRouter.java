package com.demo.tracker.common;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.HashMap;
import java.util.Map;

import com.demo.tracker.R;

// Class to perform Fragment switching

public class FragmentRouter {

    public enum Tag {
        LIST,
        DETAILS,
        HOME
    }

    private static final int ANIM_RES_SLIDE_IN_RIGHT = R.anim.slide_in_right;
    private static final int ANIM_RES_SLIDE_OUT_RIGHT = R.anim.slide_out_right;
    private static final int ANIM_RES_SLIDE_IN_BOTTOM = R.anim.slide_in_bottom;
    private static final int ANIM_RES_SLIDE_OUT_BOTTOM = R.anim.slide_out_bottom;
    private static final int ANIM_RES_FADE_IN = R.anim.fade_in;
    private static final int ANIM_RES_FADE_OUT = R.anim.fade_out;

    public enum Animation {
        NON,
        SLIDE_IN_RIGHT,
        SLIDE_IN_BOTTOM,
        FADE_IN,
    }

    private static Map<Tag, Class> showcase = new HashMap<>();

    private FragmentRouter() {
    }

    public static void register(Tag tag, Class c) {
        showcase.put(tag, c);
    }

    private static Class get(Tag tag) {
        return showcase.get(tag);
    }

    public static void replace(FragmentManager fragmentManager, @IdRes int container, Tag tag, Bundle args, Animation animation) {
        replace(fragmentManager, container, tag, args, animation, true);
    }

    public static void replace(FragmentManager fragmentManager, @IdRes int container, Tag tag, Bundle args, Animation animation, boolean addToBackStack) {
        Fragment fragment = fragmentManager.findFragmentByTag(String.valueOf(tag));
        if (fragment == null) {
            try {
                Class c = get(tag);
                fragment = (Fragment)c.newInstance();
                fragment.setArguments(args);
            } catch (Exception e) {
                return;
            }
        }

        int enterAnim;
        int exitAnim;
        int popEnterAnim;
        int popExitAnim;
        switch (animation) {
            case SLIDE_IN_RIGHT:
                enterAnim = ANIM_RES_SLIDE_IN_RIGHT;
                exitAnim = ANIM_RES_FADE_OUT;
                popEnterAnim = ANIM_RES_FADE_IN;
                popExitAnim = ANIM_RES_SLIDE_OUT_RIGHT;
                break;
            case SLIDE_IN_BOTTOM:
                enterAnim = ANIM_RES_SLIDE_IN_BOTTOM;
                exitAnim = ANIM_RES_FADE_OUT;
                popEnterAnim = ANIM_RES_FADE_IN;
                popExitAnim = ANIM_RES_SLIDE_OUT_BOTTOM;
                break;
            case FADE_IN:
            case NON:
                enterAnim = ANIM_RES_FADE_IN;
                exitAnim = ANIM_RES_FADE_OUT;
                popEnterAnim = ANIM_RES_FADE_IN;
                popExitAnim = ANIM_RES_FADE_OUT;
                break;
            default:
                return;
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (animation != Animation.NON) { fragmentTransaction.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim); }
        fragmentTransaction.replace(container, fragment, String.valueOf(tag));
        if (addToBackStack) { fragmentTransaction.addToBackStack(null); }
        fragmentTransaction.commit();
    }

    public static Fragment newInstance(Tag tag, Bundle args) {
        try {
            Class c = get(tag);
            Fragment fragment = (Fragment)c.newInstance();
            fragment.setArguments(args);
            return fragment;
        } catch (Exception e) {
            return null;
        }
    }

    public static Fragment getInstance(FragmentManager fragmentManager, Tag tag) {
        return fragmentManager.findFragmentByTag(String.valueOf(tag));
    }
}