package com.nwf.sports.mvp.model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nereus on 2017/6/23.
 */
public class QueryProgressResult implements Parcelable {
    public String requestId;
    public ProgressEnum progress;
    public long time;


    @Override
    public String toString() {
        return "QueryProgressResult{" +
                "requestId='" + requestId + '\'' +
                ", progress=" + progress +
                ", time=" + time +
                '}';
    }

    public QueryProgressResult(String requestId, ProgressEnum progress, long time) {
        this.requestId = requestId;
        this.progress = progress;
        this.time = time;
    }

    public QueryProgressResult() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.requestId);
        dest.writeInt(this.progress == null ? -1 : this.progress.ordinal());
        dest.writeLong(this.time);
    }

    protected QueryProgressResult(Parcel in) {
        this.requestId = in.readString();
        int tmpProgress = in.readInt();
        this.progress = tmpProgress == -1 ? null : ProgressEnum.values()[tmpProgress];
        this.time = in.readLong();
    }

    public static final Creator<QueryProgressResult> CREATOR = new Creator<QueryProgressResult>() {
        @Override
        public QueryProgressResult createFromParcel(Parcel source) {
            return new QueryProgressResult(source);
        }

        @Override
        public QueryProgressResult[] newArray(int size) {
            return new QueryProgressResult[size];
        }
    };
}
