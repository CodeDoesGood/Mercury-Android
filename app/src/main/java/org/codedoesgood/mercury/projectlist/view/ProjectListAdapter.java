package org.codedoesgood.mercury.projectlist.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.codedoesgood.mercury.R;
import org.codedoesgood.mercury.projectlist.model.Project;
import org.codedoesgood.mercury.projectlist.viewmodel.ProjectListViewModel;

/**
 * Custom adapter for the Project List RecyclerView
 */
class ProjectListAdapter extends RecyclerView.Adapter {

    private ProjectListViewModel projectListViewModel;

    private ProjectListAdapter() { }

    /**
     * Constructor for the ProjectListAdapter.
     * @param viewModel Used to retrieve the array size and objects from the array.
     */
    public ProjectListAdapter(ProjectListViewModel viewModel) {
        projectListViewModel = viewModel;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItemView = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.project_list_row_item, parent, false);
        return new ProjectListViewHolder(rowItemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Project projectRow = projectListViewModel.getProjectAtPosition(position);

        TextView projectTitle = holder.itemView.findViewById(R.id.project_title);
        projectTitle.setText(projectRow.getProjectTitle());
    }

    @Override
    public int getItemCount() {
        return projectListViewModel.getProjectListArraySize();
    }

    /**
     * Implement the ViewHolder for the ProjectListAdapter
     */
    private class ProjectListViewHolder extends RecyclerView.ViewHolder {
        public ProjectListViewHolder(View itemView) {
            super(itemView);
        }
    }

}
