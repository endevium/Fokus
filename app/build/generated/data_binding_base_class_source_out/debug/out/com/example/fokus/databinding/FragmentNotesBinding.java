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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.fokus.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentNotesBinding implements ViewBinding {
  @NonNull
  private final SwipeRefreshLayout rootView;

  @NonNull
  public final ImageButton addnoteBtn;

  @NonNull
  public final LinearLayout notesCardLayout;

  @NonNull
  public final RelativeLayout notesContainer;

  @NonNull
  public final SwipeRefreshLayout swipeRefreshLayout;

  @NonNull
  public final TextView tvNotes;

  @NonNull
  public final TextView tvNotesDesc;

  private FragmentNotesBinding(@NonNull SwipeRefreshLayout rootView,
      @NonNull ImageButton addnoteBtn, @NonNull LinearLayout notesCardLayout,
      @NonNull RelativeLayout notesContainer, @NonNull SwipeRefreshLayout swipeRefreshLayout,
      @NonNull TextView tvNotes, @NonNull TextView tvNotesDesc) {
    this.rootView = rootView;
    this.addnoteBtn = addnoteBtn;
    this.notesCardLayout = notesCardLayout;
    this.notesContainer = notesContainer;
    this.swipeRefreshLayout = swipeRefreshLayout;
    this.tvNotes = tvNotes;
    this.tvNotesDesc = tvNotesDesc;
  }

  @Override
  @NonNull
  public SwipeRefreshLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentNotesBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentNotesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_notes, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentNotesBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.addnoteBtn;
      ImageButton addnoteBtn = ViewBindings.findChildViewById(rootView, id);
      if (addnoteBtn == null) {
        break missingId;
      }

      id = R.id.notesCardLayout;
      LinearLayout notesCardLayout = ViewBindings.findChildViewById(rootView, id);
      if (notesCardLayout == null) {
        break missingId;
      }

      id = R.id.notes_container;
      RelativeLayout notesContainer = ViewBindings.findChildViewById(rootView, id);
      if (notesContainer == null) {
        break missingId;
      }

      SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) rootView;

      id = R.id.tvNotes;
      TextView tvNotes = ViewBindings.findChildViewById(rootView, id);
      if (tvNotes == null) {
        break missingId;
      }

      id = R.id.tvNotesDesc;
      TextView tvNotesDesc = ViewBindings.findChildViewById(rootView, id);
      if (tvNotesDesc == null) {
        break missingId;
      }

      return new FragmentNotesBinding((SwipeRefreshLayout) rootView, addnoteBtn, notesCardLayout,
          notesContainer, swipeRefreshLayout, tvNotes, tvNotesDesc);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}