package com.tramp.word.api.api;

import com.tramp.word.entity.DefaultInfo;
import com.tramp.word.entity.task.TaskInfo;
import com.tramp.word.entity.task.TaskListInfo;
import com.tramp.word.entity.task.TaskStarInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/02
 * version:1.0
 */

public interface TaskService {
    @GET("task.html")
    Call<TaskInfo> getTaskInfo(@Query("user_id") int user_id);

    @GET("task_star.html")
    Call<TaskStarInfo> getTaskStarInfo(@Query("user_id") int user_id, @Query("task_id") int task_id, @Query("task_class") int task_class);

    @GET("task_list.html")
    Call<TaskListInfo> getTaskListInfo(@Query("user_id") int user_id);

    @GET("task_delete.html")
    Call<DefaultInfo> getTaskDeleteInfo(@Query("task_id") int task_id);

    @GET("task_update.html")
    Call<DefaultInfo> getTaskUpdateInfo(@Query("task_id") int task_id,@Query("star_number") int star,@Query("money_number") int money);

    @GET("task_insert.html")
    Call<DefaultInfo> getTaskInsertInfo(@Query("task_id") int task_id);
}
