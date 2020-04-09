package com.tramp.word.port;

import com.tramp.word.entity.DefaultBookInfo;
import com.tramp.word.entity.book.BookItemInfo;

/**
 * Created by Administrator on 2019/1/14.
 */

public interface WordBookPopInterFace {
    void ShowWordBookPop(BookItemInfo.Book book);
    void ShowWordBookItemPop(DefaultBookInfo book,int delete);
}
