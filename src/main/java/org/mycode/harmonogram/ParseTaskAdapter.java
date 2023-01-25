package org.mycode.harmonogram;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ParseTaskAdapter extends ArrayAdapter<ParseTask> {
    private static final String TAG = "TaskAdapter";
    private TextView dlt;
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    private static class ViewHolder {
        TextView taskName;
    }

    public ParseTaskAdapter(Context context, int resource, ArrayList<ParseTask> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    private void delete(int id) {
        AndroidNetworking.post("http://10.0.2.2:5000/delete/"+id)
                .addHeaders("Content-Type", "application/json")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                            }
                        }, 50000L);
                        Toast.makeText(getContext(), "Usunąłeś wydarzenie!", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(getContext(), "Wystąpił błąd!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String startDay = getItem(position).start_day;
        String finishDay = getItem(position).finish_day;
        String hour = getItem(position).hour;
        String author = getItem(position).author;
        String taskName = getItem(position).taskName;
        String taskDescr = getItem(position).descriptionTask;
        int id = Integer.parseInt(getItem(position).id);
        ParseTaskClass parseTaskClass = new ParseTaskClass(startDay,finishDay,hour,author,taskName,taskDescr, id);
        final View result;
        ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.taskName = (TextView) convertView.findViewById(R.id.nazwa_zlecenia);
            result = convertView;
            convertView.setTag(holder);
            dlt = (TextView) convertView.findViewById(R.id.delete_btn);
            dlt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete(id);
                }
            });
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
            dlt = (TextView) convertView.findViewById(R.id.delete_btn);
            dlt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete(id);
                }
            });
        }
        lastPosition = position;
        holder.taskName.setText(parseTaskClass.getTaskName());
        return convertView;
    }

}