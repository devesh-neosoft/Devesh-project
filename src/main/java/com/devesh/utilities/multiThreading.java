package com.devesh.utilities;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class multiThreading extends Thread{

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("Exception occurred {}",e.getMessage());
            }
            System.out.println(i);
        }
    }
}
