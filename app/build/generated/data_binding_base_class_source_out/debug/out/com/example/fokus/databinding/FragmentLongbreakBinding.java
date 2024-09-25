// Generated by view binder compiler. Do not edit!
package com.example.fokus.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

public final class FragmentLongbreakBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final RelativeLayout fragmentContainer;

  @NonNull
  public final LinearLayout linearTimer;

  @NonNull
  public final ImageButton playButton;

  @NonNull
  public final ImageButton restartButton;

  @NonNull
  public final ImageButton sbnxtBtn;

  @NonNull
  public final TextView timerTextView;

  private FragmentLongbreakBinding(@NonNull RelativeLayout rootView,
      @NonNull RelativeLayout fragmentContainer, @NonNull LinearLayout linearTimer,
      @NonNull ImageButton playButton, @NonNull ImageButton restartButton,
      @NonNull ImageButton sbnxtBtn, @NonNull TextView timerTextView) {
    this.rootView = rootView;
    this.fragmentContainer = fragmentContainer;
    this.linearTimer = linearTimer;
    this.playButton = playButton;
    this.restartButton = restartButton;
    this.sbnxtBtn = sbnxtBtn;
    this.timerTextView = timerTextView;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentLongbreakBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentLongbreakBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_longbreak, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentLongbreakBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      RelativeLayout fragmentContainer = (RelativeLayout) rootView;

      id = R.id.linearTimer;
      LinearLayout linearTimer = ViewBindings.findChildViewById(rootView, id);
      if (linearTimer == null) {
        break missingId;
      }

      id = R.id.playButton;
      ImageButton playButton = ViewBindings.findChildViewById(rootView, id);
      if (playButton == null) {
        break missingId;
      }

      id = R.id.restartButton;
      ImageButton restartButton = ViewBindings.findChildViewById(rootView, id);
      if (restartButton == null) {
        break missingId;
      }

      id = R.id.sbnxtBtn;
      ImageButton sbnxtBtn = ViewBindings.findChildViewById(rootView, id);
      if (sbnxtBtn == null) {
        break missingId;
      }

      id = R.id.timerTextView;
      TextView timerTextView = ViewBindings.findChildViewById(rootView, id);
      if (timerTextView == null) {
        break missingId;
      }

      return new FragmentLongbreakBinding((RelativeLayout) rootView, fragmentContainer, linearTimer,
          playButton, restartButton, sbnxtBtn, timerTextView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
