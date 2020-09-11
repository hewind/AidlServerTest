package com.example.aidlservertest.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

/**
 * 日期：2020-09-08 17:00
 * 类说明：实体类，实现了Parcelable，可用于跨进程通信
 */
public class PersonBean implements Parcelable {

    private String name;

    public PersonBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PersonBean{" +
                "name='" + name + '\'' +
                '}';
    }



    protected PersonBean(Parcel in) {
        name = in.readString();
    }
    public static final Creator<PersonBean> CREATOR = new Creator<PersonBean>() {
        @Override
        public PersonBean createFromParcel(Parcel in) {
            return new PersonBean(in);
        }
        @Override
        public PersonBean[] newArray(int size) {
            return new PersonBean[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
    }


    //删除使用
    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj){//自反性
            return true;
        }
        if (!(obj instanceof PersonBean)){//instanceof比较判断是否是同一类或者子父类关系
            return false;
        }
        if (this.name.equals(((PersonBean) obj).getName())){//判断是同一类或者子父类关系，再将Object类型强转为Students
            return true;
        }
        return false;
    }
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
