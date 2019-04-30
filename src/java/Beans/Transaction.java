package Beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author yaseenfarooqui
 */
@ManagedBean(name = "transaction")
public class Transaction implements Serializable {

    private int propertyID;
    private int transactionID;
    private String buyerName;
    private String sellerName;

    public Transaction(){

    }

    public int getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(int propertyID) {
        this.propertyID = propertyID;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }
    
    
}