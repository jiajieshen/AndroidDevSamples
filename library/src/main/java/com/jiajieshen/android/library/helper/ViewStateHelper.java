package com.jiajieshen.android.library.helper;

import android.support.annotation.IntDef;
import android.util.SparseArray;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * View 状态助手
 * Created by sum on 3/12/17.
 */
public class ViewStateHelper {

    public static final int STRATEGY_REPLACE = 0;
    public static final int STRATEGY_APPEND = 1;

    /**
     * 根据 state 显示 view 的策略：
     * 替换策略（STRATEGY_REPLACE）：将所有正在显示的 views 隐藏，显示 state 绑定的 view；
     * 追加策略（STRATEGY_APPEND）：保持正在显示的 views 不变，追加显示 state 绑定的 view；
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STRATEGY_REPLACE, STRATEGY_APPEND})
    public @interface Strategy {
    }

    /**
     * 保存 state 和 view 绑定关系的稀疏数组
     * state 为 key 值；
     * view 为 value 值；
     */
    private SparseArray<View> views = new SparseArray<>();

    /**
     * 绑定 state 和 view
     *
     * @param state 要绑定的状态
     * @param view  要绑定的 view
     */
    public void bindView(int state, View view) {
        views.put(state, view);
    }

    /**
     * 解绑 state 和 view
     *
     * @param state 状态
     */
    public void unbindView(int state) {
        views.remove(state);
    }

    /***
     * @param state 要显示的状态
     */
    public void showView(int state) {
        showView(state, STRATEGY_REPLACE);
    }

    /***
     * 将 state 绑应的 view 设为 visible，并根据不同的显示策略，修改正在显示的 view 的 visibility
     * @param state 状态
     * @param strategy 显示策略
     */
    public void showView(int state, @Strategy int strategy) {
        if (views.size() == 0) {
            return;
        }
        switch (strategy) {
            case STRATEGY_REPLACE:
                for (int i = 0, size = views.size(); i < size; i++) {
                    View view = views.valueAt(i);
                    if (view.getVisibility() == View.VISIBLE) {
                        view.setVisibility(View.GONE);
                    }
                    if (state == views.keyAt(i)) {
                        view.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case STRATEGY_APPEND:
                View view = views.get(state);
                view.setVisibility(View.VISIBLE);
                break;
            default:
                throw new IllegalArgumentException("Miss the strategy.");
        }
    }

    /**
     * 将 state 绑定的 view 设为 gone
     *
     * @param state 状态
     */
    public void goneView(int state) {
        if (views.size() == 0) {
            return;
        }
        View view = views.get(state);
        view.setVisibility(View.GONE);
    }

    /**
     * 将 state 绑定的 view 设为 invisible
     *
     * @param state 状态
     */
    public void hideView(int state) {
        if (views.size() == 0) {
            return;
        }
        View view = views.get(state);
        view.setVisibility(View.INVISIBLE);
    }
}
