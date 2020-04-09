package com.tramp.word.entity.group;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/10
 * version:1.0
 */

public class GroupBoardInfo {
    private int code;
    private ArrayList<Board> boards;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<Board> getBoards() {
        return boards;
    }

    public void setBoards(ArrayList<Board> boards) {
        this.boards = boards;
    }

    public class Board{
        private int board_id;
        private int board_member;
        private String board_img;
        private String board_name;
        private int board_time;
        private int board_praise;
        private String board_text;

        public int getBoard_id() {
            return board_id;
        }

        public void setBoard_id(int board_id) {
            this.board_id = board_id;
        }

        public void setBoard_img(String board_img) {
            this.board_img = board_img;
        }


        public void setBoard_name(String board_name) {
            this.board_name = board_name;
        }

        public String getBoard_img() {
            return board_img;
        }

        public void setBoard_text(String board_text) {
            this.board_text = board_text;
        }

        public String getBoard_name() {
            return board_name;
        }

        public String getBoard_text() {
            return board_text;
        }

        public int getBoard_member() {
            return board_member;
        }

        public void setBoard_member(int board_member) {
            this.board_member = board_member;
        }

        public int getBoard_praise() {
            return board_praise;
        }

        public void setBoard_praise(int board_praise) {
            this.board_praise = board_praise;
        }

        public int getBoard_time() {
            return board_time;
        }

        public void setBoard_time(int board_time) {
            this.board_time = board_time;
        }
    }
}
