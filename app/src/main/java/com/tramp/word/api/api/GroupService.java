package com.tramp.word.api.api;

import com.tramp.word.entity.DefaultGroupCreateInfo;
import com.tramp.word.entity.DefaultInfo;
import com.tramp.word.entity.DefaultSummaryInfo;
import com.tramp.word.entity.group.GroupAvatarInfo;
import com.tramp.word.entity.group.GroupBoardInfo;
import com.tramp.word.entity.group.GroupContextInfo;
import com.tramp.word.entity.group.GroupCreateInfo;
import com.tramp.word.entity.group.GroupDayInfo;
import com.tramp.word.entity.group.GroupDetailsInfo;
import com.tramp.word.entity.group.GroupFinialInfo;
import com.tramp.word.entity.group.GroupLevelInfo;
import com.tramp.word.entity.group.GroupListInfo;
import com.tramp.word.entity.group.GroupMainInfo;
import com.tramp.word.entity.group.GroupMainRankInfo;
import com.tramp.word.entity.group.GroupMedalInfo;
import com.tramp.word.entity.group.GroupMemberInfo;
import com.tramp.word.entity.group.GroupRankInfo;
import com.tramp.word.entity.group.GroupSettingInfo;
import com.tramp.word.entity.group.GroupSummaryInfo;
import com.tramp.word.entity.group.MedalDetailsInfo;
import com.tramp.word.entity.group.MemberStatusInfo;
import com.tramp.word.entity.group.SearchListInfo;
import com.tramp.word.entity.group.SearchTagInfo;
import com.tramp.word.entity.group.BoardSearchInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/04
 * version:1.0
 */

public interface GroupService {
    @GET("group_context.html")
    Call<GroupContextInfo> getGroupContextInfo(@Query("group_class") int group_class,
                                               @Query("user_id") int user_id);

    @GET("group_rank.html")
    Call<GroupRankInfo> getGroupRankInfo(@Query("group_status") int group_status,
                                         @Query("group_star") int group_star,
                                         @Query("group_number") int group_number);

    @GET("group_finial.html")
    Call<GroupFinialInfo> getGroupFinialInfo(@Query("group_status") int group_status,
                                             @Query("group_class") int group_class);

    @GET("group_details.html")
    Call<GroupDetailsInfo> getGroupDetailsInfo(@Query("group_id") int group_id,@Query("user_id") int user_id);

    @GET("group_medal.html")
    Call<GroupMedalInfo> getGroupMedalInfo(@Query("group_id") int group_id,
                                           @Query("medal_status") int medal_status);

    @GET("group_medal_details.html")
    Call<MedalDetailsInfo> getMedalDetailsInfo(@Query("medal_id") int medal_id,@Query("group_id") int group_id);

    @GET("group_search_tag.html")
    Call<SearchTagInfo> getSearchTagInfo();

    @GET("group_search_list.html")
    Call<SearchListInfo> getSearchListInfo(@Query("search_name") String search_name);

    @GET("group_main.html")
    Call<GroupMainInfo> getGroupMainInfo(@Query("group_id") int group_id,@Query("user_id") int user_id,
                                         @Query("add_status") int add_status);

    //
    @GET("group_main_rank.html")
    Call<GroupMainRankInfo> getGroupMainRankInfo(@Query("group_id") int group_id,@Query("user_id") int user_id);

    @GET("group_board.html")
    Call<GroupBoardInfo> getGroupBoardInfo(@Query("group_id") int group_id,@Query("user_id") int user_id);

    //
    @GET("group_list.html")
    Call<GroupListInfo> getGroupListInfo(@Query("group_id") int group_id);

    //
    @GET("group_search.html")
    Call<BoardSearchInfo> getBoardSearchInfo(@Query("group_id") int group_id,
                                             @Query("user_name") String user_name);

    //
    @FormUrlEncoded
    @POST("board_send_form.html")
    Call<DefaultInfo> getSendFormInfo(@Field("user_id") int user_id,
                                       @Field("a_id") int a_id,
                                       @Field("group_id") int group_id,
                                       @Field("board_text") String board_text);

    @GET("board_praise.html")
    Call<DefaultInfo> getBoardPraiseInfo(@Query("board_id") int board_id);

    @GET("group_day.html")
    Call<GroupDayInfo> getGroupDayInfo(@Query("group_id") int group_id,
                                       @Query("user_id") int user_id);

    @GET("group_level.html")
    Call<GroupLevelInfo> getGroupLevelInfo(@Query("group_id") int group_id);

    @GET("group_member.html")
    Call<GroupMemberInfo> getGroupMemberInfo(@Query("group_id") int group_id,
                                             @Query("manage_status") int manage_status,
                                             @Query("user_id") int user_id);

    @GET("member_status.html")
    Call<MemberStatusInfo> getMemberStatusInfo(@Query("group_id") int group_id,
                                               @Query("member_status") int member_status);

    @GET("group_out.html")
    Call<DefaultInfo> getGroupOutInfo(@Query("user_id") int user_id,
                                      @Query("group_id") int group_id);

    @GET("group_dismiss.html")
    Call<DefaultInfo> getGroupDismissInfo(@Query("user_id") int user_id,
                                          @Query("group_id") int group_id);

    @GET("group_avatar.html")
    Call<GroupAvatarInfo> getGroupAvatarInfo();

    @FormUrlEncoded
    @POST("group_create.html")
    Call<GroupCreateInfo> getGroupCreateInfo(@Body DefaultGroupCreateInfo create);

    @GET("group_star.html")
    Call<DefaultInfo> getGroupStarInfo(@Query("group_id") int group_id,
                                       @Query("group_star") int group_star);

    @GET("group_delete.html")
    Call<DefaultInfo> getGroupDeleteInfo(@Query("user_id") int user_id);

    @GET("group_summary.html")
    Call<GroupSummaryInfo> getGroupSummaryInfo(@Query("user_id") int user_id,
                                               @Query("group_id") int group_id);

    @FormUrlEncoded
    @POST("summary_add.html")
    Call<DefaultInfo> getSummaryAddInfo(@Body DefaultSummaryInfo create);

    @GET("group_setting.html")
    Call<GroupSettingInfo> getGroupSettingInfo(@Query("group_id") int group_id);

    @GET("setting_add.html")
    Call<DefaultInfo> getSettingAddInfo(@Query("group_id") int group_id,
                                        @Query("group_search") int group_search,
                                        @Query("group_add'") int group_add);
    @GET("group_add.html")
    Call<DefaultInfo> getGroupAddInfo(@Query("group_id") int group_id,
                                      @Query("user_id") int user_id);

    @GET("group_add_text.html")
    Call<DefaultInfo> getAddTextInfo(@Query("group_id") int group_id,
                                     @Query("add_text") String add_text,
                                     @Query("user_id") int user_id);
}














