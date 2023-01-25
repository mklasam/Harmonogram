package org.mycode.harmonogram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAllTasks#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAllTasks extends Fragment {
    private ProgressDialog mDialog;
    private Gson gson;
    private ListView mListView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private Button refresh;
    private Button add;
    private TaskViewF taskViewF;
    private AddF addF;
    public FragmentAllTasks() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAllTasks.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAllTasks newInstance(String param1, String param2) {
        FragmentAllTasks fragment = new FragmentAllTasks();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskViewF = new TaskViewF();
        mDialog = new ProgressDialog(getActivity());
        gson = new Gson();
        mDialog.setMessage("Pobieranie danych...");
        mDialog.show();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_tasks, container, false);
        addF = new AddF();
        mListView = (ListView) view.findViewById(R.id.listView);
        add = (Button) view.findViewById(R.id.add_btn_);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frame, addF, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        AndroidNetworking.get("http://10.0.2.2:5000/crud_panel")
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Accept", "application/json")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                            }
                        }, 50000L);
                        ParseTasks gsonparse = gson.fromJson(String.valueOf(response), ParseTasks.class);
                        List<ParseTask> gsonParseAllMissions = gsonparse.PK;
                        ArrayList<ParseTask> gss = (ArrayList<ParseTask>) gsonParseAllMissions;
                        ParseTaskAdapter adapter = new ParseTaskAdapter(getActivity(), R.layout.el_adapter, gss);
                        mListView.setAdapter(adapter);
                        mDialog.dismiss();

                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(getActivity(),"Wystąpił błąd!",Toast.LENGTH_SHORT).show();
                    }
                });
        refresh = (Button) view.findViewById(R.id.refresh_btn);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseTask textView = (ParseTask) mListView.getItemAtPosition(position);
                String text = textView.id;
                String text1 = textView.author;
                String text2 = textView.descriptionTask;
                String text3 = textView.finish_day;
                String text4 = textView.start_day;
                String text5 = textView.hour;
                String text6 = textView.taskName;
                Bundle result = new Bundle();
                result.putString("bundleKey", text);
                result.putString("bundleKey1", text1);
                result.putString("bundleKey2", text2);
                result.putString("bundleKey3", text3);
                result.putString("bundleKey4", text4);
                result.putString("bundleKey5", text5);
                result.putString("bundleKey6", text6);
                getParentFragmentManager().setFragmentResult("bundleKey_", result);
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frame, taskViewF, "findThisFragment")
                        .addToBackStack(null)
                        .commit();

            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidNetworking.get("http://10.0.2.2:5000/crud_panel")
                        .addHeaders("Content-Type", "application/json")
                        .addHeaders("Accept", "application/json")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                    }
                                }, 50000L);
                                Toast.makeText(getActivity(),"Odświeżyłeś listę!",Toast.LENGTH_SHORT).show();
                                ParseTasks gsonparse = gson.fromJson(String.valueOf(response), ParseTasks.class);
                                List<ParseTask> gsonParseAllMissions = gsonparse.PK;
                                ArrayList<ParseTask> gss = (ArrayList<ParseTask>) gsonParseAllMissions;
                                ParseTaskAdapter adapter = new ParseTaskAdapter(getActivity(), R.layout.el_adapter, gss);
                                mListView.setAdapter(adapter);
                                mDialog.dismiss();
                            }
                            @Override
                            public void onError(ANError error) {
                                Toast.makeText(getActivity(),"Wystąpił błąd!",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        return view;
    }
}