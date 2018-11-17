package com.ian.spidemo;

import com.ian.spiinterfacedemo.IanInterface;

/**
 * Created by Ian.Lu on 2018/11/17.
 * Project : IanRouter
 */
public class DemoImpl implements IanInterface {
    @Override
    public String hello() {
        return "我是com.ian.spidemo.DemoImpl";
    }
}
