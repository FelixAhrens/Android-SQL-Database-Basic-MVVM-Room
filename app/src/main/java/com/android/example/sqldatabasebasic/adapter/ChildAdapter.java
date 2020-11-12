package com.android.example.sqldatabasebasic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.sqldatabasebasic.R;
import com.android.example.sqldatabasebasic.database.Child;

import java.util.ArrayList;
import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ChildHolder> {
    private List<Child> children = new ArrayList<>();
    private List<Integer> mChildIdList = new ArrayList<Integer>();
    private boolean mLayout;

    public ChildAdapter(List<Integer> childIdList, List<Child> child, boolean layout) {
        mChildIdList = childIdList;
        children = child;
        mLayout = layout;
    }

    @NonNull
    @Override
    public ChildHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView;
        if (mLayout) {
            itemView = inflater.inflate(R.layout.child_item_horizontal, viewGroup, false);
        } else {
            itemView = inflater.inflate(R.layout.child_item_grid, viewGroup, false);
        }
        return new ChildHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildHolder holder, int position) {
        int childId = mChildIdList.get(position);

        for (int i = 0; i < children.size(); i++) {
            if (childId == children.get(i).getId()) {
                holder.textViewTitle.setText(children.get(i).getTitle() + ": ");
                holder.textViewNumber.setText(String.valueOf(children.get(i).getNumber()));
                holder.textViewChildFrom.setText("Child from: " + children.get(i).getParentId());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mChildIdList.size();
    }

    class ChildHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewChildFrom;
        private TextView textViewNumber;

        public ChildHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title_child);
            textViewNumber = itemView.findViewById(R.id.text_view_number_child);
            textViewChildFrom = itemView.findViewById(R.id.text_view_child_from_parent);
        }
    }

}
