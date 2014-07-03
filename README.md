UIGridView
==========

UIGridView

UIGridViewExample uses https://github.com/sudhi001/UIGridView Library

Usage
==========

Example One:-

public class MainActivity extends Activity implements UIGridViewDataSource {

List<String> data = new ArrayList<String>();
private UIGridView<ViewHolder, String> grid;


@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    data.add("hello");
    data.add("hello");
    grid = (UIGridView<ViewHolder, String>) findViewById(R.id.StaticGridView);
    grid.setDatasourse(this);
    grid.setUiAdapterArray(data);
}

public class ViewHolder {
    TextView txt;

}


@Override
public ViewHolder holderForUIGridView() {
    return new ViewHolder();
}

@Override
public void onCreateUIGridViewView(View view, ViewHolder h) {
  h.txt=(TextView) view.findViewById(android.R.id.text1);
}

@Override
public void onUIGridViewViewCreated(View view, ViewHolder holder,
        String item) {
    holder.txt.setText(item);


}

}

activity_main.xml in layout folder

<com.androidhelpline.ui.widgets.UIGridView
    android:id="@+id/StaticGridView"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:horizontalSpacing="5dp"
    android:numColumns="1"
    android:stretchMode="columnWidth"
    android:verticalSpacing="5dp"      
    uigridview:viewLayout="@android:layout/simple_list_item_1"
    uigridview:delegateFunctionOnActivity="true" />

Example Two :-

public class MainActivity2 extends Activity {

List<String> data = new ArrayList<String>();
private UIGridView<ViewHolder, String> grid;

@SuppressWarnings("unchecked")
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_two);

    data.add("hello");
    data.add("hello");
    data.add("hello");
    data.add("hello");
    grid = (UIGridView<ViewHolder, String>) findViewById(R.id.StaticGridView2);
    grid.setAdapterArray(data);
}

public class ViewHolder {
    TextView txt;

}

public Object onCreateView(View view) {
    ViewHolder  h = new ViewHolder();
    h.txt = (TextView) view.findViewById(R.id.title);
    return h;

}

public void onViewCreated(View view, Object holder, Object item) {
    ViewHolder  h=(ViewHolder) holder;
    h.txt.setText(item.toString());

}

}

activity_main_two.xml in layout folder

<com.androidhelpline.ui.widgets.UIGridView
    android:id="@+id/StaticGridView2"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:horizontalSpacing="5dp"
    android:numColumns="1"
    android:stretchMode="columnWidth"
    android:verticalSpacing="5dp"
    uigridview:viewLayout="@layout/list_item_layout"
    uigridview:onCreateView="onCreateView" 
    uigridview:onViewCreated="onViewCreated" />

Developed By
==========


Sudhi S. sudhi001@icloud.com / droidsworld@gmail.com

License
==========


Copyright 2014 Sudhi S sudhi001@icloud.com / droidsworld@gmail.com

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License
