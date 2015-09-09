package glamvoir.appzstack.glamvoir.json;

/**
 * Created by gajendra on 24/4/15.
 */
public interface Keys {

    public interface ProductSearch {

        public static final String KEY_MESSAGE = "Message";
        public static final String KEY_SEARCHTYPE = "SearchType";
        public static final String KEY_SEARCHVALUE = "SearchValue";
        public static final String KEY_PARTIAL = "Partial";
        public static final String KEY_PRODUCTS = "Products";
    }


    public interface Products {

//  "SERVICE": "DIS",
//  "PRODUCT": "DEED",
//  "PRODUCT_DESC": "Image of Private Property Deeds",
//  "CREATED_DATE": "2015-04-17T00:00:00",
//  "CREATED_BY": null,
//  "UPDATE_DATE": "2015-04-17T00:00:00",
//  "UPDATE_BY": null

        public static final String KEY_SERVICE = "SERVICE";
        public static final String KEY_PRODUCT = "PRODUCT";
        public static final String KEY_PRODUCT_DESC = "PRODUCT_DESC";
        public static final String KEY_CREATED_DATE = "CREATED_DATE";
        public static final String KEY_CREATED_BY = "CREATED_BY";
        public static final String KEY_UPDATE_DATE = "UPDATE_DATE";
        public static final String KEY_UPDATE_BY = "UPDATE_BY";
        public static final String KEY_PRODUCT_TITLE="PRODUCT_TITLE";
    }

    public interface Cart {
        public static final String TRANS_ID = "TRANS_ID";
        public static final String CART_ID = "CART_ID";
        public static final String ITEM = "ITEM";
        public static final String SERVICE = "SERVICE";
        public static final String PRODUCT = "PRODUCT";
        public static final String PRODUCT_DESC = "PRODUCT_DESC";
        public static final String POSTAGE = "POSTAGE";
        public static final String POSTAGE_COST = "POSTAGE_COST";
        public static final String TOTAL_COST = "TOTAL_COST";
        public static final String PER_ITEM_GST = "PER_ITEM_GST";
        public static final String PRODUCT_TITLE="PRODUCT_TITLE";
    }

    public interface Payment
    {
        public static final String mid="mid";
        public static final String ref_transId="ref_transId";
        public static final String amt="amt";
        public static final String cur="cur";
        public static final String rcard="rcard";
        public static final String EP2URL="EP2URL";
        public static final String returnurl="returnurl";
        public static final String statusurl="statusurl";
        public static final String skipstatuspage="skipstatuspage";
        public static final String version="version";
        public static final String validity="validity";
        public static final String signature="signature";
        public static final String transtype="transtype";
        public static final String subtranstype="subtranstype";

    }

    public interface  PaymentConfirm
    {
        public static final String TRANS_DATE_PaymentConfirm="TRANS_DATE";
        public static final String PAYMENTTYPE_PaymentConfirm="PAYMENTTYPE";
        public static final String PAYMENTSTATUS_PaymentConfirm="PAYMENTSTATUS";
        public static final String MESSAGE_PaymentConfirm="Message";
        public static final String REF_TRANSID_PaymentConfirm="REF_TRANSID";
        public static final String ITEM_PaymentConfirm="ITEM";
        public static final String APPROVALCODE_PaymentConfirm="APPROVALCODE";
        public static final String PONUM_PaymentConfirm="PONUM";
        public static final String TOTAL_COST_PaymentConfirm="TOTAL_COST";
        public static final String PRODUCT_DESC_PaymentConfirm="PRODUCT_DESC";
        public static final String UNITPRICE_PaymentConfirm="UNITPRICE";
        public static final String STATUS_PaymentConfirm="STATUS";
        public static final String PRODUCT_TITLE_PaymentConfirm="PRODUCT_TITLE";
        public static final String TotalPostageCost_PaymentConfirm="TotalPostageCost";

    }
public interface DuplicatePayment
{
    public static final String CART_ID_Duplicate="CART_ID";
    public static final String MSG="MSG";
}


//    {
//        "SearchInfo": {
//        "Type": null,
//                "Value": null
//    },
//        "ProductItems": [
//        {
//            "ItemValue": "VOL 0005 FOL 005",
//                "ItemDescription": "Image of Private Property Deeds",
//                "ItemStatus": true
//        }
//        ],
//        "ProductStatus": "REDIRECT",
//            "Description": null,
//            "PostageCost": 3.5,
//            "UnitPrice": 4.2,
//            "GST": 0,
//            "ProductGroup": "DIS",
//            "ProductName": "DEED",
//            "ProductDescription": "Image of Private Property Deeds",
//            "Message": "",
//            "IsInDigitalFormat": false,
//            "IsAvailableForDisplay": true
//    }


    public interface ProductDetail {


//        "ProductStatus":"REDIRECT",
//                "Description":null,
//                "PostageCost":3.5,
//                "UnitPrice":4.2,
//                "GST":0,
//                "ProductGroup":"DIS",
//                "ProductName":"DEED",
//                "ProductDescription":"Image of Private Property Deeds",
//                "Message":"",
//                "IsInDigitalFormat":false,
//                "IsAvailableForDisplay":true

        public static final String KEY_PRODUCTSTATUS = "ProductStatus";
        public static final String KEY_DESCRIPTION = "Description";
        public static final String KEY_POSTAGECOST = "PostageCost";
        public static final String KEY_UNITPRICE = "UnitPrice";
        public static final String KEY_GST = "GST";
        public static final String KEY_PRODUCT_GROUP = "ProductGroup";
        public static final String KEY_PRODUCT_NAME = "ProductName";
        public static final String KEY_PRODUCT_DESCRIPTION = "ProductDescription";
        public static final String KEY_RRODUCTDETAIL_MESSAGE = "Message";
        public static final String KEY_ISIN_DIGITAL_FORMAT = "IsInDigitalFormat";
        public static final String KEY_IS_AVAILABLE_FOR_DISPLAY = "IsAvailableForDisplay";
    }

    public interface ProductDetailProductItem {

//        "ProductItems":[
//        {
//            "ItemValue":"VOL 0005 FOL 005",
//                "ItemDescription":"Image of Private Property Deeds",
//                "ItemStatus":true
//        }
//    }

        public static final String KEY_ARRAY_PRODUCTITEM="ProductItems";
        public static final String KEY_ITEMVALUE = "ItemValue";
        public static final String KEY_ITEMDESCRIPTION = "ItemDescription";
        public static final String KEY_ITEMSTATUS = "ItemStatus";
        public static final String KEY_ITEMTITLE = "ItemTitle";
    }

}
