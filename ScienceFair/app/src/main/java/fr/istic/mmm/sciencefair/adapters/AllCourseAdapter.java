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
        System.out.println("AllCourseAdapter#onBindViewHolder");
        Course course = list.get(position);
        System.out.println("position = " + position);
        System.out.println(course);
        System.out.println("name = " + course.getName());
        System.out.println("events.length = " + course.getEvents().size());
        System.out.println(holder.view.findViewById(R.id.name));
        ((TextView) holder.view.findViewById(R.id.name)).setText(course.getName());

        String titles = "";
        for(Event e : course.getEvents()) { titles += e.fields.titre_fr + "\n"; }
        ((TextView) holder.view.findViewById(R.id.events)).setText(titles);
    }

    @Override
    public int inflateId() {
        return R.layout.fragment_course_all_item;
    }
}
