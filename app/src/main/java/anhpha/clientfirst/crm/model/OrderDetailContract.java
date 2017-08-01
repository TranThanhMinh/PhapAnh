package anhpha.clientfirst.crm.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailContract {

    @SerializedName("order_detail_contract_id")
    @Expose
    private Integer orderDetailContractId;
    @SerializedName("order_contract_transaction_id")
    @Expose
    private Integer orderContractTransactionId;
    @SerializedName("contract_id")
    @Expose
    private Integer contractId;
    @SerializedName("contract_name")
    @Expose
    private String contractName;
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("contract_unit_id")
    @Expose
    private Integer contractUnitId;
    @SerializedName("contract_unit_name")
    @Expose
    private String contractUnitName;
    @SerializedName("order_contract_id")
    @Expose
    private Integer orderContractId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("price_group")
    @Expose
    private Integer priceGroup;
    @SerializedName("contract_price_original")
    @Expose
    private Integer contractPriceOriginal;
    @SerializedName("contract_price_id")
    @Expose
    private Integer contractPriceId;
    @SerializedName("promotion_id")
    @Expose
    private Integer promotionId;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("contract_status_id")
    @Expose
    private Integer contractStatusId;
    @SerializedName("flag_delete")
    @Expose
    private String flagDelete;
    @SerializedName("delete_reason")
    @Expose
    private String deleteReason;
    @SerializedName("status_id")
    @Expose
    private Integer statusId;
    @SerializedName("number_in_group")
    @Expose
    private Integer numberInGroup;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("price_name")
    @Expose
    private Object priceName;
    @SerializedName("tax")
    @Expose
    private Integer tax;
    @SerializedName("is_price_tax")
    @Expose
    private Boolean isPriceTax;
    @SerializedName("order_contract_type_id")
    @Expose
    private Integer orderContractTypeId;
    @SerializedName("contract_unit_group_id")
    @Expose
    private Integer contractUnitGroupId;
    @SerializedName("contract_unit_group_name")
    @Expose
    private String contractUnitGroupName;
    @SerializedName("photos")
    @Expose
    private List<Object> photos = null;
    private int id ;
    private double contract_price ;

    private String create_date = "";
    private String modify_date = "";
    private double point_value ;
    private double weight ;

    private double contract_price_group ;
    private int client_id ;

    private double vat;
    private int is_price_vat;

    private int number_group ;
    private String update_time = "";
    private int update_status ;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getPriceTax() {
        return isPriceTax;
    }

    public void setPriceTax(Boolean priceTax) {
        isPriceTax = priceTax;
    }



    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getModify_date() {
        return modify_date;
    }

    public void setModify_date(String modify_date) {
        this.modify_date = modify_date;
    }


    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public double getContract_price() {
        return contract_price;
    }

    public void setContract_price(double contract_price) {
        this.contract_price = contract_price;
    }

    public double getPoint_value() {
        return point_value;
    }

    public void setPoint_value(double point_value) {
        this.point_value = point_value;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getContract_price_group() {
        return contract_price_group;
    }

    public void setContract_price_group(double contract_price_group) {
        this.contract_price_group = contract_price_group;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public int getIs_price_vat() {
        return is_price_vat;
    }

    public void setIs_price_vat(int is_price_vat) {
        this.is_price_vat = is_price_vat;
    }

    public int getNumber_group() {
        return number_group;
    }

    public void setNumber_group(int number_group) {
        this.number_group = number_group;
    }

    public int getUpdate_status() {
        return update_status;
    }

    public void setUpdate_status(int update_status) {
        this.update_status = update_status;
    }

    public Integer getOrderDetailContractId() {
        return orderDetailContractId;
    }

    public void setOrderDetailContractId(Integer orderDetailContractId) {
        this.orderDetailContractId = orderDetailContractId;
    }

    public Integer getOrderContractTransactionId() {
        return orderContractTransactionId;
    }

    public void setOrderContractTransactionId(Integer orderContractTransactionId) {
        this.orderContractTransactionId = orderContractTransactionId;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getContractUnitId() {
        return contractUnitId;
    }

    public void setContractUnitId(Integer contractUnitId) {
        this.contractUnitId = contractUnitId;
    }

    public String getContractUnitName() {
        return contractUnitName;
    }

    public void setContractUnitName(String contractUnitName) {
        this.contractUnitName = contractUnitName;
    }

    public Integer getOrderContractId() {
        return orderContractId;
    }

    public void setOrderContractId(Integer orderContractId) {
        this.orderContractId = orderContractId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPriceGroup() {
        return priceGroup;
    }

    public void setPriceGroup(Integer priceGroup) {
        this.priceGroup = priceGroup;
    }

    public Integer getContractPriceOriginal() {
        return contractPriceOriginal;
    }

    public void setContractPriceOriginal(Integer contractPriceOriginal) {
        this.contractPriceOriginal = contractPriceOriginal;
    }

    public Integer getContractPriceId() {
        return contractPriceId;
    }

    public void setContractPriceId(Integer contractPriceId) {
        this.contractPriceId = contractPriceId;
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }



    public Integer getContractStatusId() {
        return contractStatusId;
    }

    public void setContractStatusId(Integer contractStatusId) {
        this.contractStatusId = contractStatusId;
    }

    public String getFlagDelete() {
        return flagDelete;
    }

    public void setFlagDelete(String flagDelete) {
        this.flagDelete = flagDelete;
    }

    public String getDeleteReason() {
        return deleteReason;
    }

    public void setDeleteReason(String deleteReason) {
        this.deleteReason = deleteReason;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getNumberInGroup() {
        return numberInGroup;
    }

    public void setNumberInGroup(Integer numberInGroup) {
        this.numberInGroup = numberInGroup;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Object getPriceName() {
        return priceName;
    }

    public void setPriceName(Object priceName) {
        this.priceName = priceName;
    }

    public Integer getTax() {
        return tax;
    }

    public void setTax(Integer tax) {
        this.tax = tax;
    }

    public Boolean getIsPriceTax() {
        return isPriceTax;
    }

    public void setIsPriceTax(Boolean isPriceTax) {
        this.isPriceTax = isPriceTax;
    }

    public Integer getOrderContractTypeId() {
        return orderContractTypeId;
    }

    public void setOrderContractTypeId(Integer orderContractTypeId) {
        this.orderContractTypeId = orderContractTypeId;
    }

    public Integer getContractUnitGroupId() {
        return contractUnitGroupId;
    }

    public void setContractUnitGroupId(Integer contractUnitGroupId) {
        this.contractUnitGroupId = contractUnitGroupId;
    }

    public String getContractUnitGroupName() {
        return contractUnitGroupName;
    }

    public void setContractUnitGroupName(String contractUnitGroupName) {
        this.contractUnitGroupName = contractUnitGroupName;
    }

    public List<Object> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Object> photos) {
        this.photos = photos;
    }



}