package com.android.example.sqldatabasebasic.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.sqldatabasebasic.R;
import com.android.example.sqldatabasebasic.database.Child;
import com.android.example.sqldatabasebasic.database.Parent;

import java.util.ArrayList;
import java.util.List;

public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.ParentHolder> {
    private List<Parent> parents = new ArrayList<>();
    private List<Child> children = new ArrayList<>();

    private OnItemClickListener listener;

    private Context mContext;
    private boolean layout;

    public ParentAdapter(){}

    public ParentAdapter(Context context, boolean isMenuChecked) {
        mContext = context;
        layout = isMenuChecked;
    }

    @NonNull
    @Override
    public ParentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.parent_item, viewGroup, false);
        return new ParentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentHolder holder, int position) {
        Log.i("position", "position: " + position);
        Parent currentParent = parents.get(position);
        holder.textViewTitle.setText(currentParent.getTitle());
        holder.textViewDescription.setText(currentParent.getDescription());
        holder.textViewNumber.setText(String.valueOf(currentParent.getNumber()));

        if (layout) {
            holder.recyclerViewChild.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        } else {
            holder.recyclerViewChild.setLayoutManager(new GridLayoutManager(mContext, 3));
        }

        holder.recyclerViewChild.setHasFixedSize(true);

        List<Integer> childIdList = new ArrayList<>();
        for (int i = 0; i < children.size(); i++) {
            if (currentParent.getNumber() == children.get(i).getParentId()) {
                childIdList.add(children.get(i).getId());
            }
        }

        ChildAdapter childAdapter = new ChildAdapter(childIdList, children, layout);
        holder.recyclerViewChild.setAdapter(childAdapter);

    }

    @Override
    public int getItemCount() {
        return parents.size();
    }

    public void setParents(List<Parent> parents) {
        this.parents = parents;
        notifyDataSetChanged();
    }

    public void setChildren(List<Child> children) {
        this.children = children;
        notifyDataSetChanged();
    }

    public Parent getParentAt(int position) {
        return parents.get(position);
    }

    public List<Child> getChildren() {
        return children;
    }
    public List<Parent> getParents() {return parents;}

    class ParentHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewNumber;
        private RecyclerView recyclerViewChild;

        public ParentHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title_parent);
            textViewDescription = itemView.findViewById(R.id.text_view_description_parent);
            textViewNumber = itemView.findViewById(R.id.text_view_number_parent);
            recyclerViewChild = itemView.findViewById(R.id.recycler_view_child);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(parents.get(position));
                    }
                }
            });
        }
    }

    //create on Listener for click
    public interface OnItemClickListener {
        void onItemClick(Parent parent);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
