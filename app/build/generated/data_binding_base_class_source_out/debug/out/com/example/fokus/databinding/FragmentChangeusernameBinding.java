// Generated by view binder compiler. Do not edit!
package com.example.fokus.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.fokus.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentChangeusernameBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageButton backBtn;

  @NonNull
  public final TextView cancelBtn;

  @NonNull
  public final EditText etUsername;

  @NonNull
  public final TextView saveBtn;

  private FragmentChangeusernameBinding(@NonNull RelativeLayout rootView,
      @NonNull ImageButton backBtn, @NonNull TextView cancelBtn, @NonNull EditText etUsername,
      @NonNull TextView saveBtn) {
    this.rootView = rootView;
    this.backBtn = backBtn;
    this.cancelBtn = cancelBtn;
    this.etUsername = etUsername;
    this.saveBtn = saveBtn;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentChangeusernameBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentChangeusernameBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_changeusername, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentChangeusernameBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.backBtn;
      ImageButton backBtn = ViewBindings.findChildViewById(rootView, id);
      if (backBtn == null) {
        break missingId;
      }

      id = R.id.cancelBtn;
      TextView cancelBtn = ViewBindings.findChildViewById(rootView, id);
      if (cancelBtn == null) {
        break missingId;
      }

      id = R.id.etUsername;
      EditText etUsername = ViewBindings.findChildViewById(rootView, id);
      if (etUsername == null) {
        break missingId;
      }

      id = R.id.saveBtn;
      TextView saveBtn = ViewBindings.findChildViewById(rootView, id);
      if (saveBtn == null) {
        break missingId;
      }

      return new FragmentChangeusernameBinding((RelativeLayout) rootView, backBtn, cancelBtn,
          etUsername, saveBtn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}