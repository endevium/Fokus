// Generated by view binder compiler. Do not edit!
package com.example.fokus.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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

public final class ActivityLoginBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView fpassL;

  @NonNull
  public final ImageView googleBtn;

  @NonNull
  public final Button lBtn;

  @NonNull
  public final EditText lEmail;

  @NonNull
  public final EditText lPass;

  @NonNull
  public final RelativeLayout main;

  @NonNull
  public final CheckBox rMeCb;

  @NonNull
  public final TextView signupD;

  private ActivityLoginBinding(@NonNull RelativeLayout rootView, @NonNull TextView fpassL,
      @NonNull ImageView googleBtn, @NonNull Button lBtn, @NonNull EditText lEmail,
      @NonNull EditText lPass, @NonNull RelativeLayout main, @NonNull CheckBox rMeCb,
      @NonNull TextView signupD) {
    this.rootView = rootView;
    this.fpassL = fpassL;
    this.googleBtn = googleBtn;
    this.lBtn = lBtn;
    this.lEmail = lEmail;
    this.lPass = lPass;
    this.main = main;
    this.rMeCb = rMeCb;
    this.signupD = signupD;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_login, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLoginBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.fpassL;
      TextView fpassL = ViewBindings.findChildViewById(rootView, id);
      if (fpassL == null) {
        break missingId;
      }

      id = R.id.googleBtn;
      ImageView googleBtn = ViewBindings.findChildViewById(rootView, id);
      if (googleBtn == null) {
        break missingId;
      }

      id = R.id.lBtn;
      Button lBtn = ViewBindings.findChildViewById(rootView, id);
      if (lBtn == null) {
        break missingId;
      }

      id = R.id.lEmail;
      EditText lEmail = ViewBindings.findChildViewById(rootView, id);
      if (lEmail == null) {
        break missingId;
      }

      id = R.id.lPass;
      EditText lPass = ViewBindings.findChildViewById(rootView, id);
      if (lPass == null) {
        break missingId;
      }

      RelativeLayout main = (RelativeLayout) rootView;

      id = R.id.rMeCb;
      CheckBox rMeCb = ViewBindings.findChildViewById(rootView, id);
      if (rMeCb == null) {
        break missingId;
      }

      id = R.id.signupD;
      TextView signupD = ViewBindings.findChildViewById(rootView, id);
      if (signupD == null) {
        break missingId;
      }

      return new ActivityLoginBinding((RelativeLayout) rootView, fpassL, googleBtn, lBtn, lEmail,
          lPass, main, rMeCb, signupD);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
