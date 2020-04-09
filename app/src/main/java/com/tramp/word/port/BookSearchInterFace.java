package com.tramp.word.port;

import com.tramp.word.entity.book.BookSearchInfo;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/06/09
 * version:1.0
 */
public interface BookSearchInterFace {
    void ShowBookPop(BookSearchInfo.Language.Book book);
    void SwipeSearch();
}
