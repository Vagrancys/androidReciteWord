package com.tramp.word.api;

import com.tramp.word.api.api.BookService;
import com.tramp.word.api.api.DownService;
import com.tramp.word.api.api.FriendService;
import com.tramp.word.api.api.GroupService;
import com.tramp.word.api.api.MainService;
import com.tramp.word.api.api.PkService;
import com.tramp.word.api.api.TaskService;
import com.tramp.word.api.api.UserService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/22
 * version:1.0
 */

public class Retrofits {
    private static final String Url="http://www.yiyuzhou.com/";

    public static UserService getUserAPI(){
        return createApi(UserService.class,Url);
    }

    public static BookService getBookAPI(){ return createApi(BookService.class,Url);}

    public static FriendService getFriendAPI(){ return createApi(FriendService.class,Url);}

    public static TaskService getTaskAPI(){ return createApi(TaskService.class,Url);}

    public static PkService getPkAPI(){ return createApi(PkService.class,Url);}

    public static GroupService getGroupAPI(){ return createApi(GroupService.class,Url);}

    public static MainService getMainAPI(){ return createApi(MainService.class,Url);}

    public static DownService getDownAPI(){ return createApi(DownService.class,Url);}

    private static <T> T createApi(Class<T> clazz,String baseUrl){
        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }
}
















