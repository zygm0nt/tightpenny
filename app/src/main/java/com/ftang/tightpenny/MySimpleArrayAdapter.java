package com.ftang.tightpenny;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftang.tightpenny.model.AggregateSpendingEntry;

import java.util.List;

public class MySimpleArrayAdapter extends ArrayAdapter<AggregateSpendingEntry> {
    private final Activity context;
    private final List<AggregateSpendingEntry> values;

    static class ViewHolder {
        public TextView text;
        public TextView amount;
    }

    public MySimpleArrayAdapter(Activity context, List<AggregateSpendingEntry> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

   /* @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        textView.setText(values[position]);
        // change the icon for Windows and iPhone
        String s = values[position];
        if (s.startsWith("iPhone")) {
            imageView.setImageResource(R.drawable.not_ok);
        } else {
            imageView.setImageResource(R.drawable.ok);
        }

        return rowView;
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            rowView = createNewRowView();
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        AggregateSpendingEntry entry = values.get(position);
        holder.text.setText(entry.getCategory());
        holder.amount.setText(entry.amount().toString()); // TODO better formatting

        return rowView;
    }

    @NonNull
    private View createNewRowView() {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.rowlayout, null);
        // configure view holder
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.text = (TextView) rowView.findViewById(R.id.label);
        viewHolder.amount = (TextView) rowView.findViewById(R.id.amount);
        rowView.setTag(viewHolder);
        return rowView;
    }
}
