package com.kickstarter.ui.viewholders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kickstarter.KSApplication;
import com.kickstarter.R;
import com.kickstarter.libs.KSString;
import com.kickstarter.libs.utils.ObjectUtils;
import com.kickstarter.models.Photo;
import com.kickstarter.models.Project;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

public final class ProjectContextViewHolder extends KSViewHolder {
  private Project project;
  private Context context;
  private final Delegate delegate;

  protected @Bind(R.id.project_context_image_view) ImageView projectContextImageView;
  protected @Bind(R.id.project_context_project_name) TextView projectNameTextView;
  protected @Bind(R.id.project_context_creator_name) TextView creatorNameTextView;
  protected @BindString(R.string.project_creator_by_creator) String projectCreatorByCreatorString;

  protected @Inject KSString ksString;

  public interface Delegate {
    void projectContextClicked(ProjectContextViewHolder viewHolder);
  }

  public ProjectContextViewHolder(final @NonNull View view, final @NonNull Delegate delegate) {
    super(view);
    this.delegate = delegate;
    this.context = view.getContext();
    ButterKnife.bind(this, view);
    ((KSApplication) this.context.getApplicationContext()).component().inject(this);
  }

  @Override
  public void bindData(final @Nullable Object data) throws Exception {
    this.project = ObjectUtils.requireNonNull((Project) data, Project.class);
  }

  public void onBind() {
    final Photo photo = this.project.photo();

    if (photo != null) {
      this.projectContextImageView.setVisibility(View.VISIBLE);
      Picasso.with(this.context).load(photo.full()).into(this.projectContextImageView);
    } else {
      this.projectContextImageView.setVisibility(View.INVISIBLE);
    }

    this.projectNameTextView.setText(this.project.name());
    this.creatorNameTextView.setText(this.ksString.format(
      this.projectCreatorByCreatorString,
      "creator_name",
      this.project.creator().name()
    ));
  }

  @Override
  public void onClick(final @NonNull View view) {
    this.delegate.projectContextClicked(this);
  }
}
