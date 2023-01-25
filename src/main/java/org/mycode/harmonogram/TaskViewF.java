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
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskViewF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskViewF extends Fragment {
    private EditViewF editViewF;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private TextView mStart_date;
    private TextView mFinish_date;
    private TextView mHour;
    private TextView mAuthor;
    private TextView mTaskName;
    private TextView mTaskDescr;
    private Button edit_btn;
    private String mParam1;
    private String mParam2;
    private String id;
    public TaskViewF() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaskViewF.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskViewF newInstance(String param1, String param2) {
        TaskViewF fragment = new TaskViewF();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EditViewF editViewF = new EditViewF();
        View view = inflater.inflate(R.layout.fragment_task_view, container, false);
        mStart_date = (TextView) view.findViewById(R.id.start_day);
        mFinish_date = (TextView) view.findViewById(R.id.finish_day);
        mHour = (TextView) view.findViewById(R.id.hour);
        mAuthor = (TextView) view.findViewById(R.id.author);
        mTaskName = (TextView) view.findViewById(R.id.taskName);
        mTaskDescr = (TextView) view.findViewById(R.id.descriptionTask);
        edit_btn = (Button) view.findViewById(R.id.edit_btn);
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frame, editViewF, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        getParentFragmentManager().setFragmentResultListener("bundleKey_", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                mAuthor.setText(bundle.getString("bundleKey1"));
                mTaskDescr.setText(bundle.getString("bundleKey2"));
                mFinish_date.setText(bundle.getString("bundleKey3"));
                mStart_date.setText(bundle.getString("bundleKey4"));
                mHour.setText(bundle.getString("bundleKey5"));
                mTaskName.setText(bundle.getString("bundleKey6"));
                String result = bundle.getString("bundleKey");
                id = result;
                Bundle resulta = new Bundle();
                resulta.putString("bundleKey", bundle.getString("bundleKey"));
                resulta.putString("bundleKey1", bundle.getString("bundleKey1"));
                resulta.putString("bundleKey2", bundle.getString("bundleKey2"));
                resulta.putString("bundleKey3", bundle.getString("bundleKey3"));
                resulta.putString("bundleKey4", bundle.getString("bundleKey4"));
                resulta.putString("bundleKey5", bundle.getString("bundleKey5"));
                resulta.putString("bundleKey6", bundle.getString("bundleKey6"));
                getParentFragmentManager().setFragmentResult("bundleKey_2", resulta);
            }
        });
        return view;
    }
}