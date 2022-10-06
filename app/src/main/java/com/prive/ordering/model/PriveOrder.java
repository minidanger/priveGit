package com.prive.ordering.model;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @Author: Brooke Li
 * @E-mail: Halloworld1992@outlook.com
 * @Date: Created in $[TIME] $[DATE]
 * @Description:
 * @Modified by:
 */
@TableName("prive_order")
public class PriveOrder {
    private String id;
    private String type;
    private int qty;
    private String code;
    private double price;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PriveOrder{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", qty=" + qty +
                ", code='" + code + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}
