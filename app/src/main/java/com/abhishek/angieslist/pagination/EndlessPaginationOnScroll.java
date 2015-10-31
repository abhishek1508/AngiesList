package com.abhishek.angieslist.pagination;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by Abhishek on 10/28/2015.
 */
public abstract class EndlessPaginationOnScroll extends RecyclerView.OnScrollListener {

    private int mThreshold = 5; //Min number of items to have below current position to load next page.
    private boolean isLoading = true;//set to be true if the last data set is still loading.
    private int mPreviousTotal = 0;//Total number of items in the dataset after last load results.
    int firstVisibleItem, visibleItemCount, totalItemCount;
    public static final String TAG = EndlessPaginationOnScroll.class.getSimpleName();

    private int mCurrentPage = 0;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessPaginationOnScroll(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        Log.d(TAG, "firstVisibleItem is : " + firstVisibleItem);

        if (isLoading) {
            if (totalItemCount > mPreviousTotal) {
                isLoading = false;
                mPreviousTotal = totalItemCount;
                Log.d(TAG, "mPreviousTotal is : " + mPreviousTotal);
            }
        }
        if (!isLoading
                && (mPreviousTotal - visibleItemCount) <= (firstVisibleItem + mThreshold)) {
            mCurrentPage++;
            loadMore(mCurrentPage);
            isLoading = true;
        }
    }

    public abstract void loadMore(int mCurrentPage);
}
