package pl.edu.pb.mainactivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Date;
import java.util.UUID;

public class TaskFragment extends Fragment {

    private static final String ARG_TASK_ID = "task_id";

    private Task task;

    private EditText nameField;
    private Button dateButton;
    private CheckBox doneCheckbox;

    public static TaskFragment newInstance(UUID taskId) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_TASK_ID, taskId);

        TaskFragment taskFragment = new TaskFragment();
        taskFragment.setArguments(bundle);
        return taskFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        UUID taskId = (UUID) getArguments().getSerializable(ARG_TASK_ID);
        task = TaskStorage.getInstance().getTask(taskId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        nameField = (EditText) view.findViewById(R.id.task_name);
        nameField.setText(task.getName());
        nameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                task.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dateButton = (Button) view.findViewById(R.id.task_date);
        dateButton.setText(formatDate(task.getDate()));
        dateButton.setEnabled(false);

        doneCheckbox = (CheckBox) view.findViewById(R.id.task_done);
        doneCheckbox.setChecked(task.isDone());
        doneCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setDone(isChecked);
        });
        return view;
    }

    private String formatDate(Date date) {
        return DateFormat.getLongDateFormat(getActivity()).format(date);
    }
}
