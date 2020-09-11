package com.example.aidlservertest.Proxy;

import android.os.IInterface;
import android.os.RemoteException;

import com.example.aidlservertest.Bean.PersonBean;

import java.util.List;

/**
 * 日期：2020-09-08 17:11
 * 类说明：这个类用来定义服务端具有什么样的能力，继承自IInterface才就有跨进程传输的基础能力
 */
public interface PersonManagerInterface extends IInterface {

    //添加人数
    void addPerson(PersonBean personBean) throws RemoteException;

    //删除人数
    void deletePerson(PersonBean personBean) throws RemoteException;

    //获取人数
    List<PersonBean> getPerson() throws RemoteException;

}
