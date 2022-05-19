package com.jatri.common.constant

object AppConstant {

    const val broadcast_detected_trip_request = "BROADCAST_DETECTED_ACTIVITY"

    const val local = "local"
    const val image_url= "https://storage.jatri.co/"
    const val app_base_url= "https://storage.jatri.co/"
    const val url= "url"
    const val default_notification_channel_id = "com.jatri.user.v.1"
    const val JATRI_PUSH_NOTIFICATION= "jatri_push_notification"
    const val appLanguageEn = "en"
    const val appLanguageBn = "bn"

    const val vehicleConditionAny = "Any"
    const val vehicleConditionAc = "AC"
    const val vehicleConditionNonAc = "NonAC"

    const val statusCompleted = "COMPLETED"
    const val statusConfirmed = "CONFIRMED"
    const val statusBidding = "BIDDING"
    const val statusWaiting = "WAITING"
    const val statusExpired = "EXPIRED"
    const val statusCancelled = "CANCELLED"
    const val statusRejected = "REJECTED"

    const val female = "Female"
    const val male = "Male"

    const val landing_rental_bidding_ongoing = "bidding_ongoing"
    const val landing_rental_trip_details_admin_panel = "RentalTripDetails"//used for Rental Trip Details, coming from admin panel
    const val landing_rental_home_screen = "RentalBookingPage"
    const val landing_rental_confirmed_trip_details = "rental_confirmed_trip_details"
    const val landing_rental_trip_details = "trip_details"//used for display trip details, notification coming for [trip request submitted, confirm trip and after complete a trip]
    const val landing_offer_screen = "OfferPage"
    const val landing_user_profile_screen = "Profile"
    const val landing_promo_code_screen = "PromocodePage"
    const val landing_main_home_screen = "HomePage"
    const val landing_nothing = "no_landing"

    const val notificationDigitalBusTicketPendingPayment = "dt_pending_payment" // target available
    const val notificationDigitalBusTicketBookingDetails = "dt_booking_details" // target available
    const val notificationDigitalBusTicketHomepage = "dt_homepage"

    const val fromLocation = "fromLocation"
    const val toLocation = "toLocation"
    const val buttonBoardingPoint = "buttonBoardingPoint"
    const val buttonDepartureTime = "buttonDepartureTime"
    const val priorityRemove = "priorityRemove"
    const val maxItemSelected = "itemThreeSelected"
    const val pendingConfirmation = "pendingConfirmation"
    const val confirmed = "confirmed"
    const val completed = "completed"
    const val pendingPayment = "pendingPayment"
    const val processing = "processing"
    const val cancel = "cancel"
    const val PENDING = "PENDING"
    const val ACCEPTED = "ACCEPTED"
    const val APPROVED = "APPROVED"
    const val CONFIRMED = "CONFIRMED"
    const val COMPLETED = "COMPLETED"
    const val CANCELLED = "CANCELLED"
    const val REFUNDED = "REFUNDED"
    const val maxBusTicketPriorityCount = 3
    const val minBusTicketPriorityCount = 1
    const val minBusTicketSeatSelection = 1
    const val maxBusTicketSeatSelection = 10

    const val sort = "sort"
    const val filter = "filter"
    const val price_low_to_high = "Price Low to high"
    const val price_high_to_low = "Price high to low"

    const val economy_class = "Economy"
    const val business_class = "Business Class"

    const val filter_no_data = 1

    const val maximum_char_length = 230

    const val PICK_LOCATION = 1
    const val VIA_LOCATION = 2
    const val DROP_LOCATION = 3

    const val YES = "YES"
    const val NO = "NO"
    const val validation_successful = 1

}