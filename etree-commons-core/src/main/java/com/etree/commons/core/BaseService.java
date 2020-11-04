/**
* Copyright © 2020 Franklinton IT Pvt. Ltd.
*
* @author  Franklin Joshua
* @version 1.0
* @since   2016-01-15 
*/
package com.etree.commons.core;

import com.etree.commons.core.dto.RequestWrapperDto;

public interface BaseService {

	double NANOS_IN_ONE_MILLI = 1000000;

	String SERVICE = "Service";
	
	String SERVICE_SWITCH_OFFICE = "SwitchOffice";

	String SERVICE_TRAVELAGENCYPROFILE_TRAVELAGENCY	= "TravelAgencyProfile.travelAgency";
	String SERVICE_TRAVELAGENCYPROFILE_AGENCYOFFICE	= "TravelAgencyProfile.agencyOffice";
	String SERVICE_TRAVELAGENCYPROFILE_OFFICECODES	= "TravelAgencyProfile.officeCodes";
	String SERVICE_TRAVELAGENCYPROFILE_AGENTEXISTS	= "TravelAgencyProfile.agentExists";
	String SERVICE_TRAVELAGENCYPROFILE_AGENTPROFILE	= "TravelAgencyProfile.agentProfile";
	String SERVICE_TRAVELAGENCYPROFILE_AGENTPROFILES= "TravelAgencyProfile.agentProfiles";
	
	String SERVICE_USERIDENTITY_USEREXISTS		= "UserIdentity.userExists";
	String SERVICE_USERIDENTITY_PASSWORD		= "UserIdentity.password";
	String SERVICE_USERIDENTITY_USERDETAILS		= "UserIdentity.userDetails";
	String SERVICE_USERIDENTITY_ACCESSRIGHTS	= "UserIdentity.accessRights";
	String SERVICE_USERIDENTITY_CONFIGUREDROLE	= "UserIdentity.configuredRole";
	String SERVICE_USERIDENTITY_CONFIGUREDROLES	= "UserIdentity.configuredRoles";

	String SERVICE_CONFIGURATOR				= "Configurator";
	String SERVICE_USERIDENTITY				= "UserIdentity";
	String SERVICE_TRAVELAGENCYPROFILE		= "TravelAgencyProfile";
	String SERVICE_CUSTOMER_PROFILE			= "CustomerProfile";

	String SERVICE_AIRSHOPPING 			= "AirShopping";
	String SERVICE_ANCILLARY 			= "BaggageAllowance";
	String SERVICE_BAGGAGE_CHARGES 		= "BaggageCharges";
	String SERVICE_BAGGAGE_LIST 		= "BaggageList";
	String SERVICE_FARE_RULES 			= "FareRules";
	String SERVICE_FILE_RETRIEVE 		= "FileRetrieve";
	String SERVICE_FLIGHTPRICE 			= "FlightPrice";
	String SERVICE_SEATAVAILABILITY 	= "SeatAvailability";
	String SERVICE_SERVICE_LIST 		= "ServiceList";
	String SERVICE_SERVICE_PRICE 		= "ServicePrice";

	String SERVICE_AIRLINE_PROFILE 		= "AirlineProfile";
	String SERVICE_CUSTOMER_INPUT 		= "CustomerInput";
	
	String SERVICE_ORDER_CHANGE_NOTIF 	= "OrderChangeNotif";
	String SERVICE_ORDER_HISTORY_NOTIF 	= "OrderHistoryNotif";
	String SERVICE_ORDER_HISTORY 		= "OrderHistory";
	String SERVICE_ORDER_LIST 			= "OrderList";
	String SERVICE_ORDER_RULES 			= "OrderRules";
	String SERVICE_ITINRESHOP 			= "ItinReshop";

	String SERVICE_AIRDOC_DISPLAY 		= "AirDocDisplay";
	String SERVICE_AIRDOC_HISTORY 		= "AirDocHistory";
	String SERVICE_AIRDOC_ISSUE 		= "AirDocIssue";
	String SERVICE_AIRDOC_EXCHANGE 		= "AirDocExchange";
	String SERVICE_AIRDOC_REFUND 		= "AirDocRefund";
	String SERVICE_SERVICE_INFO 		= "ServiceInfo";
	String SERVICE_FARERULES			= "FareRules";
	String SERVICE_TICKETISSUE 			= "TicketIssue";
	String SERVICE_TICKETRETRIEVE 			= "TicketRetrieve";

	String SERVICE_ORDERCREATE 				= "OrderCreate";
	String SERVICE_ORDER_CANCEL 			= "OrderCancel";
	String SERVICE_ORDER_CANCEL_TICKETREFUND 	= "OrderCancel.TicketRefund";
	String SERVICE_ORDER_CANCEL_TICKETVOID 		= "OrderCancel.TicketVoid";
	String SERVICE_ORDER_CHANGE 				= "OrderChange";
	String SERVICE_ORDER_CHANGE_TICKETEXCHANGE 	= "OrderChange.TicketExchange";
	String SERVICE_ORDER_CHANGE_PAIDSEAT		= "OrderChange.PaidSeat";
	String SERVICE_ORDER_RETRIEVE 			= "OrderRetrieve";
	String SERVICE_POSTCONFIRMATION			= "PostConfirmation";

	String SERVICE_POSTCONFIRMATION_PDF		= "PostConfirmation.pdf";
	String SERVICE_POSTCONFIRMATION_EMAIL	= "PostConfirmation.email";
	
	String SERVICE_OFFERPRICE 				= "OfferPrice";
	String SERVICE_ORDERRESHOP 				= "OrderReshop";

	String SERVICE_IFG_PAYMENT_AUTHORIZE	= "PaymentAuthorize";
	String SERVICE_IFG_AUTHORIZE_CANCEL		= "AuthorizeCancel";
	String SERVICE_IFG_ORDER				= "IfgOrder";
	String SERVICE_IFG_ORDER_CANCEL			= "IfgOrderCancel";
	String SERVICE_IFG_ORDER_REFUND			= "IfgOrderRefund";
	String SERVICE_IFG_ORDER_EXCHANGE		= "IfgOrderExchange";

	<T> T process(RequestWrapperDto requestWrapper);
}
