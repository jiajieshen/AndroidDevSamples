package com.jiajieshen.android.samples.model;

import java.util.List;

/**
 * Created by xin on 8/7/17.
 */

public class FirebaseResult {
    public String multicast_id;
    public String success;
    public String failure;
    public String canonical_ids;
    public List<Result> results;

    public class Result {
        public String message_id;

        @Override
        public String toString() {
            return "Result{" +
                    "message_id='" + message_id + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FirebaseResult{" +
                "multicast_id='" + multicast_id + '\'' +
                ", success='" + success + '\'' +
                ", failure='" + failure + '\'' +
                ", canonical_ids='" + canonical_ids + '\'' +
                ", results=" + results +
                '}';
    }
}
