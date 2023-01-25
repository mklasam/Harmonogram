package org.mycode.harmonogram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddF extends Fragment {
    private EditText mStart_date;
    private EditText mFinish_date;
    private EditText mHour;
    private EditText mAuthor;
    private EditText mTaskName;
    private EditText mTaskDescr;
    private Button addTask_btn;
    private ProgressDialog mDialog;
    private FragmentAllTasks fragmentAllTasks;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddF() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddF.
     */
    // TODO: Rename and change types and number of parameters
    public static AddF newInstance(String param1, String param2) {
        AddF fragment = new AddF();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentAllTasks = new FragmentAllTasks();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        AndroidNetworking.initialize(getContext());
        mDialog = new ProgressDialog(getContext());
    }
    private void addTask() {


        addTask_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String startDate = mStart_date.getText().toString().trim();
                String finishDate = mFinish_date.getText().toString().trim();
                String hour = mHour.getText().toString().trim();
                String author = mAuthor.getText().toString().trim();
                String taskName = mTaskName.getText().toString().trim();
                String taskDesc = mTaskDescr.getText().toString().trim();
                if(TextUtils.isEmpty(startDate)) {
                    mStart_date.setError("Wpisz datę początkową!");
                    return;
                }
                if(TextUtils.isEmpty(finishDate)) {
                    mFinish_date.setError("Wpisz datę początkową!");
                    return;
                }

                if(TextUtils.isEmpty(hour)) {
                    mHour.setError("Wpisz godzinę!");
                    return;
                }

                if(TextUtils.isEmpty(author)) {
                    mAuthor.setError("Wpisz autora!");
                    return;
                }

                if(TextUtils.isEmpty(taskName)) {
                    mTaskName.setError("Wpisz nazwę wydarzenia!");
                    return;
                }

                if(TextUtils.isEmpty(taskDesc)) {
                    mTaskDescr.setError("Wpisz opis!");
                    return;
                }
                mDialog.setMessage("Logowanie...");
                mDialog.show();
                AndroidNetworking.post("http://10.0.2.2:5000/add_task")
                        .addHeaders("Content-Type", "application/json")
                        .addBodyParameter("start_day", startDate)
                        .addBodyParameter("finish_day", finishDate)
                        .addBodyParameter("hour", hour)
                        .addBodyParameter("author", author)
                        .addBodyParameter("taskName", taskName)
                        .addBodyParameter("descriptionTask", taskDesc)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                    }
                                }, 50000L);
                                mDialog.dismiss();
                                requireActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.main_frame, fragmentAllTasks, "findThisFragment")
                                        .addToBackStack(null)
                                        .commit();
                                Toast.makeText(getContext(), "Dodałeś wydarzenie!", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onError(ANError error) {
                                Toast.makeText(getContext(), "Wystąpił błąd, nie dodałeś wydarzenia!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        mStart_date = (EditText) view.findViewById(R.id.start_day);
        mFinish_date = (EditText) view.findViewById(R.id.finish_day);
        mHour = (EditText) view.findViewById(R.id.hour);
        mAuthor = (EditText) view.findViewById(R.id.author);
        mTaskName = (EditText) view.findViewById(R.id.taskName);
        mTaskDescr = (EditText) view.findViewById(R.id.descriptionTask);
        addTask_btn = (Button) view.findViewById(R.id.add_task_btn);
        addTask();
        return view;
    }
}