package com.tramp.word.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.api.Retrofits;
import com.tramp.word.entity.DefaultInfo;
import com.tramp.word.entity.group.GroupBoardInfo;
import com.tramp.word.module.user.FriendDetailsActivity;
import com.tramp.word.port.GroupMainInterFace;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/21
 * version:1.0
 */

public class GroupBoardViewAdapter extends AbsRecyclerViewAdapter{
    private Activity mActivity;
    private ArrayList<GroupBoardInfo.Board> Boards;
    private GroupMainInterFace mFace;
    public GroupBoardViewAdapter(RecyclerView recyclerView, Activity activity, ArrayList<GroupBoardInfo.Board> boards){
        super(recyclerView);
        mActivity=activity;
        Boards=boards;
        mFace=(GroupMainInterFace) activity;
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.BoardImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendDetailsActivity.launch(mActivity,Boards.get(position).getBoard_member());
            }
        });
        Glide.with(getContext())
                .load(Boards.get(position).getBoard_img())
                .placeholder(R.drawable.user_avater)
                .into(mHolder.BoardImg);
        mHolder.BoardPraise.setText(String.valueOf(Boards.get(position).getBoard_praise()));
        mHolder.BoardText.setText(Boards.get(position).getBoard_text());
        mHolder.BoardName.setText(Boards.get(position).getBoard_name());
        mHolder.BoardPraiseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofits.getGroupAPI()
                        .getBoardPraiseInfo(Boards.get(position).getBoard_id())
                        .enqueue(new Callback<DefaultInfo>() {
                            @Override
                            public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                                if(response.body().getCode()==200){
                                    mHolder.BoardPraise.setText(String.valueOf(Boards.get(position).getBoard_praise()+1));
                                }
                            }

                            @Override
                            public void onFailure(Call<DefaultInfo> call, Throwable t) {

                            }
                        });
            }
        });
        mHolder.BoardA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFace.AMember(Boards.get(position).getBoard_name(),Boards.get(position).getBoard_member());
            }
        });
        super.onBindViewHolder(holder, position);
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_group_board,parent,false));
    }

    @Override
    public int getItemCount() {
        return Boards.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        ImageView BoardImg;
        TextView BoardName;
        TextView BoardTime;
        TextView BoardPraise;
        ImageView BoardPraiseImg;
        TextView BoardText;
        TextView BoardA;
        public ItemViewHolder(View itemView){
            super(itemView);
            BoardImg=$(R.id.group_board_img);
            BoardName=$(R.id.group_board_name);
            BoardTime=$(R.id.group_board_time);
            BoardPraise=$(R.id.group_board_praise_num);
            BoardPraiseImg=$(R.id.group_board_praise);
            BoardText=$(R.id.group_board_text);
            BoardA=$(R.id.group_board_a);
        }
    }
}
