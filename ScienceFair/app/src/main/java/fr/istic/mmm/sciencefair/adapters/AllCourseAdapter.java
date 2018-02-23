package fr.istic.mmm.sciencefair.adapters;

import android.content.Context;
import android.widget.TextView;

import java.util.List;

import fr.istic.mmm.sciencefair.R;
import fr.istic.mmm.sciencefair.data.Course;

public class AllCourseAdapter extends ListAdapter<Course> {

    public AllCourseAdapter(Context ctx, List<Course> list) {
        super(ctx, list);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Course course = list.get(position);
        ((TextView) holder.view.findViewById(R.id.name)).setText(course.getName());
    }

    @Override
    public int inflateId() {
        return R.layout.fragment_course_my_item;
    }
}
