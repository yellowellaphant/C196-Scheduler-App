package com.example.c196schedulingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.c196schedulingapp.Entity.Course;
import com.example.c196schedulingapp.R;
import com.example.c196schedulingapp.UI.CourseDetails;
import com.example.c196schedulingapp.Helper.ParseDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CourseViewAdapter extends RecyclerView.Adapter<CourseViewAdapter.CourseViewHolder> {

    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflator;

    class CourseViewHolder extends RecyclerView.ViewHolder {

    private final TextView listItemView;
    private final TextView listItemView1;
    private final TextView listItemView2;

    private CourseViewHolder(View itemView) {
        super(itemView);
        listItemView = itemView.findViewById(R.id.textView);
        listItemView1 = itemView.findViewById(R.id.textView1);
        listItemView2 = itemView.findViewById(R.id.textView2);
        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                final Course current = mCourses.get(position);
                Intent intent = new Intent(context, CourseDetails.class);
                intent.putExtra("courseID" ,current.getCourseID());
                intent.putExtra("courseTitle", current.getCourseTitle());
                intent.putExtra("courseStart", ParseDate.dateParseString(current.getStartDate()));
                intent.putExtra("courseEnd", ParseDate.dateParseString(current.getEndDate()));
                intent.putExtra("termID", current.getTermID());
                intent.putExtra("instructorName", current.getInstructorName());
                intent.putExtra("instructorEmail", current.getInstructorEmail());
                intent.putExtra("instructorPhone", current.getInstructorPhone());
                intent.putExtra("status" ,current.getStatus());
                intent.putExtra("optionalNotes",current.getOptionalNotes());
                context.startActivity(intent);

            }
        });
    }
}
    @NonNull
    @Override
    public CourseViewAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflator.inflate(R.layout.list_items, parent, false);
        return new CourseViewAdapter.CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewAdapter.CourseViewHolder holder, int position) {
        if(mCourses!=null){
            Course current=mCourses.get(position);
            holder.listItemView.setText((current.getCourseTitle()));
            holder.listItemView1.setText(ParseDate.dateParseString(current.getStartDate()));
            holder.listItemView2.setText(ParseDate.dateParseString(current.getEndDate()));
        }
        else{
            holder.listItemView.setText("No Thing Name");
            holder.listItemView1.setText("No Thing ID");
            holder.listItemView2.setText("No Thing ID");
        }
    }

    public CourseViewAdapter(Context context){
        mInflator=LayoutInflater.from(context);
        this.context=context;
    }


    public void setCourse(List<Course> courses){
        mCourses=courses;
        notifyDataSetChanged();
    }

    public String dateFormat(String date) throws ParseException {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        return sdf.format(date);
    }

    @Override
    public int getItemCount() {
        if (mCourses != null)
            return mCourses.size();
        else return 0;
    }
}
