package com.example.sherif.trialcontacts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Sherif on 6/26/2018.
 */

class RecylerClickListener implements RecyclerView.OnItemTouchListener {

    private ClickListener clickListener;
    //Object to detect motion events and their type (longPress , singleTap ,etc..)
    private GestureDetector mGestureDetector;

    public RecylerClickListener(Context context, final RecyclerView rv, final ClickListener clickListener) {
        this.clickListener = clickListener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                //find the child that has interacted with the motionEvent
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                //check the conditions
                if (child != null && clickListener != null) {
                    clickListener.onLongClick(child, rv.getChildAdapterPosition(child));
                }
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }
    /*
     * should always return false to prevent further processing of the motion event by
     * the children of the recyclerView
     */

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        /* onIntercepting the motion event of the touch
         *  the motion event is given to the gesture detector to determine its type
         *  if its of type singleTap then, the onclick listener will be triggered
         */
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && mGestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildAdapterPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        //empty method
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        //empty method
    }

    public interface ClickListener {
        public void onClick(View v, int position);

        public void onLongClick(View v, int position);
    }
}
