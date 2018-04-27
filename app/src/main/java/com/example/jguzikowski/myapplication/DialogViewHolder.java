package com.example.jguzikowski.myapplication;

import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by j.guzikowski on 9/25/17.
 */
public class DialogViewHolder {

    @BindView(R.id.dialog_name)
    EditText dialogName;

    DialogViewHolder(View view) {
        ButterKnife.bind(this, view);
    }
}