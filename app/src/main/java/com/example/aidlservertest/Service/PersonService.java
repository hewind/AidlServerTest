package com.example.aidlservertest.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.example.aidlservertest.Bean.PersonBean;
import com.example.aidlservertest.Proxy.PersonManagerProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * 日期：2020-09-09 11:08
 * 类说明：服务端的service
 */
public class PersonService extends Service {

    private List<PersonBean> list = new ArrayList<>();

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        System.out.println("---服务端 onStart; Thread: "+Thread.currentThread().getName());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("---服务端 onStartCommand; Thread: "+Thread.currentThread().getName());
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("---服务端 onBind; Thread: "+Thread.currentThread().getName());
        return mIBinder;
    }

    /**
     * 方法说明：
     * 日期：2020-09-09 11:26
     */
    private IBinder mIBinder = new PersonManagerProxy.Stub(){
        @Override
        public void addPerson(PersonBean personBean) throws RemoteException {
            if (personBean != null){
                list.add(personBean);
            }
            System.out.println("---服务端 addPerson; Thread: "+Thread.currentThread().getName()+", list: "+list);
        }
        @Override
        public void deletePerson(PersonBean personBean) throws RemoteException {
            list.remove(personBean);
            System.out.println("---服务端 deletePerson; Thread: "+Thread.currentThread().getName()+", list: "+list);
        }
        @Override
        public List<PersonBean> getPerson() throws RemoteException {
            System.out.println("---服务端 getPerson; Thread: "+Thread.currentThread().getName()+", list: "+list);
            return list;
        }
    };
}
