// Generated by view binder compiler. Do not edit!
package com.example.fokus.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public final class FragmentTimerBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final RelativeLayout fragmentContainer;

  @NonNull
  public final ImageButton playButton;

  @NonNull
  public final ImageButton restartButton;

  @NonNull
  public final TextView timerTextView;

  @NonNull
  public final ImageButton tnxtBtn;

  private FragmentTimerBinding(@NonNull RelativeLayout rootView,
      @NonNull RelativeLayout fragmentContainer, @NonNull ImageButton playButton,
      @NonNull ImageButton restartButton, @NonNull TextView timerTextView,
      @NonNull ImageButton tnxtBtn) {
    this.rootView = rootView;
    this.fragmentContainer = fragmentContainer;
    this.playButton = playButton;
    this.restartButton = restartButton;
    this.timerTextView = timerTextView;
    this.tnxtBtn = tnxtBtn;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentTimerBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentTimerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_timer, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentTimerBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      RelativeLayout fragmentContainer = (RelativeLayout) rootView;

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

      id = R.id.timerTextView;
      TextView timerTextView = ViewBindings.findChildViewById(rootView, id);
      if (timerTextView == null) {
        break missingId;
      }

      id = R.id.tnxtBtn;
      ImageButton tnxtBtn = ViewBindings.findChildViewById(rootView, id);
      if (tnxtBtn == null) {
        break missingId;
      }

      return new FragmentTimerBinding((RelativeLayout) rootView, fragmentContainer, playButton,
          restartButton, timerTextView, tnxtBtn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}