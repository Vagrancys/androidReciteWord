package com.tramp.word.api.api;

import com.tramp.word.entity.DefaultInfo;
import com.tramp.word.entity.book.BookFormInfo;
import com.tramp.word.entity.book.BookHintInfo;
import com.tramp.word.entity.book.BookInfo;
import com.tramp.word.entity.book.BookInsertInfo;
import com.tramp.word.entity.book.BookItemInfo;
import com.tramp.word.entity.book.BookListInfo;
import com.tramp.word.entity.book.BookSearchInfo;
import com.tramp.word.entity.book.BookTagInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/26
 * version:1.0
 */

public interface BookService {
    @GET("book.html")
    Call<BookInfo> getBookInfo(@Query("user_id") int user_id);

    @GET("book_list.html")
    Call<BookListInfo> getBookListInfo(@Query("language_id") int language_id, @Query("one_number") int one_number);

    @GET("book_item.html")
    Call<BookItemInfo> getBookItemInfo(@Query("language_id") int language_id,@Query("one_number") int one_id, @Query("two_number") int two_id);

    @GET("book_insert.html")
    Call<BookInsertInfo> getBookInsertInfo(@Query("book_id") int book_id,@Query("user_id") int user_id);

    @GET("book_delete.html")
    Call<DefaultInfo> getBookDeleteInfo(@Query("user_id") int user_id,@Query("book_id") int book_id);

    @GET("book_search.html")
    Call<BookSearchInfo> getBookSearchInfo(@Query("user_id") int user_id,@Query("book_name") String book_name,@Query("more_status") int more_status);

    @GET("book_tag.html")
    Call<BookTagInfo> getBookTagInfo();

    @GET("book_hint.html")
    Call<BookHintInfo> getBookHintInfo(@Query("book_hint") String book_hint);

    @FormUrlEncoded
    @POST("book_form.html")
    Call<DefaultInfo> getBookFormInfo(@Query("user_id") int user_id,@Body BookFormInfo bookForm);
}













