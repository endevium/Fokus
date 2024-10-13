// Generated by view binder compiler. Do not edit!
package com.example.fokus.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.fokus.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentSettingsBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final BottomNavigationView bottomNavigation;

  @NonNull
  public final LinearLayout changeemailBtn;

  @NonNull
  public final LinearLayout changelbtimerBtn;

  @NonNull
  public final LinearLayout changepassBtn;

  @NonNull
  public final LinearLayout changepfpBtn;

  @NonNull
  public final LinearLayout changeptimerBtn;

  @NonNull
  public final LinearLayout changesbtimerBtn;

  @NonNull
  public final LinearLayout changeuserBtn;

  private FragmentSettingsBinding(@NonNull RelativeLayout rootView,
      @NonNull BottomNavigationView bottomNavigation, @NonNull LinearLayout changeemailBtn,
      @NonNull LinearLayout changelbtimerBtn, @NonNull LinearLayout changepassBtn,
      @NonNull LinearLayout changepfpBtn, @NonNull LinearLayout changeptimerBtn,
      @NonNull LinearLayout changesbtimerBtn, @NonNull LinearLayout changeuserBtn) {
    this.rootView = rootView;
    this.bottomNavigation = bottomNavigation;
    this.changeemailBtn = changeemailBtn;
    this.changelbtimerBtn = changelbtimerBtn;
    this.changepassBtn = changepassBtn;
    this.changepfpBtn = changepfpBtn;
    this.changeptimerBtn = changeptimerBtn;
    this.changesbtimerBtn = changesbtimerBtn;
    this.changeuserBtn = changeuserBtn;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentSettingsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentSettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_settings, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentSettingsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bottomNavigation;
      BottomNavigationView bottomNavigation = ViewBindings.findChildViewById(rootView, id);
      if (bottomNavigation == null) {
        break missingId;
      }

      id = R.id.changeemailBtn;
      LinearLayout changeemailBtn = ViewBindings.findChildViewById(rootView, id);
      if (changeemailBtn == null) {
        break missingId;
      }

      id = R.id.changelbtimerBtn;
      LinearLayout changelbtimerBtn = ViewBindings.findChildViewById(rootView, id);
      if (changelbtimerBtn == null) {
        break missingId;
      }

      id = R.id.changepassBtn;
      LinearLayout changepassBtn = ViewBindings.findChildViewById(rootView, id);
      if (changepassBtn == null) {
        break missingId;
      }

      id = R.id.changepfpBtn;
      LinearLayout changepfpBtn = ViewBindings.findChildViewById(rootView, id);
      if (changepfpBtn == null) {
        break missingId;
      }

      id = R.id.changeptimerBtn;
      LinearLayout changeptimerBtn = ViewBindings.findChildViewById(rootView, id);
      if (changeptimerBtn == null) {
        break missingId;
      }

      id = R.id.changesbtimerBtn;
      LinearLayout changesbtimerBtn = ViewBindings.findChildViewById(rootView, id);
      if (changesbtimerBtn == null) {
        break missingId;
      }

      id = R.id.changeuserBtn;
      LinearLayout changeuserBtn = ViewBindings.findChildViewById(rootView, id);
      if (changeuserBtn == null) {
        break missingId;
      }

      return new FragmentSettingsBinding((RelativeLayout) rootView, bottomNavigation,
          changeemailBtn, changelbtimerBtn, changepassBtn, changepfpBtn, changeptimerBtn,
          changesbtimerBtn, changeuserBtn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}