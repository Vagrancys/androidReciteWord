package com.tramp.word.adapter.section;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.entity.main.HomeFindInfo;
import com.tramp.word.module.common.WedCommonActivity;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.section.SectionedRecyclerViewAdapter;
import com.tramp.word.widget.section.StatelessSection;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/10
 * version:1.0
 */

public class HomeFindBannerSection extends StatelessSection {
    private Activity mActivity;
    private ArrayList<HomeFindInfo.find.banner> Banners;
    private String Title;
    private ArrayList<String> mImages=new ArrayList<>();
    public HomeFindBannerSection(Activity activity, ArrayList<HomeFindInfo.find.banner> banners,String title){
        super(R.layout.item_empty,R.layout.item_home_find_footer,R.layout.item_home_find_banner);
        mActivity=activity;
        Banners=banners;
        Title=title;
        for (HomeFindInfo.find.banner banner :Banners){
            mImages.add(banner.getBanner_img());
        }
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new SectionedRecyclerViewAdapter.EmptyViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.ItemFindBanner.setImageLoader(new GlideImageLoader());
        mHolder.ItemFindBanner.setImages(mImages);
        mHolder.ItemFindBanner.setDelayTime(2000);
        mHolder.ItemFindBanner.setIndicatorGravity(BannerConfig.CENTER);
        mHolder.ItemFindBanner.setBannerAnimation(Transformer.DepthPage);
        mHolder.ItemFindBanner.start();
        mHolder.ItemFindBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                mActivity.startActivity(new Intent(mActivity,WedCommonActivity.class));
                Utils.StarActivityInAnim(mActivity);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
        return new FooterViewHolder(view);
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
        FooterViewHolder mFooter=(FooterViewHolder) holder;
        mFooter.ItemHomeFindHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(new Intent(mActivity, WedCommonActivity.class));
                Utils.StarActivityInAnim(mActivity);
            }
        });
        mFooter.ItemHomeFindTitle.setText(Title);
        mFooter.ItemHomeFindTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(new Intent(mActivity, WedCommonActivity.class));
                Utils.StarActivityInAnim(mActivity);
            }
        });
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_find_banner)
        Banner ItemFindBanner;
        public ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_home_find_hot)
        TextView ItemHomeFindHot;
        @BindView(R.id.item_home_find_title)
        TextView ItemHomeFindTitle;
        public FooterViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class GlideImageLoader extends ImageLoader{
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).placeholder(R.drawable.user_avater).into(imageView);
        }
    }
}
