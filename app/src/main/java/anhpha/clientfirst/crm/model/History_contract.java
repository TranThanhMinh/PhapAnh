package anhpha.clientfirst.crm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MinhTran on 7/12/2017.
 */

public class History_contract {

    @SerializedName("order_contract_id")
    @Expose
    private Integer orderContractId;
    @SerializedName("order_contract_transaction_id")
    @Expose
    private Integer orderContractTransactionId;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("number_client")
    @Expose
    private Integer numberClient;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("amount_non_discount")
    @Expose
    private Integer amountNonDiscount;
    @SerializedName("amount_payment")
    @Expose
    private Integer amountPayment;
    @SerializedName("latitude")
    @Expose
    private Integer latitude;
    @SerializedName("longitude")
    @Expose
    private Integer longitude;
    @SerializedName("discount_order_price")
    @Expose
    private Integer discountOrderPrice;
    @SerializedName("discount_order_percent")
    @Expose
    private Integer discountOrderPercent;
    @SerializedName("payment_type_id")
    @Expose
    private Integer paymentTypeId;
    @SerializedName("payment_status_id")
    @Expose
    private Integer paymentStatusId;
    @SerializedName("tax_price")
    @Expose
    private Integer taxPrice;
    @SerializedName("prepay")
    @Expose
    private Integer prepay;
    @SerializedName("order_sheet_id")
    @Expose
    private Integer orderSheetId;
    @SerializedName("tax_percent")
    @Expose
    private Integer taxPercent;
    @SerializedName("surcharge_price")
    @Expose
    private Integer surchargePrice;
    @SerializedName("surcharge_percent")
    @Expose
    private Integer surchargePercent;
    @SerializedName("discount_client_price")
    @Expose
    private Integer discountClientPrice;
    @SerializedName("discount_client_percent")
    @Expose
    private Integer discountClientPercent;
    @SerializedName("payment_client_money")
    @Expose
    private Integer paymentClientMoney;
    @SerializedName("order_status_id")
    @Expose
    private Integer orderStatusId;
    @SerializedName("status_id")
    @Expose
    private Integer statusId;
    @SerializedName("partner_id")
    @Expose
    private Integer partnerId;
    @SerializedName("order_type_id")
    @Expose
    private Integer orderTypeId;
    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;
    @SerializedName("expected_completion_date")
    @Expose
    private String expectedCompletionDate;
    @SerializedName("partner_name")
    @Expose
    private Object partnerName;
    @SerializedName("client_id")
    @Expose
    private Integer clientId;
    @SerializedName("order_contract_code")
    @Expose
    private String orderContractCode;
    @SerializedName("order_contract_code_parent")
    @Expose
    private String orderContractCodeParent;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("order_status_name")
    @Expose
    private String orderStatusName;
    @SerializedName("order_contract_no")
    @Expose
    private Object orderContractNo;
    @SerializedName("phone")
    @Expose
    private Object phone;
    @SerializedName("client_name")
    @Expose
    private Object clientName;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("order_detail_contracts")
    @Expose
    private List<OrderDetailContract> orderDetailContracts = null;
    @SerializedName("debts")
    @Expose
    private List<Object> debts = null;

    public Integer getOrderContractId() {
        return orderContractId;
    }

    public void setOrderContractId(Integer orderContractId) {
        this.orderContractId = orderContractId;
    }

    public Integer getOrderContractTransactionId() {
        return orderContractTransactionId;
    }

    public void setOrderContractTransactionId(Integer orderContractTransactionId) {
        this.orderContractTransactionId = orderContractTransactionId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getNumberClient() {
        return numberClient;
    }

    public void setNumberClient(Integer numberClient) {
        this.numberClient = numberClient;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAmountNonDiscount() {
        return amountNonDiscount;
    }

    public void setAmountNonDiscount(Integer amountNonDiscount) {
        this.amountNonDiscount = amountNonDiscount;
    }

    public Integer getAmountPayment() {
        return amountPayment;
    }

    public void setAmountPayment(Integer amountPayment) {
        this.amountPayment = amountPayment;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public Integer getDiscountOrderPrice() {
        return discountOrderPrice;
    }

    public void setDiscountOrderPrice(Integer discountOrderPrice) {
        this.discountOrderPrice = discountOrderPrice;
    }

    public Integer getDiscountOrderPercent() {
        return discountOrderPercent;
    }

    public void setDiscountOrderPercent(Integer discountOrderPercent) {
        this.discountOrderPercent = discountOrderPercent;
    }

    public Integer getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(Integer paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public Integer getPaymentStatusId() {
        return paymentStatusId;
    }

    public void setPaymentStatusId(Integer paymentStatusId) {
        this.paymentStatusId = paymentStatusId;
    }

    public Integer getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(Integer taxPrice) {
        this.taxPrice = taxPrice;
    }

    public Integer getPrepay() {
        return prepay;
    }

    public void setPrepay(Integer prepay) {
        this.prepay = prepay;
    }

    public Integer getOrderSheetId() {
        return orderSheetId;
    }

    public void setOrderSheetId(Integer orderSheetId) {
        this.orderSheetId = orderSheetId;
    }

    public Integer getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(Integer taxPercent) {
        this.taxPercent = taxPercent;
    }

    public Integer getSurchargePrice() {
        return surchargePrice;
    }

    public void setSurchargePrice(Integer surchargePrice) {
        this.surchargePrice = surchargePrice;
    }

    public Integer getSurchargePercent() {
        return surchargePercent;
    }

    public void setSurchargePercent(Integer surchargePercent) {
        this.surchargePercent = surchargePercent;
    }

    public Integer getDiscountClientPrice() {
        return discountClientPrice;
    }

    public void setDiscountClientPrice(Integer discountClientPrice) {
        this.discountClientPrice = discountClientPrice;
    }

    public Integer getDiscountClientPercent() {
        return discountClientPercent;
    }

    public void setDiscountClientPercent(Integer discountClientPercent) {
        this.discountClientPercent = discountClientPercent;
    }

    public Integer getPaymentClientMoney() {
        return paymentClientMoney;
    }

    public void setPaymentClientMoney(Integer paymentClientMoney) {
        this.paymentClientMoney = paymentClientMoney;
    }

    public Integer getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(Integer orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Integer orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getExpectedCompletionDate() {
        return expectedCompletionDate;
    }

    public void setExpectedCompletionDate(String expectedCompletionDate) {
        this.expectedCompletionDate = expectedCompletionDate;
    }

    public Object getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(Object partnerName) {
        this.partnerName = partnerName;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getOrderContractCode() {
        return orderContractCode;
    }

    public void setOrderContractCode(String orderContractCode) {
        this.orderContractCode = orderContractCode;
    }

    public String getOrderContractCodeParent() {
        return orderContractCodeParent;
    }

    public void setOrderContractCodeParent(String orderContractCodeParent) {
        this.orderContractCodeParent = orderContractCodeParent;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public Object getOrderContractNo() {
        return orderContractNo;
    }

    public void setOrderContractNo(Object orderContractNo) {
        this.orderContractNo = orderContractNo;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public Object getClientName() {
        return clientName;
    }

    public void setClientName(Object clientName) {
        this.clientName = clientName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public List<OrderDetailContract> getOrderDetailContracts() {
        return orderDetailContracts;
    }

    public void setOrderDetailContracts(List<OrderDetailContract> orderDetailContracts) {
        this.orderDetailContracts = orderDetailContracts;
    }

    public List<Object> getDebts() {
        return debts;
    }

    public void setDebts(List<Object> debts) {
        this.debts = debts;
    }
}
