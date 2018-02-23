package fr.istic.mmm.sciencefair.adapters;

import android.content.Context;
import android.widget.TextView;

import java.util.List;

import fr.istic.mmm.sciencefair.MainActivity;
import fr.istic.mmm.sciencefair.R;
import fr.istic.mmm.sciencefair.data.Event;

public class CourseAdapter extends ListAdapter {

    public CourseAdapter(Context ctx, List<Event> list) {
        super(ctx, list);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = list.get(position);
        ((TextView) holder.view.findViewById(R.id.titre)).setText(event.fields.titre_fr);
        holder.view.setOnClickListener(l -> {
            ((MainActivity) ctx).removeFromCourse(position);
        });
    }

    @Override
    public int inflateId() {
        return R.layout.fragment_course_item;
    }
}
