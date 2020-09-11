package com.example.aidlservertest.Proxy;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.aidlservertest.Bean.PersonBean;

import java.util.List;

/**
 * 日期：2020-09-08 17:22
 * 类说明：
 */
public class PersonManagerProxy implements PersonManagerInterface {

    //远程binder对象
    private IBinder iBinder;
    //Binder ID
    private static final java.lang.String DESCRIPTOR = "com.example.aidlservertest.Proxy.PersonManagerInterface";

    public PersonManagerProxy(IBinder iBinder) {
        this.iBinder = iBinder;
    }

    //增加人数实现
    @Override
    public void addPerson(PersonBean personBean) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel replay = Parcel.obtain();
        try {
            data.writeInterfaceToken(DESCRIPTOR);
            if (personBean != null){
                data.writeInt(1);
                personBean.writeToParcel(data,0);
            }else {
                data.writeInt(0);
            }
            iBinder.transact(Stub.TRANSAVTION_addperson,data,replay,0);
            replay.readException();
            System.out.println("--- PersonManagerProxy;addPerson; Thread: "+Thread.currentThread().getName()+", replay = "+replay);
        }finally {
            replay.recycle();
            data.recycle();
        }
    }
    //删除人数实现
    @Override
    public void deletePerson(PersonBean personBean) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel replay = Parcel.obtain();
        try {
            data.writeInterfaceToken(DESCRIPTOR);
            if (personBean != null){
                data.writeInt(1);
                personBean.writeToParcel(data,0);
            }else {
                data.writeInt(0);
            }
            iBinder.transact(Stub.TRANSAVTION_deleteperson,data,replay,0);
            replay.readException();
            System.out.println("--- PersonManagerProxy;deletePerson; Thread: "+Thread.currentThread().getName()+", replay = "+replay);
        }finally {
            replay.recycle();
            data.recycle();
        }
    }
    //查询人数实现
    @Override
    public List<PersonBean> getPerson() throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel replay = Parcel.obtain();
        List<PersonBean> result;
        try {
            data.writeInterfaceToken(DESCRIPTOR);
            iBinder.transact(Stub.TRANSAVTION_getpersons,data,replay,0);
            replay.readException();
            result = replay.createTypedArrayList(PersonBean.CREATOR);
        }finally {
            replay.recycle();
            data.recycle();
        }
        return result;
    }

    /**
     * 方法说明：返回当前获取到的服务端的IBander
     * 日期：2020-09-09 10:31
     */
    @Override
    public IBinder asBinder() {
        return iBinder;
    }




    /**
     * 方法说明：静态内部抽象类
     * Sub类继承自binder实现了 PersonManagerService 接口方法，说明它可以跨进程传输，并可进行服务端相关的数据操作
     * 日期：2020-09-08 17:46
     */
    public abstract static class Stub extends Binder implements PersonManagerInterface {
        public Stub() {
            //attachInterface方法：向BinderService注册Binder服务。只有注册了binder，客户端才能查询到有这个binder对象，并使用它
            this.attachInterface(this,DESCRIPTOR);
        }

        @Override
        public IBinder asBinder() {
            return this;
        }
        //将一个binder对象转换为 PersonManagerService 这个我们定义的接口
        public static PersonManagerInterface asInterface(IBinder iBinder){
            if ((iBinder==null)) {
                return null;
            }
            //查询当前进程，如果客户端和服务端位于同一进程，那么此方法返回的就是当前进程的Stub对象本身，否则返回服务端进程的Stub.proxy对象
            IInterface iin = iBinder.queryLocalInterface(DESCRIPTOR);
            if ((iin != null) && (iin instanceof PersonManagerInterface)) {
                return (PersonManagerInterface)iin;
            }
            return new PersonManagerProxy(iBinder);
        }

        //这个方法运行在服务端中的Binder线程池当中，当客户端发起跨进程请求时，远程请求会通过系统底层封装后交由此方法处理。
        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            switch (code){
                case INTERFACE_TRANSACTION:
                    reply.writeString(DESCRIPTOR);
                    return true;
                case TRANSAVTION_addperson://增加人数
                    data.enforceInterface(DESCRIPTOR);
                    PersonBean personBean = null;
                    if (data.readInt() != 0){
                        personBean = PersonBean.CREATOR.createFromParcel(data);
                    }
                    this.addPerson(personBean);
                    reply.writeNoException();
                    System.out.println("--- Stub;addPerson; Thread: "+Thread.currentThread().getName()+", reply = "+reply);
                    return true;
                case TRANSAVTION_deleteperson://删除人数
                    data.enforceInterface(DESCRIPTOR);
                    PersonBean personBean2 = null;
                    if (data.readInt() != 0){
                        personBean2 = PersonBean.CREATOR.createFromParcel(data);
                    }
                    this.deletePerson(personBean2);
                    reply.writeNoException();
                    System.out.println("--- Stub;deletePerson; Thread: "+Thread.currentThread().getName()+", reply = "+reply);
                    return true;
                case TRANSAVTION_getpersons://获取人数
                    data.enforceInterface(DESCRIPTOR);
                    List<PersonBean> result = this.getPerson();
                    reply.writeNoException();
                    reply.writeTypedList(result);
                    return true;
            }
            return super.onTransact(code, data, reply, flags);
        }

        public static final int TRANSAVTION_getpersons = IBinder.FIRST_CALL_TRANSACTION;
        public static final int TRANSAVTION_addperson = IBinder.FIRST_CALL_TRANSACTION + 1;
        public static final int TRANSAVTION_deleteperson = IBinder.FIRST_CALL_TRANSACTION + 2;
    }

}
