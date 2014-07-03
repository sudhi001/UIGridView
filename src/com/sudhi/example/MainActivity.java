package com.sudhi.example;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.androidhelpline.ui.widgets.UIGridView;
import com.androidhelpline.ui.widgets.UIGridView.UIGridViewDataSource;
import com.sudhi.example.MainActivity.ViewHolder;

public class MainActivity extends Activity implements
		UIGridViewDataSource<ViewHolder, String> {

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
