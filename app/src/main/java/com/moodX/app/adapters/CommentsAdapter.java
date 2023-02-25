package com.moodX.app.adapters;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moodX.app.R;
import com.moodX.app.ReplyActivity;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.moodX.app.models.GetCommentsModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.OriginalViewHolder> {

    private List<GetCommentsModel> items;
    private Context ctx;

    public CommentsAdapter(Context context, List<GetCommentsModel> items) {
        this.items = items;
        ctx = context;
    }

    @NonNull
    @Override
    public CommentsAdapter.OriginalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommentsAdapter.OriginalViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_comment, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CommentsAdapter.OriginalViewHolder holder, final int position) {

        final GetCommentsModel obj = items.get(position);

        holder.name.setText(obj.getUserName());
        holder.comment.setText(obj.getComments());

        Picasso.get().load(obj.getUserImgUrl()).placeholder(R.drawable.ic_avatar).into(holder.imageView);

        holder.reply.setOnClickListener(v -> {
            Intent intent = new Intent(ctx, ReplyActivity.class);
            intent.putExtra("commentId", obj.getCommentsId());
            intent.putExtra("videoId", obj.getVideosId());
            ctx.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class OriginalViewHolder extends RecyclerView.ViewHolder {
        private TextView name, comment, reply;
        private CircularImageView imageView;

        public OriginalViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            imageView = v.findViewById(R.id.profile_img);
            comment = v.findViewById(R.id.comments);
            reply = v.findViewById(R.id.tv_replay);
        }
    }

}