package com.company;

import com.company.UI.Shell;

public class Main {

    public static void main(String[] args) {
        try {
            Shell shell = new Shell();
        }
        catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
