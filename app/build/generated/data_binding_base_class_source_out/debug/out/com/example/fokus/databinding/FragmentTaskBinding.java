// Generated by view binder compiler. Do not edit!
package com.example.fokus.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.fokus.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentTaskBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageButton addTask;

  @NonNull
  public final LinearLayout taskCardContainer;

  private FragmentTaskBinding(@NonNull RelativeLayout rootView, @NonNull ImageButton addTask,
      @NonNull LinearLayout taskCardContainer) {
    this.rootView = rootView;
    this.addTask = addTask;
    this.taskCardContainer = taskCardContainer;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentTaskBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentTaskBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_task, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentTaskBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.addTask;
      ImageButton addTask = ViewBindings.findChildViewById(rootView, id);
      if (addTask == null) {
        break missingId;
      }

      id = R.id.taskCardContainer;
      LinearLayout taskCardContainer = ViewBindings.findChildViewById(rootView, id);
      if (taskCardContainer == null) {
        break missingId;
      }

      return new FragmentTaskBinding((RelativeLayout) rootView, addTask, taskCardContainer);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
