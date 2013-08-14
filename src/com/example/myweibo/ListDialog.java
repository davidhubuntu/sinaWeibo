package com.example.myweibo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ListDialog extends Dialog {

	LayoutInflater inflater = null;
	List<String> list_item;
	TextView itemName;
	
	public ListDialog(Context context, List<String> list_data) {
		super(context, R.style.dialogTitleSize);
		list_item = list_data;
//		inflater = LayoutInflater.from(context);

	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_dialog);
		GridView gridview = (GridView)findViewById(R.id.list_dialog_gridview);
		
		gridview.setColumnWidth(getWindow().getWindowManager().getDefaultDisplay().getWidth());
		ArrayList<HashMap<String, String>> item = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < list_item.size(); i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("item_text", list_item.get(i));
			item.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(getContext(), item, R.layout.list_dialog_item, 
				new String[]{"item_text"}, new int[]{R.id.list_dialog_item});
		
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				 System.out.println("aaaaaaaaaaaaaaaaaaaaaa   " + list_item.get(position));
				 ListDialog.this.dismiss();
			}
		});
		
		setTitle("·Ö×é");
	}

	public void setTitle(String title) {
		((TextView)findViewById(R.id.dialog_title_bar)).setText(title);
	}
}
