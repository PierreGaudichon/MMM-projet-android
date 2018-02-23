package fr.istic.mmm.sciencefair.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.istic.mmm.sciencefair.R;
import fr.istic.mmm.sciencefair.data.Course;
import fr.istic.mmm.sciencefair.data.Event;

public class AllCourseAdapter extends ListAdapter<Course> {

    public AllCourseAdapter(Context ctx, List<Course> list) {
        super(ctx, list);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Course course = list.get(position);

        ((TextView) holder.view.findViewById(R.id.name)).setText(course.getName());

        List<String> titles = new ArrayList<>();
        for(Event e : course.getEvents()) { titles.add(e.fields.titre_fr); }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ctx, R.layout.fragment_course_all_item_event, titles);
        ((ListView) holder.view.findViewById(R.id.events)).setAdapter(adapter);
    }

    @Override
    public int inflateId() {
        return R.layout.fragment_course_all_item;
    }
}
