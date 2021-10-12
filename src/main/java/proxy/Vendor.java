package proxy;

import annotation.Route;

public class Vendor implements Sell {
    @Route
    public void sell() {
        System.out.println("In sell method");
    }

    public void ad() {
        System.out.println("ad method");
    }
}
