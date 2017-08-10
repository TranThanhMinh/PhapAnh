package anhpha.clientfirst.crm.service_api;

/**
 * Created by mc975 on 2/3/17.
 */

import java.util.List;

import anhpha.clientfirst.crm.model.AddFocus;
import anhpha.clientfirst.crm.model.Focus;
import anhpha.clientfirst.crm.model.Focus_date;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MActivity;
import anhpha.clientfirst.crm.model.MCall;
import anhpha.clientfirst.crm.model.MCheckin;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MClientArea;
import anhpha.clientfirst.crm.model.MClientBusiness;
import anhpha.clientfirst.crm.model.MClientContact;
import anhpha.clientfirst.crm.model.MClientGroup;
import anhpha.clientfirst.crm.model.MClientRequest;
import anhpha.clientfirst.crm.model.MClientType;
import anhpha.clientfirst.crm.model.MColor;
import anhpha.clientfirst.crm.model.MCommunication;
import anhpha.clientfirst.crm.model.MContract;
import anhpha.clientfirst.crm.model.MDebt;
import anhpha.clientfirst.crm.model.MEmail;
import anhpha.clientfirst.crm.model.MEvent;
import anhpha.clientfirst.crm.model.MGroupContract;
import anhpha.clientfirst.crm.model.MId;
import anhpha.clientfirst.crm.model.MKPI;
import anhpha.clientfirst.crm.model.MLabel;
import anhpha.clientfirst.crm.model.MMessager;
import anhpha.clientfirst.crm.model.MOrder;
import anhpha.clientfirst.crm.model.MPartner;
import anhpha.clientfirst.crm.model.MPhoto;
import anhpha.clientfirst.crm.model.MReport;
import anhpha.clientfirst.crm.model.MRequestBody;
import anhpha.clientfirst.crm.model.MResult_order_history;
import anhpha.clientfirst.crm.model.MTracking;
import anhpha.clientfirst.crm.model.MUser;
import anhpha.clientfirst.crm.model.MUserDefault;
import anhpha.clientfirst.crm.model.MWeekWork;
import anhpha.clientfirst.crm.model.MWorkUser;
import anhpha.clientfirst.crm.model.Result;
import anhpha.clientfirst.crm.model.Result_focus;
import anhpha.clientfirst.crm.model.Result_focus_date;
import anhpha.clientfirst.crm.model.Result_history_contract;
import anhpha.clientfirst.crm.model.Result_status_contract;
import anhpha.clientfirst.crm.model.Result_upload_photo;
import anhpha.clientfirst.crm.model.Status;
import anhpha.clientfirst.crm.model.Tracking_value_defaults;
import anhpha.clientfirst.crm.model.UserEmail;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServiceAPI {

    //static var delete_photo = domainDMS + "utils/v1/delete_photo"
    //static var get_program_promotions = domainDMS + "program/v1/get_program_promotions"
    //client
    @GET("/api/v1/client/v1/get_clients")
    Call<MAPIResponse<List<MClient>>> getClients(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("type") String type);

    @POST("/api/v1/client/v1/get_clients_in_page")
    Call<MAPIResponse<List<MClient>>> getClientsInPage(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("page_size") int page_size, @Header("value") String value, @Header("page_index") int page_index, @Body MClientRequest clientRequest);

    @POST("/api/v1/client/v1/get_clients_in_page_location")
    Call<MAPIResponse<List<MClient>>> getClientsLocation(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("page_size") int page_size, @Header("value") String value, @Header("page_index") int page_index, @Header("latitude") double latitude, @Header("longitude") double longitude, @Body MClientRequest clientRequest);

    @GET("/api/v1/client/v1/get_client_areas")
    Call<MAPIResponse<List<MClientArea>>> getClientAreas(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id);

    @GET("/api/v1/client/v1/get_client_groups")
    Call<MAPIResponse<List<MClientGroup>>> getClientGroups(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id);

    @GET("/api/v1/client/v1/get_client_types")
    Call<MAPIResponse<List<MClientType>>> getClientTypes(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id);

    @GET("/api/v1/client/v1/get_client_business")
    Call<MAPIResponse<List<MClientBusiness>>> getClientBusiness(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id);

    @GET("/api/v1/user/v1/get_week_work_day")
    Call<MAPIResponse<MWeekWork>> getCalendarActivity(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("object_id") int object_id, @Header("type_id") int type_id);

    @GET("/api/v1/client/v1/get_client")
    Call<MAPIResponse<MClient>> getClient(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("client_id") int client_id);

    @POST("/api/v1/client/v1/set_client_label_detail")
    Call<MAPIResponse<MLabel>> setClientLabelDetail(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("client_id") int client_id, @Header("status_id") int status_id, @Header("object_id") int object_id);

    @GET("/api/v1/client/v1/get_client_labels")
    Call<MAPIResponse<List<MLabel>>> getClientLabels(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("client_id") int client_id);

    @POST("/api/v1/client/v1/set_client_label")
    Call<MAPIResponse<MLabel>> setClientLabel(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("status_id") int status_id, @Body MLabel mLabel);

    @POST("/api/v1/client/v1/set_client")
    Call<MAPIResponse<MClient>> setClient(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("client_id") int client_id, @Body MClient mClient);

    @POST("/api/v1/client/v1/set_clients_contact")
    Call<MAPIResponse<List<MClientContact>>> setClientContact(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("client_id") int client_id, @Body List<MClientContact> mClient);

    //utils
    @GET("/api/v1/utils/v1/get_colors")
    Call<MAPIResponse<List<MColor>>> getColors(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id);

    @Multipart
    @POST("/api/v1/utils/v1/upload_photo")
    Call<MAPIResponse<MPhoto>> upLoadPhoto(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("code") String code, @Header("device") String device, @Header("type") String type, @Header("object_id") int object_id, @Part MultipartBody.Part photo);

    //user
    @GET("/api/v1/user/v1/get_user_login")
    Call<MAPIResponse<MUser>> getUserLogin(@Header("Authorization") String authorization, @Header("code") String code, @Header("type_id") int type_id); //user

    @POST("/api/v1/user/v1/set_user_password")
    Call<MAPIResponse<MUser>> setUserPassword(@Header("Authorization") String authorization, @Header("value") String value, @Header("user_id") int user_id, @Header("token") String token);

    @POST("/api/v1/user/v1/set_notification_tracking_device")
    Call<MAPIResponse<MUser>> setTrackingDevice(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("latitude") double latitude, @Header("longitude") double longitude);

    @POST("/api/v1/user/v1/set_user_password_by_email")
    Call<MAPIResponse<MUser>> setUserPasswordByEmail(@Header("Authorization") String authorization, @Header("value") String value, @Header("user_id") int user_id);

    @POST("/api/v1/user/v1/get_user_activies")
    Call<MAPIResponse<MActivity>> getUserActivities(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Body MRequestBody body);

    @POST("/api/v1/user/v1/get_user_activies")
    Call<MAPIResponse<MActivity>> getUserActivities(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("client_id") int client_id, @Body MRequestBody body);

    @GET("/api/v1/user/v1/get_works_user")
    Call<MAPIResponse<List<MWorkUser>>> getWorksUser(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id);

    @POST("/api/v1/user/v2/get_kpi_user")
    Call<MAPIResponse<List<MKPI>>> getKPIUser(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("object_id") int object_id, @Body MRequestBody body);

    @GET("/api/v1/user/v1/get_users")
    Call<MAPIResponse<List<MUser>>> getUsers(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id);

    @GET("/api/v1/user/v1/get_user_tracking")
    Call<MAPIResponse<List<MTracking>>> getTrackingUser(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id);

    @POST("/api/v1/user/v1/get_user_tracking_by_user")
    Call<MAPIResponse<List<MTracking>>> getTrackingUserByUser(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("object_id") int object_id, @Body MRequestBody body);

    @POST("/api/v1/user/v1/set_notification_tracking")
    Call<MAPIResponse<MMessager>> setTracking(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("type_id") int type_id);

    @POST("/api/v1/user/v1/set_user_call")
    Call<MAPIResponse<MCall>> setUserCall(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("client_id") int client_id, @Body MCall mCall);

    @POST("/api/v1/user/v1/set_user_email")
    Call<MAPIResponse<MEmail>> setUserEmail(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("client_id") int client_id, @Body MEmail mEmail);

    @POST("/api/v1/user/v1/set_user_meeting")
    Call<MAPIResponse<MCheckin>> setUserCheckin(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("client_id") int client_id, @Body MCheckin mCheckin);

    @POST("/api/v1/user/v1/set_work_user")
    Call<MAPIResponse<MWorkUser>> setWorkUser(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("client_id") int client_id, @Body MWorkUser mWorkUser);

    @POST("/api/v1/user/v1/set_work_user_status")
    Call<MAPIResponse<MWorkUser>> setWorkUserStatus(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("object_id") int object_id, @Header("status_id") int status_id);

    @GET("/api/v1/user/v1/get_work_user")
    Call<MAPIResponse<MWorkUser>> getWorkUser(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("object_id") int object_id);

    @GET("/api/v1/user/v1/get_user_meeting")
    Call<MAPIResponse<MCheckin>> getUserCheckin(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("object_id") int object_id);

    @GET("/api/v1/user/v1/get_default_user")
    Call<MAPIResponse<MUserDefault>> getUserDefault(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id);

    @GET("get_tracking_value_defaults")
    Call<MAPIResponse<List<Tracking_value_defaults>>> getTracking_value_defaults(@Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("type_id") int type_id, @Header("token") String token);

    @GET("get_user_email")
    Call<MAPIResponse<UserEmail>> get_user_email (@Header("user_id") int user_id, @Header("object_id") int object_id, @Header("token") String token, @Header("partner_id") int partner_id);
    @GET("get_user_call")
    Call<MAPIResponse<UserEmail>> get_user_call (@Header("user_id") int user_id, @Header("object_id") int object_id, @Header("token") String token, @Header("partner_id") int partner_id);
    @GET("get_user_meeting ")
    Call<MAPIResponse<UserEmail>> get_user_meeting  (@Header("user_id") int user_id, @Header("object_id") int object_id, @Header("token") String token, @Header("partner_id") int partner_id);


    //contract
    @GET("/api/v1/contract/v1/get_contracts_by_group")
    Call<MAPIResponse<List<MContract>>> getContractByGroup(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("object_id") int object_id, @Header("client_id") int client_id);

    @GET("/api/v1/contract/v1/get_contract_groups")
    Call<MAPIResponse<List<MGroupContract>>> getContractGroups(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("object_id") int object_id, @Header("client_id") int client_id);

    //exchange
    @POST("/api/v1/exchange/v1/get_order_by_partner")
    Call<MAPIResponse<List<MOrder>>> getOrderByPartner(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Body MRequestBody body);

    @GET("/api/v1/exchange/v1/get_order_debt_by_partner")
    Call<MAPIResponse<List<MOrder>>> getOrderDebtByPartner(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id);

    @POST("/api/v1/exchange/v1/get_inventory_contract_clients")
    Call<MAPIResponse<List<MContract>>> getInventoryContractClients(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Body MRequestBody body);

    @GET("/api/v1/exchange/v1/get_order")
    Call<MAPIResponse<MOrder>> getOrder(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("object_id") int object_id);

    @GET("/api/v1/exchange/v1/get_order_debt")
    Call<MAPIResponse<MOrder>> getOrderDebt(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("object_id") int object_id);

    @POST("/api/v1/exchange/v1/set_order_debt")
    Call<MAPIResponse<MDebt>> setOrderDebt(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Body MDebt body);

    @POST("/api/v1/exchange/v1/set_order")
    Call<MAPIResponse<MOrder>> setOrder(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("client_id") int client_id, @Body MOrder mOrder);

    @POST("/api/v1/exchange/v1/set_inventory_contract_client")
    Call<MAPIResponse<MOrder>> setInventory(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("client_id") int client_id, @Body MOrder MContracts);

    @GET("/api/v1/exchange/v1/get_inventory_contract_client")
    Call<MAPIResponse<MOrder>> getInventory(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("object_id") int object_id);

    @POST("/api/v1/exchange/v1/set_user_status_order")
    Call<MAPIResponse<MId>> setStatusOrder(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("object_id") int object_id, @Header("status_id") int status_id);

    //partner
    @POST("/api/v1/partner/v1/get_sales_amount")
    Call<MAPIResponse<List<MReport>>> getSalesAmount(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Body MRequestBody body);

    @POST("/api/v1/partner/v1/get_sales_amount_by_user")
    Call<MAPIResponse<List<MReport>>> getSalesAmountUser(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Body MRequestBody body);

    @POST("/api/v1/partner/v1/get_sales_order_number_by_user")
    Call<MAPIResponse<List<MReport>>> getOrderCountUser(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Body MRequestBody body);

    @POST("/api/v1/partner/v1/partner_register")
    Call<MAPIResponse<MPartner>> setPartner(@Body MPartner body);

    //program
    @GET("/api/v1/program/v1/get_program_communications")
    Call<MAPIResponse<List<MCommunication>>> getCommunication(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id);

    @GET("/api/v1/program/v1/get_program_events")
    Call<MAPIResponse<List<MEvent>>> getEvents(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id);

    @POST("/api/v1/program/v2/get_program_event")
    Call<MAPIResponse<MEvent>> getEvent(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("object_id") int object_id, @Body MRequestBody body);

    @GET("/api/v1/program/v1/get_program_event")
    Call<MAPIResponse<MEvent>> getEventClient(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("object_id") int object_id, @Header("client_id") int client_id);

    @POST("/api/v1/program/v1/set_program_event_status")
    Call<MAPIResponse<MMessager>> setEventStatus(@Header("token") String token, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("object_id") int object_id, @Header("status_id") int status_id, @Header("client_id") int client_id);

    //History transaction
//        @GET("get_clients")
//        Call<MResult_client> getClients(@Header("user_id") int user_id, @Header("partner_id") int partner_id , @Header("token") String token, @Header("page_size") int page_size, @Header("page_index") int page_index);
//        @GET("get_client_areas")
//        Call<Result_client_area> getClient_area(@Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("token") String token);
//        @GET("get_client_groups ")
//        Call<Result_client_groups> getClient_groups(@Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("token") String token);
//        @GET("get_client_labels  ")
//        Call<Result_client_lable> getClient_lable(@Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("token") String token,  @Header("client_id") int client_id);
//        @GET("get_client_types ")
//        Call<Result_client_types> getClient_types(@Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("token") String token);
    @GET("get_order_transaction ")
    Call<Result_history_contract> getHistory_contract(@Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("token") String token, @Header("object_id") int object_id);

    @GET("get_user_status_order ")
    Call<MResult_order_history> getHistory_order(@Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("token") String token, @Header("object_id") int object_id);

    @POST("set_user_status_order")
    Call<Result_status_contract> getStatus_contract(@Header("object_id") int object_id, @Header("token") String token, @Header("client_id") int client_id, @Header("status_id") int status_id, @Header("user_id") int user_id, @Header("partner_id") int partner_id, @Body Status value);

    @Multipart
    @POST("upload_photo ")
    Call<Result_upload_photo> getUpload_photo(@Header("code") String code, @Header("object_id") int object_id, @Header("token") String token, @Header("user_id") int user_id, @Header("device") String device, @Header("partner_id") int partner_id, @Header("type") String type, @Part MultipartBody.Part photo);

    @DELETE("delete_photo ")
    Call<Result_upload_photo> getDelete_photo(@Header("name") String name, @Header("code") String code, @Header("object_id") int object_id, @Header("token") String token, @Header("user_id") int user_id, @Header("device") String device, @Header("partner_id") int partner_id, @Header("type") String type, @Header("order_id") int order_id);
//        @GET("get_users ")
//        Call<Result_user> getUser(@Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("token") String token);

    //Focus
    @GET("get_clients_focus")
    Call<MAPIResponse<List<Focus_date>>> get_clients_focus (@Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("client_id") int client_id, @Header("token") String token);

    @GET("get_clients_focus")
    Call<MAPIResponse<List<Focus>>> get_clients_focus_num (@Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("client_id") int client_id, @Header("token") String token);


    @POST("set_clients_focus_status")
    Call<Result> set_clients_focus_status(@Header("user_id") int user_id, @Header("partner_id") int partner_id, @Header("token") String token, @Body List<Focus> focus);

    @POST("set_clients_focus")
    Call<Result> set_clients_focus(@Header("user_id") int usder_id, @Header("partner_id") int partner_id, @Header("token") String token, @Body List<AddFocus> focus);


}
