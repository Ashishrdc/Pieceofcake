package com.develop.pieceofcake.global;

import java.util.ArrayList;
import java.util.List;

import com.develop.pieceofcake.model.Product;

public class GlobalData {
    public static List<Product> cart;
    static {
        cart = new ArrayList<Product>();
    }

}
