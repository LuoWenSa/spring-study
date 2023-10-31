package com.luo.demo01;

//租客
public class Client {
    public static void main(String[] args) {
        Host host = new Host();
        //1.直接找房东租房子
        //host.rent();
        //2.找代理租房子
        //代理，中介帮房东租房子，但是呢？代理角色一般会有一些附属操作！
        Proxy proxy = new Proxy(host);

        //你不用面对房东，直接找中介租房即可！
        proxy.rent();
    }
}
