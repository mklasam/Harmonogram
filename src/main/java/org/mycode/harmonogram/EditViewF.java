package org.mycode.harmonogram;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditViewF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditViewF extends Fragment {
    private FragmentAllTasks fragmentAllTasks;
    private EditText mStart_date;
    private EditText mFinish_date;
    private EditText mHour;
    private EditText mAuthor;
    private EditText mTaskName;
    private EditText mTaskDescr;
    private Button update_btn;
    private String id;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditViewF() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditViewF.
     */
    // TODO: Rename and change types and number of parameters
    public static EditViewF newInstance(String param1, String param2) {
        EditViewF fragment = new EditViewF();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_view, container, false);
        mStart_date = (EditText) view.findViewById(R.id.start_daye);
        mFinish_date = (EditText) view.findViewById(R.id.finish_daye);
        mHour = (EditText) view.findViewById(R.id.houre);
        mAuthor = (EditText) view.findViewById(R.id.authore);
        mTaskName = (EditText) view.findViewById(R.id.taskNamee);
        mTaskDescr = (EditText) view.findViewById(R.id.descriptionTaske);
        update_btn = (Button) view.findViewById(R.id.update_btn);
        getParentFragmentManager().setFragmentResultListener("bundleKey_2", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                id = bundle.getString("bundleKey");
                mAuthor.setText(bundle.getString("bundleKey1"));
                mTaskDescr.setText(bundle.getString("bundleKey2"));
                mFinish_date.setText(bundle.getString("bundleKey3"));
                mStart_date.setText(bundle.getString("bundleKey4"));
                mHour.setText(bundle.getString("bundleKey5"));
                mTaskName.setText(bundle.getString("bundleKey6"));
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidNetworking.post("http://10.0.2.2:5000/update/"+id)
                        .addHeaders("Content-Type", "application/json")
                        .addBodyParameter("start_day", String.valueOf(mStart_date.getText()))
                        .addBodyParameter("finish_day", String.valueOf(mFinish_date.getText()))
                        .addBodyParameter("hour", String.valueOf(mHour.getText()))
                        .addBodyParameter("author", String.valueOf(mAuthor.getText()))
                        .addBodyParameter("taskName", String.valueOf(mTaskName.getText()))
                        .addBodyParameter("descriptionTask", String.valueOf(mTaskDescr.getText()))
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
                                requireActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.main_frame, fragmentAllTasks, "findThisFragment")
                                        .addToBackStack(null)
                                        .commit();
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