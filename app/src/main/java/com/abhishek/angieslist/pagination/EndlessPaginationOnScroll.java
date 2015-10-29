package com.abhishek.angieslist.pagination;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Abhishek on 10/28/2015.
 */
public abstract class EndlessPaginationOnScroll extends RecyclerView.OnScrollListener {

    private int mPreviousTotal = 0;//Total number of items in the dataset after last load results.
    private boolean isLoading = true;//set to be true if we last data set is still loading.
    private int mThreshold = 5; //Min number of items to have below current position to load next page.
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private int mCurrentPage = 1;

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

        if (isLoading) {
            if (totalItemCount > mPreviousTotal) {
                isLoading = false;
                mPreviousTotal = totalItemCount;
            }
        }
        if (!isLoading
                && (totalItemCount - visibleItemCount) <= (firstVisibleItem + mThreshold)) {
            mCurrentPage++;
            loadMore(mCurrentPage);
            isLoading = true;
        }
    }

    public abstract void loadMore(int mCurrentPage);
}
