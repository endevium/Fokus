// Generated by view binder compiler. Do not edit!
package com.example.fokus.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.fokus.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySecondBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageButton ibckBtn;

  @NonNull
  public final ImageButton inxtBtn;

  @NonNull
  public final RelativeLayout main;

  private ActivitySecondBinding(@NonNull RelativeLayout rootView, @NonNull ImageButton ibckBtn,
      @NonNull ImageButton inxtBtn, @NonNull RelativeLayout main) {
    this.rootView = rootView;
    this.ibckBtn = ibckBtn;
    this.inxtBtn = inxtBtn;
    this.main = main;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySecondBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySecondBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_second, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySecondBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.ibckBtn;
      ImageButton ibckBtn = ViewBindings.findChildViewById(rootView, id);
      if (ibckBtn == null) {
        break missingId;
      }

      id = R.id.inxtBtn;
      ImageButton inxtBtn = ViewBindings.findChildViewById(rootView, id);
      if (inxtBtn == null) {
        break missingId;
      }

      RelativeLayout main = (RelativeLayout) rootView;

      return new ActivitySecondBinding((RelativeLayout) rootView, ibckBtn, inxtBtn, main);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}