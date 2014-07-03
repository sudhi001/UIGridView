package com.sudhi.example;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.androidhelpline.ui.widgets.UIGridView;

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
		ViewHolder	h = new ViewHolder();
		h.txt = (TextView) view.findViewById(R.id.title);
		return h;
		
	}

	public void onViewCreated(View view, Object holder, Object item) {
		ViewHolder	h=(ViewHolder) holder;
		h.txt.setText(item.toString());

	}

}
