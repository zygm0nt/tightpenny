package com.ftang.tightpenny;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ftang.tightpenny.model.AggregateSpendingEntry;
import com.ftang.tightpenny.model.Category;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {
    private final Activity context;
    private final List<Category> values;

    static class ViewHolder {
        public TextView text;
    }

    public CategoryAdapter(Activity context, List<Category> values) {
        super(context, R.layout.simple_row_layout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            rowView = createNewRowView();
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        Category entry = values.get(position);
        holder.text.setText(entry.getTitle());

        return rowView;
    }

    @NonNull
    private View createNewRowView() {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.simple_row_layout, null);
        // configure view holder
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.text = (TextView) rowView.findViewById(R.id.list_item);
        rowView.setTag(viewHolder);
        return rowView;
    }
}
