// Generated by view binder compiler. Do not edit!
package com.example.fokus.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public final class ActivitySignupBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageView googleBtn;

  @NonNull
  public final TextView loginD;

  @NonNull
  public final RelativeLayout main;

  @NonNull
  public final Button sBtn;

  @NonNull
  public final EditText sEmail;

  @NonNull
  public final EditText sPass;

  @NonNull
  public final EditText sUser;

  private ActivitySignupBinding(@NonNull RelativeLayout rootView, @NonNull ImageView googleBtn,
      @NonNull TextView loginD, @NonNull RelativeLayout main, @NonNull Button sBtn,
      @NonNull EditText sEmail, @NonNull EditText sPass, @NonNull EditText sUser) {
    this.rootView = rootView;
    this.googleBtn = googleBtn;
    this.loginD = loginD;
    this.main = main;
    this.sBtn = sBtn;
    this.sEmail = sEmail;
    this.sPass = sPass;
    this.sUser = sUser;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySignupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySignupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_signup, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySignupBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.googleBtn;
      ImageView googleBtn = ViewBindings.findChildViewById(rootView, id);
      if (googleBtn == null) {
        break missingId;
      }

      id = R.id.loginD;
      TextView loginD = ViewBindings.findChildViewById(rootView, id);
      if (loginD == null) {
        break missingId;
      }

      RelativeLayout main = (RelativeLayout) rootView;

      id = R.id.sBtn;
      Button sBtn = ViewBindings.findChildViewById(rootView, id);
      if (sBtn == null) {
        break missingId;
      }

      id = R.id.sEmail;
      EditText sEmail = ViewBindings.findChildViewById(rootView, id);
      if (sEmail == null) {
        break missingId;
      }

      id = R.id.sPass;
      EditText sPass = ViewBindings.findChildViewById(rootView, id);
      if (sPass == null) {
        break missingId;
      }

      id = R.id.sUser;
      EditText sUser = ViewBindings.findChildViewById(rootView, id);
      if (sUser == null) {
        break missingId;
      }

      return new ActivitySignupBinding((RelativeLayout) rootView, googleBtn, loginD, main, sBtn,
          sEmail, sPass, sUser);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
