/**
 * Copyright 2014 Sudhi S
 * sudhi001@icloud.com /droidsworld@gmail.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.androidhelpline.ui.widgets;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

/**
 * 
 * @author Sudhi.S sudhi001@icloud.com /droidsworld@gmail.com
 * @param <Holder>
 *            Holder object Type
 * @param <DataModel>
 *            Data source object Type
 * 
 */

public class UIGridView<Holder, DataModel> extends GridView {

	  private final static String TAG = "UIGridView";
    private BaseAdapter adapter;
    private List<DataModel> adapterArray = new ArrayList<DataModel>();
    private UIGridViewDataSource<Holder, DataModel> datasourse;
    LayoutInflater inflater;
    private boolean isDelegateFunctionOnActivity = false;

    private Method mOnCreateView;

    private Method mOnViewCreated;

    private int viewLayoutResId = 0;

    public UIGridView(Context context) {
        this(context, null);
    }

    public UIGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public UIGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public UIGridViewDataSource<?, ?> getDatasourse() {
        return datasourse;
    }

    private void init(AttributeSet attrs) {
        inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs,
                    R.styleable.UIGridView);
            try {
                viewLayoutResId = a.getResourceId(
                        R.styleable.UIGridView_viewLayout,
                        android.R.layout.simple_list_item_1);
                final String onCreateView = a
                        .getString(R.styleable.UIGridView_onCreateView);
                final String onViewCreated = a
                        .getString(R.styleable.UIGridView_onViewCreated);
                isDelegateFunctionOnActivity = a.getBoolean(
                        R.styleable.UIGridView_delegateFunctionOnActivity,
                        false);
                if (onCreateView != null && onViewCreated != null
                        && !isDelegateFunctionOnActivity) {
                    try {
                        mOnCreateView = getContext().getClass().getMethod(
                                onCreateView, View.class);
                    } catch (NoSuchMethodException e) {
                        Log.e(TAG, "No Such Methode in tag Named onCreateView");
                    }
                    try {
                        mOnViewCreated = getContext().getClass().getMethod(
                                onViewCreated, View.class, Object.class,
                                Object.class);

                    } catch (NoSuchMethodException e) {
                        Log.e(TAG, "No Such Methode in tag Named onViewCreated");

                    }
                }
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage());
            } finally {

                a.recycle();
            }
        }
    }

    public void notifyDataSetChange() {
        adapter.notifyDataSetChanged();
        adapter.notifyDataSetInvalidated();
    }

    public void removeData(Object object) {
        this.adapterArray.remove(object);
    }

    public void setAdapterArray(List<DataModel> adapterArray) {

        this.adapterArray = adapterArray;
        adapter = new SimpleDataAdapter();
        this.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void setDatasourse(UIGridViewDataSource<Holder, DataModel> datasourse) {
        this.datasourse = datasourse;
    }

    public void setUiAdapterArray(List<DataModel> adapterArray) {
        this.adapterArray = adapterArray;
        adapter = new UIGridViewAdapter(this.adapterArray);
        this.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @SuppressWarnings("unchecked")
    public UIGridViewAdapter getUIGridViewAdapter()
    {
        return (UIGridView<Holder, DataModel>.UIGridViewAdapter) adapter;
    }

    private class SimpleDataAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return adapterArray.size();
        }

        @Override
        public Object getItem(int i) {
            return adapterArray.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @SuppressWarnings("unchecked")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Holder holder = null;
            try {
                if (view == null) {
                    view = inflater.inflate(viewLayoutResId, viewGroup, false);
                    holder = (Holder) mOnCreateView.invoke(getContext(), view);
                    view.setTag(holder);
                } else {
                    holder = (Holder) view.getTag();
                }
                DataModel d = (DataModel) getItem(i);
                mOnViewCreated.invoke(getContext(), view, holder, d);
            } catch (Exception e) {
                Log.e(TAG, e.getStackTrace().toString());
            }
            return view;

        }
    }

    public class UIGridViewAdapter extends BaseAdapter implements
            Filterable {
        List<DataModel> mDatas;
        List<DataModel> mOriginalData;

        public UIGridViewAdapter(List<DataModel> mList) {
            this.mOriginalData = mList;
            this.mDatas = new ArrayList<DataModel>(mOriginalData);
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressWarnings("unchecked")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder h;
            if (convertView == null) {
                h = (Holder) datasourse.holderForUIGridView();
                convertView = (View) inflater.inflate(viewLayoutResId, parent,
                        false);
                datasourse.onCreateUIGridViewView(convertView, h);
                convertView.setTag(h);
            } else {
                h = (Holder) convertView.getTag();
            }

            DataModel d = (DataModel) getItem(position);
            datasourse.onUIGridViewViewCreated(convertView, h, d, position);
            return convertView;
        }

        @Override
        public Filter getFilter() {

            return new Filter() {

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    constraint = constraint.toString().toLowerCase();

                    FilterResults result = new FilterResults();

                    if (constraint != null
                            && constraint.toString().length() > 0) {
                        List<DataModel> founded = new ArrayList<DataModel>();
                        for (DataModel item : mOriginalData) {
                            datasourse.onFilter(founded, item, constraint);
                        }

                        result.values = founded;
                        result.count = founded.size();
                    } else {
                        result.values = mOriginalData;
                        result.count = mOriginalData.size();
                    }
                    return result;

                }

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint,
                        FilterResults results) {
                    mDatas = (List<DataModel>) results.values;
                    notifyDataSetChanged();

                }

            };
        }
    }

    public interface UIGridViewDataSource<H, DataModel> {

        /**
         * Create Class For Holding View Objects
         * 
         * @return View Holder object
         */
        public H holderForUIGridView();

        /**
         * Call This Function only Once
         * 
         * @param view
         * @param holder View Holder Object
         */
        public void onCreateUIGridViewView(View view, H holder);

        /**
         * Call this function for every list item
         * 
         * @param view
         * @param holder View Holder Object
         * @param item item Object For Each Row
         */
        public void onUIGridViewViewCreated(View view, H holder, DataModel item, int position);

        public void onFilter(List<DataModel> mList, DataModel item, CharSequence constraint);

    }

}
