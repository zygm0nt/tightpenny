package com.ftang.tightpenny;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.ftang.tightpenny.model.AggregateSpendingEntry;
import com.ftang.tightpenny.model.Category;
import com.ftang.tightpenny.model.SimpleSpendingEntry;
import com.ftang.tightpenny.model.SpendingEntry;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.math.BigDecimal;
import java.util.ArrayList;

import kotlin.Unit;

public class ExpandableAggregateSpendingEntryAdapter extends BaseExpandableListAdapter {

    private final SparseArray<AggregateSpendingEntry> values;
    private final RemoveSpending removeSpending;
    public LayoutInflater inflater;
    public Activity activity;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");

    static class ViewHolder {
        public TextView text;
        public TextView amount;
        public TextView limit;
    }

    static class DetailsViewHolder {
        public TextView amount;
        public TextView date;
    }

    public ExpandableAggregateSpendingEntryAdapter(Activity act, ArrayList<AggregateSpendingEntry> values, RemoveSpending removeSpending) {
        activity = act;
        this.values = toSparseArray(values);
        inflater = act.getLayoutInflater();
        this.removeSpending = removeSpending;
    }

    private SparseArray<AggregateSpendingEntry> toSparseArray(ArrayList<AggregateSpendingEntry> values) {
        final SparseArray<AggregateSpendingEntry> result = new SparseArray<>();
        for (int i = 0; i < values.size(); i++) {
            result.append(i, values.get(i));
        }
        return result;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return values.get(groupPosition).getEntries().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final SimpleSpendingEntry entry = (SimpleSpendingEntry) getChild(groupPosition, childPosition);
        View rowView = convertView;
        if (rowView == null) {
            rowView = createNewRowDetailsView();
        }
        DetailsViewHolder holder = (DetailsViewHolder) rowView.getTag();
        holder.amount.setText(String.format("%.2f", entry.getAmount()));
        holder.date.setText(dateTimeFormatter.print(entry.getDate()));

        rowView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                v.animate().setDuration(2000).alpha(0.0f)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                SimpleSpendingEntry entry = values.get(groupPosition).getEntries().get(childPosition);
                                values.get(groupPosition).getEntries().remove(childPosition);
                                removeSpending.removeSpending(entry.getUuid());
                                notifyDataSetChanged();
                            }
                        });
                return false;
            }
        });
        return rowView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return values.get(groupPosition).getEntries().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return values.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return values.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            rowView = createNewRowView();
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        AggregateSpendingEntry entry = (AggregateSpendingEntry) getGroup(groupPosition);
        holder.text.setText(entry.getCategory().getTitle());
        holder.amount.setText(String.format("%.2f", entry.amount()));

        if (!entry.limit().equals(BigDecimal.ZERO)) {
            holder.limit.setText(String.format("limit: %.2f", entry.currentDailyLimit()));
        } else {
            holder.limit.setText("");
        }
        return rowView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @NonNull
    private View createNewRowView() {
        View rowView = inflater.inflate(R.layout.rowlayout, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.text = (TextView) rowView.findViewById(R.id.label);
        viewHolder.amount = (TextView) rowView.findViewById(R.id.amount);
        viewHolder.limit = (TextView) rowView.findViewById(R.id.limit);
        rowView.setTag(viewHolder);
        return rowView;
    }

    @NonNull
    private View createNewRowDetailsView() {
        View rowView = inflater.inflate(R.layout.listrow_details, null);
        DetailsViewHolder viewHolder = new DetailsViewHolder();
        viewHolder.date = (TextView) rowView.findViewById(R.id.detailsRowDate);
        viewHolder.amount = (TextView) rowView.findViewById(R.id.detailsRow);
        rowView.setTag(viewHolder);
        return rowView;
    }

    public void addSpending(SpendingEntry entry) {
        AggregateSpendingEntry thatEntry = null;
        for (int i = 0; i < values.size(); i++) {
            AggregateSpendingEntry aggEntry = values.get(i);
            if (aggEntry.getCategory().equals(entry.getCategory())) {
                thatEntry = aggEntry;
                break;
            }
        }

        if (thatEntry == null) {
            thatEntry = new AggregateSpendingEntry(entry.getCategory(), new ArrayList<SimpleSpendingEntry>());
            values.append(values.size(), thatEntry);
        }
        thatEntry.addEntry(entry);


        notifyDataSetChanged();
    }
}